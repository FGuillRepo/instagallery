package instagallery.app.com.gallery.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import instagallery.app.com.gallery.Model.Data;
import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.adapter.LinearLayoutAdapter;


public class GalleryDetailActivity extends Activity {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.left_swipe) ImageView leftSwipe;
    @BindView(R.id.right_swipe) ImageView rightSwipe;
    @BindView(R.id.close_button) ImageView close;

    private int current_position=-1;
    protected ArrayList<Data> arrayPicture = new ArrayList<>();


    private Data pictureData=null;
    private RecyclerView.LayoutManager mLayoutManager;
    public LinearLayoutAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detailgallery);
    }

}