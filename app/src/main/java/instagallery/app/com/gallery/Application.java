package instagallery.app.com.gallery;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import instagallery.app.com.gallery.LocalDatabase.DatabaseHandler;


public class Application extends android.app.Application {


    public static Application application;
    private static Context context;
    public static DatabaseHandler databaseHandler;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
        setContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        databaseHandler=new DatabaseHandler(context);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }

    public static void setContext(Context context) {
        Application.context = context;
    }

    public static Context getContext(Context context)
    {
        return Application.context;
    }

    public static synchronized Application getApplication() {
        return application;
    }

    public static DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}

