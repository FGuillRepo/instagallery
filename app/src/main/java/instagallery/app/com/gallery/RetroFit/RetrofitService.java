package instagallery.app.com.gallery.RetroFit;


import instagallery.app.com.gallery.Model.InstagramResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
 
    @GET("v1/users/self/media/recent?access_token=access_token")
    Call<InstagramResponse> getTagPhotos(@Query("access_token") String access_token);


    /* not use

    @GET("v1/users/self/media/recent?access_token=access_token")
    Observable<Call<InstagramResponse>> getTagPhotoss(@Query("access_token") String access_token);
    */
}