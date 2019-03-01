package instagallery.app.com.gallery.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import instagallery.app.com.gallery.Application;
import instagallery.app.com.gallery.Model.Data;
import instagallery.app.com.gallery.Network.InstaView;
import instagallery.app.com.gallery.Network.InstagramRequestPresenter;
import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.Utils.Utils;
import instagallery.app.com.gallery.adapter.CustomStaggeredLayoutManager;
import instagallery.app.com.gallery.adapter.GalleryStaggeredGridAdapter;
import instagallery.app.com.gallery.view.CollapseLayoutLottieView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.functions.Action1;

import static instagallery.app.com.gallery.Utils.Utils.showSnackbarConnectivity;


public class GalleryActivity extends AppCompatActivity implements InstaView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lv_feed) RecyclerView recyclerView;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.user_picture) CircleImageView userpicture;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.barlayout_animation) CollapseLayoutLottieView barlayout_animation;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;


    public static String mUsername = "";
    public static String mUserPicture = "";
    private String DATA_LIST_KEY;
    private String TOKEN_KEY;

    private CustomStaggeredLayoutManager mLayoutManager;
    private GalleryStaggeredGridAdapter adapter;
    private InstagramRequestPresenter instagramPresenter;
    public static ArrayList<Data> data = new ArrayList<>();
    private String access_token = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        setup();
        InitRecyclerView();

        // presenter initalize
        instagramPresenter = new InstagramRequestPresenter(this);
        //observer username and picture on request
        instagramPresenter.getInstaInteractor().getUsernameChange().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String user_name) {
                username.setText(user_name);
                Log.d("userdd", "toto");

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        instagramPresenter.getInstaInteractor().getUserPictureChange().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String picture) {
                Picasso.with(GalleryActivity.this)
                        .load(picture)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .resize(270, 270)
                        .centerCrop()
                        .into(userpicture);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        if (savedInstanceState == null) {
            if (getIntent() != null) {
                Intent i = this.getIntent();
                access_token = i.getStringExtra(getApplicationContext().getString(R.string.intent_acces_stoken));
                data.clear();

                // presenter to request instagram user data
                instagramPresenter.Gallery_ReqestData(GalleryActivity.this, access_token, getApplicationContext().getString(R.string.type_instagram));
            }else {
                finish();
            }
        } else {
            barlayout_animation.ReMeasure();
            access_token = savedInstanceState.getString(TOKEN_KEY);
            data = savedInstanceState.getParcelableArrayList(DATA_LIST_KEY);
            if (data.size()>0) {
                    mUsername = data.get(0).getUser().getFull_name();
                    mUserPicture = data.get(0).getImages().getStandard_resolution().getUrl();

                username.setText(mUsername);
                Picasso.with(GalleryActivity.this).load(mUserPicture)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .resize(200, 200)
                        .centerCrop()
                        .into(userpicture);

            }
        }

        //observer click events recyclerview
        if (adapter!=null) {
            adapter.getViewClickedObservable().subscribe(new Action1<Object[]>() {
                @Override
                public void call(Object[] view) {
                    showDetailedPicture(view);
                }
            });
        }
    }


    private void InitRecyclerView() {
        if (mLayoutManager == null) {
            mLayoutManager = new CustomStaggeredLayoutManager(2, GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
            adapter = new GalleryStaggeredGridAdapter(this, data);
            recyclerView.setAdapter(adapter);
        }
    }

    public void setup() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturnScreen();
            }
        });

        DATA_LIST_KEY  = getApplicationContext().getResources().getString(R.string.saved_data_list);
        TOKEN_KEY  = getApplicationContext().getResources().getString(R.string.saved_token);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void showDetailedPicture(Object[] data) {
        String transitionName = this.getString(R.string.transition_string);

        Data dataItem = (Data) data[0]; //image from recycler touched
        ImageView viewStart = (ImageView) data[1]; //image from recycler touched
        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        viewStart,
                        transitionName
                );

        Intent intent = new Intent(this, GalleryDetailActivity.class);
        intent.putExtra("position", dataItem);
        ActivityCompat.startActivity(this, intent, options.toBundle());
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }


    @Override
    public void ShowRequestProgress() {
        barlayout_animation.startAnimation();
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
    }

    @Override
    public void noNetworkConnectivity() {
        showSnackbarConnectivity(getApplicationContext(),coordinatorLayout);

    }

    @Override
    public void onRefresh() {
        if(Utils.isConnected(getApplicationContext())) {
            data.clear();
            InitRecyclerView();
            adapter.clearData();
            instagramPresenter.Gallery_ReqestData(GalleryActivity.this, access_token, getApplicationContext().getString(R.string.type_instagram));
        }else {
            showSnackbarConnectivity(getApplicationContext(),coordinatorLayout);
            swipeRefreshLayout.setRefreshing(false);

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TOKEN_KEY, access_token);
        outState.putParcelableArrayList(DATA_LIST_KEY, new ArrayList<Data>(adapter.getList()));
        super.onSaveInstanceState(outState);
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

            Application.getDatabaseHandler().deleteAllUserData();
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();

            ReturnScreen();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        barlayout_animation.getLottie().cancelAnimation();
    }

    private void ReturnScreen(){
        if (Application.getDatabaseHandler().getUserCount()<=0){
            Intent intent = new Intent(getBaseContext(), AuthentificationActivity.class);
            startActivity(intent);
            finish();
        }else{
            finish();
        }
    }
}