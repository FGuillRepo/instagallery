package instagallery.app.com.gallery.Network;

public interface InstaView {
    void ShowRequestProgress();

    void onError();

    void RequestSuccess();

    void noNetworkConnectivity();
}