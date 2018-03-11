package instagallery.app.com.gallery;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import instagallery.app.com.gallery.activity.AuthentificationActivity;


/**
 * Created by Moi on 23/06/2016.
 */
public class LauncherApplication extends Activity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_launcher);

        if (savedInstanceState == null) {
                 //   if (Status(getApplicationContext()).equals("Auth")) {
                        Intent intent = new Intent(getBaseContext(), AuthentificationActivity.class);
                        startActivity(intent);
                        finish();
            }
    }

    private String Status(Context context) {
        SharedPreferences pref = context.getSharedPreferences("Pref", context.MODE_PRIVATE);
        final String accountDisconnect = pref.getString("status", "");         // getting String
        return accountDisconnect;
    }
}
