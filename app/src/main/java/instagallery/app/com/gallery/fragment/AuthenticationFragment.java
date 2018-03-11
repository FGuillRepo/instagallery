package instagallery.app.com.gallery.fragment;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.AuthenticationListener;
import instagallery.app.com.gallery.R;
import instagallery.app.com.gallery.Utils.Utils;
import instagallery.app.com.gallery.activity.AuthenticationDialog;
import instagallery.app.com.gallery.activity.GalleryActivity;
import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by Moi on 14/11/15.
 */
public class AuthenticationFragment extends Fragment implements AuthenticationListener, Animator.AnimatorListener {

    private AuthenticationDialog auth_dialog;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    @BindView(R.id.login_button)
    Button login_button;

    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottie;

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


        lottie.setImageAssetsFolder("images/");
        lottie.loop(false);
        lottie.playAnimation();
        lottie.addAnimatorListener(this);


        login_button.setVisibility(View.INVISIBLE);
        Subscription buttonSub = RxView.clicks(login_button).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (Utils.isConnected(getActivity())) {
                    auth_dialog = new AuthenticationDialog(getActivity(), AuthenticationFragment.this);
                    auth_dialog.setCancelable(true);
                    auth_dialog.show();
                } else {
                    String message = getActivity().getString(R.string.dialog_network_message);
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        return inflate;
    }


    @Override
    public void onCodeReceived(String access_token) {
        if (access_token == null) {
            auth_dialog.dismiss();
        }

        Intent i = new Intent(getActivity(), GalleryActivity.class);
        i.putExtra("access_token", access_token);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);


    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        login_button.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        login_button.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

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


}
