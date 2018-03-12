package instagallery.app.com.gallery.RetroFit;


import instagallery.app.com.gallery.Model.AccessToken;
import instagallery.app.com.gallery.Model.InstagramResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RetrofitService {
 
    @GET("v1/users/self/media/recent?access_token=access_token")
    Call<InstagramResponse> getTagPhotos(@Query("access_token") String access_token);


    @FormUrlEncoded
    @POST("oauth/access_token/?client_id=client_id&client_secret=client_secret&grant_type=authorization_code&redirect_uri=redirect_uri&code=code")
    Call<AccessToken> getAccessToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret,
                                     @Field("grant_type") String grant_type,
                                     @Field("redirect_uri") String redirect_uri,
                                     @Field("code") String code);

}