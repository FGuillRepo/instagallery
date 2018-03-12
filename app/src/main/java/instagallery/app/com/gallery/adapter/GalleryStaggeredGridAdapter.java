package instagallery.app.com.gallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.Model.Data;
import instagallery.app.com.gallery.R;
import rx.functions.Func1;

public class GalleryStaggeredGridAdapter extends CustomRecyclerViewAdapter {
    private Activity activity;
    private int screenWidth;
    private int screenHeight;


    public GalleryStaggeredGridAdapter(Activity activity, ArrayList<Data> images) {
        this.activity = activity;
        this.images = images;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

    }

    public Object getItem(int position) {
        return images.get(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.recyclerview_row, parent, false);
        Holder dataObjectHolder = new Holder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        final Data myHolder = (Data) getItem(position);
        myHolder.setPosition(position);
        final Object[] ItemObject=new Object[]{myHolder,((Holder) holder).images};

        RxView.clicks(holder.itemView) //Item click binding, observer in GalleryActivity
                .map(new Func1<Void, Object[]>() {
                    @Override
                    public Object[] call(Void holder)
                    {
                        return ItemObject;
                    }
                })
                .subscribe(mViewClickSubject);


        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(myHolder.getUser().getProfile_picture(), opts);
        opts.inJustDecodeBounds = false;
        final int height;
        if (position == 1 || position == (images.size() - 1)) {
            height = screenHeight/3;
        } else {
            height = screenHeight/(int)2.2;
        }


        Picasso.with(activity)
                .load(myHolder.getImages().getStandard_resolution().getUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(screenWidth / 2, height)
                .centerCrop()
               .into(((Holder) holder).images, new Callback() {
            @Override
            public void onSuccess() {
                Log.v("Picasso","fetch cache");
            }

            @Override
            public void onError() {
                // Try again online if cache failed
                Picasso.with(activity)
                        .load(Uri.parse(myHolder.getImages().getStandard_resolution().getUrl()))
                        .error(R.drawable.ic_close)
                        .placeholder(R.drawable.ic_photo_camera)
                        .resize(screenWidth / 2, height)
                        .centerCrop()
                        .into(((Holder) holder).images, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.v("Picasso","fetch online");

                            }

                            @Override
                            public void onError() {
                                Log.v("Picasso","Could not fetch image");
                            }
                        });

            }
        });




        if (myHolder.getLikes().getCount()!=null) {
            ((Holder) holder).likes.setText(myHolder.getLikes().getCount());
        }
        if (myHolder.getComments()!=null) {
            ((Holder) holder).comments.setText(myHolder.getComments().getCount());
        }

    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class Holder extends CustomRecycleViewHolder{

        @BindView(R.id.ivItemGridImage) ImageView images;
        @BindView(R.id.comments) TextView comments;
        @BindView(R.id.likes) TextView likes;

        public Holder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public ArrayList<Data> getList(){
        return images;
    }

    public void clearData(){
         images.clear();
        this.notifyDataSetChanged();
    }

}