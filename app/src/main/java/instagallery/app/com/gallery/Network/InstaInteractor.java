package instagallery.app.com.gallery.Network;

import android.content.Context;

import io.reactivex.Observable;

public interface InstaInteractor {

    interface OnRequestFinishedListener {
        void onError();
        void onSuccess();
    }

    public Observable<String> getUsernameChange();
    public Observable<String> getUserPictureChange();

    void getInstagram_Data(final Context context, final OnRequestFinishedListener listener, String accessToken);
    }