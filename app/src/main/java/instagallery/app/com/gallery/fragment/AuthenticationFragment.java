package instagallery.app.com.gallery.fragment;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.Model.AuthenticationListener;
import instagallery.app.com.gallery.Network.InstaView;
import instagallery.app.com.gallery.Network.InstagramRequestPresenter;
import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.Utils.Utils;
import instagallery.app.com.gallery.activity.GalleryActivity;
import instagallery.app.com.gallery.dialog.AuthenticationDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by Moi on 14/11/15.
 */
public class AuthenticationFragment extends Fragment implements AuthenticationListener, Animator.AnimatorListener, InstaView {

    private AuthenticationDialog auth_dialog;
    private InstagramRequestPresenter instagramPresenter;

    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.lottieAnimationView) LottieAnimationView lottie;
    @BindView(R.id.login_button) Button login_button;

    public static AuthenticationFragment newInstance() {
        AuthenticationFragment myFragment = new AuthenticationFragment();
        Bundle args = new Bundle();
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_authentication, container, false);
        ButterKnife.bind(this, inflate);

        // Initialize presenter
        instagramPresenter = new InstagramRequestPresenter(this);

        // Observer Token request interaction
        instagramPresenter.getInstaInteractor().getAccessTokenChange().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String access_token) {
                try {
                    Intent i = new Intent(getActivity(), GalleryActivity.class);
                    i.putExtra(getActivity().getString(R.string.intent_acces_stoken), access_token);
                    getActivity().startActivity(i);
                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                }catch (NullPointerException e){
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        lottie.setImageAssetsFolder("images/");
        lottie.loop(false);
        lottie.playAnimation();
        lottie.addAnimatorListener(this);


        login_button.setVisibility(View.INVISIBLE);


        // button login, start authentication process
        Subscription buttonSub = RxView.clicks(login_button).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (Utils.isConnected(getActivity())) {
                    auth_dialog = new AuthenticationDialog(getActivity(), AuthenticationFragment.this);
                    auth_dialog.setCancelable(true);
                    auth_dialog.show();
                } else {
                    Utils.showSnackbarConnectivity(getActivity(),coordinatorLayout);;
                }
            }
        });

        return inflate;
    }


    // Take the provided code parameter,  exchange it for an access_token by POSTing the code to our access_token url.
    @Override
    public void onCodeReceived(String code) {
        if (code == null) {
            auth_dialog.dismiss();
        }else {
            // Now code is received, we request accessToken via posting code
            instagramPresenter.Login_RequestAccessToken(getActivity(), code);
        }
    }


       /*
        *  Callback animation
        */

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        login_button.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationCancel(Animator animation) {login_button.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }


    /*
    *  Callback Login AccessToken request
    * */

    @Override
    public void ShowRequestProgress() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void RequestSuccess() {

    }

    @Override
    public void noNetworkConnectivity() {
        Utils.showSnackbarConnectivity(getActivity(),coordinatorLayout);
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        lottie.cancelAnimation();
        super.onDestroy();
    }


}
