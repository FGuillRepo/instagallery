package instagallery.app.com.gallery;

import android.content.Context;
import android.support.multidex.MultiDex;


public class Application extends android.app.Application {


    public static Application parseApplication;
    private static Context context;
    public static final String BASE_URL = "https://api.instagram.com/";
    public static final String CLIENT_ID = "8c26e90e81484d91836edf2b36b67f0c";

    public static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
    //Used for Authentication.
    public static final String TOKENURL ="https://api.instagram.com/oauth/access_token";
    //Used for getting token and User details.
    public static final String APIURL = "https://api.instagram.com/v1";
    //Used to specify the API version which we are going to use.
    public static String REDIRECT_URI = "https://guillalab.wixsite.com/lifesafe";
    //The callback url that we have used while registering the application.
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext()
    {
        return context;
    }

    public static synchronized Application getInstance() {
        return parseApplication;
    }


}

