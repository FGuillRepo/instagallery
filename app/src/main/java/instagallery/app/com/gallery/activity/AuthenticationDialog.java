package instagallery.app.com.gallery.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.Application;
import instagallery.app.com.gallery.AuthenticationListener;
import instagallery.app.com.gallery.R;


public class AuthenticationDialog extends Dialog {
 
    private final AuthenticationListener listener;
    private Context context;

    @BindView(R.id.web_view)
    WebView web_view;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private final String url = Application.BASE_URL
            + "oauth/authorize/?client_id="
            + Application.CLIENT_ID
            + "&redirect_uri="
            + Application.REDIRECT_URI
            + "&response_type=token"
            + "&display=touch&scope=public_content";

    public AuthenticationDialog(@NonNull Context context, AuthenticationListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_authentication);
        ButterKnife.bind(this, this);
        initializeWebView();
    }
 
    private void initializeWebView() {
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {
 
            boolean authComplete = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
 
            String access_token;

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.contains("#access_token=") && !authComplete) {
                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();
                    access_token = access_token.substring(access_token.lastIndexOf("=")+1);
                    authComplete = true;
                    listener.onCodeReceived(access_token);
                    dismiss();

                } else if (url.contains("error")){
                    String message = context.getString(R.string.auth_error);
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
}