package instagallery.app.com.gallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.Model.Data;
import instagallery.app.com.gallery.R;
import rx.Observable;
import rx.subjects.PublishSubject;

public class LinearLayoutAdapter extends CustomRecyclerViewAdapter {
    private Activity activity;
    private int screenWidth;
    private int screenHeight;

    public PublishSubject<Object[]> mViewClickSubject = PublishSubject.create();

    public Observable<Object[]> getViewClickedObservable() {
        return mViewClickSubject.asObservable();
    }

    public LinearLayoutAdapter(Activity activity, ArrayList<Data> images) {
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

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(myHolder.getUser().getProfile_picture(), opts);
        opts.inJustDecodeBounds = false;

        Picasso.with(activity)
                .load(myHolder.getImages().getStandard_resolution().getUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(screenWidth, screenHeight)
                .centerCrop()
               .into(((Holder) holder).images, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                // Try again online if cache failed
                Picasso.with(activity)
                        .load(Uri.parse(myHolder.getImages().getStandard_resolution().getUrl()))
                        .error(R.drawable.ic_share)
                        .placeholder(R.drawable.ic_photo_camera)
                        .resize(screenWidth , screenHeight)
                        .centerCrop()
                        .into(((Holder) holder).images);
            }
        });

        ((Holder) holder).likes.setText(myHolder.getLikes().getCount());
        ((Holder) holder).comments.setText(myHolder.getComments().getCount());

    }


    @Override
    public int getItemCount() {
        return images.size();
    }


    public class Holder extends CustomRecycleViewHolder {
        @BindView(R.id.likes) TextView likes;
        @BindView(R.id.comments) TextView comments;
        @BindView(R.id.ivItemGridImage) ImageView images;

        public Holder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

    }

    public ArrayList<Data> getList(){
        return images;
    }


}