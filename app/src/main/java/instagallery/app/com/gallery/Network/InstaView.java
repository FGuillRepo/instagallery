package instagallery.app.com.gallery.Network;

public interface InstaView {
    void showNetworkProgress();

    void onError();

    void NetworkSuccess();

    void noNetworkConnectivity();
}