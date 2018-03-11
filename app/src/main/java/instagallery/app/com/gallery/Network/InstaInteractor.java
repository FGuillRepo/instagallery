package instagallery.app.com.gallery.Network;

import android.content.Context;

public interface InstaInteractor {

    interface OnRequestFinishedListener {
        void onError();
        void onSuccess();
    }
     void getInstagram_Data(final Context context, final OnRequestFinishedListener listener, String accessToken);
    }