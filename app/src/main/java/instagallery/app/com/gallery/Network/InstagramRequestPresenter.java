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

    // calling method to request Instagram data

    @Override
    public void Instagram_request(Context context,String accessToken, String type) {

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
}