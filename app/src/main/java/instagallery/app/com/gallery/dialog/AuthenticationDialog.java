package instagallery.app.com.gallery.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagallery.app.com.gallery.Model.AuthenticationListener;
import instagallery.app.com.gallery.Network.Login_URL;
import instagallery.app.com.gallery.R;


public class AuthenticationDialog extends Dialog {

    private final AuthenticationListener listener;
    private Context context;

    @BindView(R.id.web_view)
    WebView web_view;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    Login_URL login_url;


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
        login_url=new Login_URL(context);
        initializeWebView();
    }

    private void initializeWebView() {

        web_view.loadUrl(login_url.getAuthenticationURL());
        web_view.setWebViewClient(new WebViewClient() {

            boolean authComplete = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            String code;

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleURL(url);
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final String url = request.getUrl().toString();
                return handleURL(url);
            }


            private boolean handleURL(String url) {
                if (url.startsWith(login_url.getREDIRECT_URI()))
                {
                    Log.d("totoo",login_url.getREDIRECT_URI());
                    String parts[] = url.split("=");
                    code = parts[1];  //This is your request token.
                    authComplete = true;
                    listener.onCodeReceived(code);
                    dismiss();
                    return true;
                }
                return false;
            }

        });
    }
}