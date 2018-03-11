package instagallery.app.com.gallery.Network;

import android.content.Context;

public interface InstaPresenter {
    void Instagram_request(Context context, String accessToken, String type);

    void onDestroy();
}