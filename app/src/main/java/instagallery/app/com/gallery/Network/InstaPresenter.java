package instagallery.app.com.gallery.Network;

import android.content.Context;

public interface InstaPresenter {
    void Gallery_ReqestData(Context context, String accessToken, String type);
    void Login_RequestAccessToken(Context context,String code);
    void onDestroy();
}