package instagallery.app.com.gallery.RetroFit;

import android.content.Context;

import instagallery.app.com.gallery.Network.Login_URL;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
 
    public static Retrofit createRetroFit(Context context) {
        Login_URL login_url=new Login_URL(context);
        return new Retrofit.Builder()
                .baseUrl(login_url.getBASE_URL())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static RetrofitService getRetroFitService(Context context) {
        final Retrofit retrofit = createRetroFit(context);
        return retrofit.create(RetrofitService.class);
    }




}