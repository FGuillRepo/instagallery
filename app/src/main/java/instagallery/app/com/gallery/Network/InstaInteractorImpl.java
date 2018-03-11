package instagallery.app.com.gallery.Network;

import android.content.Context;

import instagallery.app.com.gallery.Model.InstagramResponse;
import instagallery.app.com.gallery.RetroFit.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static instagallery.app.com.gallery.activity.GalleryActivity.data;
import static instagallery.app.com.gallery.activity.GalleryActivity.mUserPicture;
import static instagallery.app.com.gallery.activity.GalleryActivity.mUsername;


public class InstaInteractorImpl implements InstaInteractor {

    protected boolean error = false;
    OnRequestFinishedListener listener;

    @Override
    public void getInstagram_Data(final Context context, final OnRequestFinishedListener listener, String accessToken) {
        this.listener = listener;
        Call<InstagramResponse> call = RestClient.getRetroFitService().getTagPhotos(accessToken);
        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().getData().length; i++) {
                        if (mUsername.equals("")) {
                            mUsername = response.body().getData()[i].getUser().getFull_name();
                        }
                        if (mUserPicture.equals("")) {
                            mUserPicture = response.body().getData()[i].getImages().getStandard_resolution().getUrl();
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

}

