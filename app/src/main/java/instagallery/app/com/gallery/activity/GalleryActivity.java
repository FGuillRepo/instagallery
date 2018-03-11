package instagallery.app.com.gallery.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import instagallery.app.com.gallery.Model.Data;
import instagallery.app.com.gallery.Network.InstaView;
import instagallery.app.com.gallery.Network.InstagramRequestPresenter;
import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.adapter.CustomStaggeredGridLayoutManager;
import instagallery.app.com.gallery.adapter.StaggeredGridLayoutAdapter;
import rx.functions.Action1;


public class GalleryActivity extends AppCompatActivity implements InstaView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lv_feed) RecyclerView recyclerView;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.user_picture) CircleImageView userpicture;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;

    public static String mUsername="";
    public static String mUserPicture="";
    private String DATA_LIST_KEY = "DATA_LIST_KEY";
    private String TOKEN_KEY = "TOKEN";

    private CustomStaggeredGridLayoutManager mLayoutManager;
    private StaggeredGridLayoutAdapter adapter;
    private InstagramRequestPresenter instagramPresenter;
    public static ArrayList<Data> data = new ArrayList<>();
    private String access_token = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setToolbar();

        // presenter initalize
        instagramPresenter=new InstagramRequestPresenter(this);

        // presenter initalize
        if (savedInstanceState == null) {
            if (getIntent()!=null) {
                Intent i = this.getIntent();
                access_token = i.getStringExtra("access_token");
                InitRecyclerView();
                data.clear();
                // presenter to request instagram user data
                instagramPresenter.Instagram_request(GalleryActivity.this,access_token,"instagram");
            }
        } else {
            if (data != null) {
                data = savedInstanceState.getParcelableArrayList(DATA_LIST_KEY);
                access_token= savedInstanceState.getString(TOKEN_KEY);

                if (mUsername.length()> 0) {
                    username.setText(data.get(0).getUser().getFull_name());
                    Picasso.with(GalleryActivity.this)
                            .load(data.get(0).getImages().getStandard_resolution().getUrl())
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .resize(200, 200)
                            .centerCrop()
                            .into(userpicture);
                }

                InitRecyclerView();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(this);

        //observer click events recyclerview
        adapter.getViewClickedObservable().subscribe(new Action1<Object[]>() {
            @Override
            public void call(Object[] view) {
                showDetailedPicture(view);
            }
        });
    }


    private void InitRecyclerView() {
        if (mLayoutManager==null) {
            mLayoutManager = new CustomStaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
            adapter = new StaggeredGridLayoutAdapter(this, data);
            recyclerView.setAdapter(adapter);
        }
    }

    public void showDetailedPicture(Object[] data) {
        String transitionName = this.getString(R.string.transition_string);

        Data dataItem =  (Data) data[0]; //image from recycler touched
        ImageView viewStart = (ImageView) data[1]; //image from recycler touched
        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        viewStart,
                        transitionName
                );

        Intent intent=new Intent(this,GalleryDetailActivity.class);
        intent.putExtra("position",dataItem);
        ActivityCompat.startActivity(this, intent, options.toBundle());
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.disconnect) {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            finish();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void ShowRequestProgress() {
        swipeRefreshLayout.setRefreshing(true);


    }

    @Override
    public void onError() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void RequestSuccess() {
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

        if (mUsername.length() >= 0) {
            username.setText(mUsername);

            Picasso.with(GalleryActivity.this)
                    .load(mUserPicture)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .resize(270, 270)
                    .centerCrop()
                    .into(userpicture);
        }

    }

    @Override
    public void noNetworkConnectivity() {
        Log.d("Instagram response", "show View no network");

    }

    @Override
    public void onRefresh() {
        InitRecyclerView();
        data.clear();
        adapter.clearData();
        instagramPresenter.Instagram_request(GalleryActivity.this,access_token,"instagram");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TOKEN_KEY, access_token);
        outState.putParcelableArrayList(DATA_LIST_KEY, new ArrayList<Data>(adapter.getList()));
        super.onSaveInstanceState(outState);
    }
}