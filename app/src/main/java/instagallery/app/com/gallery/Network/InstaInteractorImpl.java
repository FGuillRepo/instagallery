package instagallery.app.com.gallery.Network;

import android.content.Context;

import instagallery.app.com.gallery.Model.InstagramResponse;
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


    @Override
    public void getInstagram_Data(final Context context, final OnRequestFinishedListener listener, String accessToken) {

        username ="";
        pictureuser ="";
        this.listener = listener;
        Call<InstagramResponse> call = RestClient.getRetroFitService().getTagPhotos(accessToken);
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


    public void setUsername(String username) {
        usernameObservable.onNext(username);
    }

    @Override
    public Observable<String> getUsernameChange() {
        return usernameObservable;
    }

    public void setPictureProfil(String username) {
        pictureProfilObservable.onNext(username);
    }
    @Override
    public Observable<String> getUserPictureChange() {
        return pictureProfilObservable;
    }

}

