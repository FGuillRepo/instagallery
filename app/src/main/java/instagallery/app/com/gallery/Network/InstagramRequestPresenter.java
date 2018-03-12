package instagallery.app.com.gallery.Network;

import android.content.Context;

import instagallery.app.com.gallery.Utils.Utils;

/**
 *
 *  Class managing Presenter and Interactor for Instagram data request
 */

public class InstagramRequestPresenter implements InstaPresenter, InstaInteractor.OnRequestFinishedListener {


    private InstaView instaView;
    private InstaInteractor instaInteractor;


    public InstagramRequestPresenter(InstaView loginView) {
        this.instaView = loginView;
        this.instaInteractor = new InstaInteractorImpl();
    }


    // Request Instagram data user

    @Override
    public void Gallery_ReqestData(Context context,String accessToken, String type) {
        if (Utils.isConnected(context)) {
            if (type.equals("instagram")) {
                instaView.ShowRequestProgress();
                // request Instagram data
                instaInteractor.getInstagram_Data(context, this, accessToken);
            }
        } else {
            instaView.noNetworkConnectivity();
        }
    }


    /**
     * Login request Access Token
     */

    @Override
    public void Login_RequestAccessToken(Context context,String code) {
        if (Utils.isConnected(context)) {
                instaInteractor.getAccessToken(context,this, code);
        } else {
            instaView.noNetworkConnectivity();
        }
    }


    @Override public void onDestroy() {
        instaView = null;
    }


    @Override
    public void onError() {
        if (instaView != null) {
            instaView.onError();
        }
    }

    @Override public void onSuccess() {

        if (instaView != null) {
            instaView.RequestSuccess();
        }
    }

    public InstaInteractor getInstaInteractor() {
        return instaInteractor;
    }

}