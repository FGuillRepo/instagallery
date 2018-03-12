package instagallery.app.com.gallery;

import android.content.Context;
import android.support.multidex.MultiDex;


public class Application extends android.app.Application {


    public static Application application;
    private static Context context;

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


}

