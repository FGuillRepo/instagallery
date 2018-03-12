package instagallery.app.com.gallery;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import instagallery.app.com.gallery.activity.AuthentificationActivity;
import instagallery.app.com.gallery.activity.GalleryActivity;


/**
 * Created by Franck Guillaouic on 23/06/2016.
 */
public class LauncherApplication extends Activity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_launcher);

        if (savedInstanceState == null) {
            if (Application.getDatabaseHandler().getUserCount()<=0) {
                Intent intent = new Intent(getBaseContext(), AuthentificationActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(getBaseContext(), GalleryActivity.class);
                String access_token= Application.getDatabaseHandler().getKeyToken();
                intent.putExtra(getApplicationContext().getString(R.string.intent_acces_stoken), access_token);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                finish();
            }
        }
}


}
