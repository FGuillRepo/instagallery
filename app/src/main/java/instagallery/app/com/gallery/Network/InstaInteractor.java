package instagallery.app.com.gallery.Network;

import android.content.Context;

import instagallery.app.com.gallery.Model.AccessToken;
import io.reactivex.Observable;

public interface InstaInteractor {

    interface OnRequestFinishedListener {
        void onError();
        void onSuccess();
    }

    public Observable<String> getUsernameChange();
    public Observable<String> getUserPictureChange();
    public Observable<AccessToken> getAccessTokenChange();

    void getInstagram_Data(Context context, OnRequestFinishedListener listener, String accessToken);

    void getAccessToken(Context context, OnRequestFinishedListener listener,String code);

}