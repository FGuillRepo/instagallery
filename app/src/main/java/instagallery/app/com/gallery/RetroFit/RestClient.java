package instagallery.app.com.gallery.RetroFit;

import instagallery.app.com.gallery.Application;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
 
    public static Retrofit createRetroFit() {
        return new Retrofit.Builder()
                .baseUrl(Application.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static RetrofitService getRetroFitService() {
        final Retrofit retrofit = createRetroFit();
        return retrofit.create(RetrofitService.class);
    }




}