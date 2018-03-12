package instagallery.app.com.gallery.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.fragment.AuthenticationFragment;


public class AuthentificationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_fragment, AuthenticationFragment.newInstance(),"Authentication_fragment")
                .commit();

    }
}
