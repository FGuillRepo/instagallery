package instagallery.app.com.gallery.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import instagallery.app.com.gallery.Model.Data;
import instagallery.app.com.gallery.Model.InstagramResponse;
import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.RetroFit.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GalleryActivity extends AppCompatActivity  {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lv_feed) RecyclerView recyclerView;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.user_picture) CircleImageView userpicture;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;

    public static ArrayList<Data> data = new ArrayList<>();
    private String access_token = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setToolbar();

        // presenter initalize
        if (savedInstanceState == null) {
            if (getIntent()!=null) {
                Intent i = this.getIntent();
                access_token = i.getStringExtra("access_token");
                CallInstagram();
            }
        } else {
        }

    }

    private void CallInstagram(){
        if (access_token!=null) {
            Call<InstagramResponse> call = RestClient.getRetroFitService().getTagPhotos(access_token);
            call.enqueue(new Callback<InstagramResponse>() {
                @Override
                public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {
                    if (response.body() != null) {
                        for (int i = 0; i < response.body().getData().length; i++) {
                            data.add(response.body().getData()[i]);
                            Log.d("Instagram response",response.body().getData()[i].getUser().getFull_name());
                        }
                    }
                }

                @Override
                public void onFailure(Call<InstagramResponse> call, Throwable t) {
                    //Handle failure
                }
            });
        }
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

}