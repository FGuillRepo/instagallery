package instagallery.app.com.gallery.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.Model.Data;
import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.adapter.LinearLayoutAdapter;
import rx.Subscription;
import rx.functions.Action1;


public class GalleryDetailActivity extends Activity {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @BindView(R.id.left_swipe) ImageView leftSwipe;

    @BindView(R.id.right_swipe) ImageView rightSwipe;

    @BindView(R.id.close_button) ImageView close;

    private int current_position=-1;
    private Data pictureData=null;

    private String POSITION_SAVED="POSITION_KEY";
    private String DATA_LIST_SAVED="DATA_LIST_KEY";

    private RecyclerView.SmoothScroller smoothScroller;
    private ArrayList<Data> arrayPicture = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutAdapter adapter;
    private Subscription buttonSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detailgallery);
        ButterKnife.bind(this,this);


        if (savedInstanceState==null){
            Intent i = this.getIntent();
            pictureData = (Data) i.getParcelableExtra("position");
            current_position =  pictureData.getPosition();
            arrayPicture= GalleryActivity.data;
        }
        else {
            if (arrayPicture!=null) {
                current_position = savedInstanceState.getInt(POSITION_SAVED);
                arrayPicture= savedInstanceState.getParcelableArrayList(DATA_LIST_SAVED);
            }
        }

        InitRecyclerView();

        buttonSub = RxView.clicks(close).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                GalleryDetailActivity.this.onBackPressed();
            }
        });

        RxView.clicks(leftSwipe).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (current_position>0 && current_position <= GalleryActivity.data.size()){
                    current_position--;
                    smoothScroller.setTargetPosition(current_position);
                    mLayoutManager.startSmoothScroll(smoothScroller);
                }
            }
        });

        RxView.clicks(rightSwipe).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (current_position< GalleryActivity.data.size()){
                    current_position++;
                    smoothScroller.setTargetPosition(current_position);
                    mLayoutManager.startSmoothScroll(smoothScroller);
                }
            }
        });
    }


    private void InitRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new LinearLayoutAdapter(this, arrayPicture);
        recyclerView.setAdapter(adapter);

        smoothScroller = new
                LinearSmoothScroller(this) {
                    @Override protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };

        smoothScroller.setTargetPosition(current_position);
        mLayoutManager.scrollToPosition(current_position);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(DATA_LIST_SAVED, new ArrayList<Data>(adapter.getList()));
        outState.putInt(POSITION_SAVED, current_position);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override public void onDestroy(){
        super.onDestroy();
        if (!buttonSub.isUnsubscribed()){
            buttonSub.unsubscribe();
        }
    }

}