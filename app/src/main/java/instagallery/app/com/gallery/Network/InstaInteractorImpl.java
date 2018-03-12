package instagallery.app.com.gallery.Network;

import android.content.Context;

import instagallery.app.com.gallery.Model.InstagramResponse;
import instagallery.app.com.gallery.Model.AccessToken;
import instagallery.app.com.gallery.RetroFit.RestClient;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static instagallery.app.com.gallery.activity.GalleryActivity.data;


public class InstaInteractorImpl implements InstaInteractor {

    protected boolean error = false;
    private String username;
    private String pictureuser;

    OnRequestFinishedListener listener;

    private PublishSubject<String> usernameObservable = PublishSubject.create();
    private PublishSubject<String> pictureProfilObservable = PublishSubject.create();
    private PublishSubject<String> accessTokenObservable = PublishSubject.create();


    @Override
    public void getInstagram_Data(final Context context, final OnRequestFinishedListener listener, String accessToken) {

        username ="";
        pictureuser ="";
        this.listener = listener;
        Call<InstagramResponse> call = RestClient.getRetroFitService(context).getTagPhotos(accessToken);
        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().getData().length; i++) {
                        if (username.equals("")) {
                            username = response.body().getData()[i].getUser().getFull_name();
                            setUsername(username);
                        }

                        if (pictureuser.equals("")) {
                            pictureuser = response.body().getData()[i].getImages().getStandard_resolution().getUrl();
                            setPictureProfil(pictureuser);
                        }

                        data.add(response.body().getData()[i]);
                    }
                    listener.onSuccess();

                }
            }
            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                //Handle failure
                listener.onError();
            }
        });

    }

    /*
    *   Login : Request accessToken for login
    * */
    @Override
    public void getAccessToken(final Context context,final OnRequestFinishedListener listener, String code) {
        this.listener=listener;
       Login_URL login_url=new Login_URL(context);

        Call<AccessToken> call = RestClient.getRetroFitService(context).getAccessToken(login_url.getCLIENT_ID(),
                login_url.getCLIENT_SECRET(),login_url.getGRANT_TYPE(),login_url.getREDIRECT_URI(),code);

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                try {
                    listener.onSuccess();
                    AccessToken accessToken = response.body();
                    setAccessToken(accessToken.getAccessToken());

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                listener.onError();
            }
        });

    }




    public void setUsername(String username) {
        usernameObservable.onNext(username);
    }
    public void setPictureProfil(String username) {pictureProfilObservable.onNext(username);}
    public void setAccessToken(String accessToken) {accessTokenObservable.onNext(accessToken);}


    @Override
    public Observable<String> getUsernameChange() {
        return usernameObservable;
    }

    @Override
    public Observable<String> getUserPictureChange() {
        return pictureProfilObservable;
    }

    @Override
    public Observable<String> getAccessTokenChange() {return accessTokenObservable;
    }

}

