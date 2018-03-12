package instagallery.app.com.gallery.Utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import instagallery.app.com.gallery.R;


/**
 * Created by Moi on 29/01/2017.
 */

public class Utils {


    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else return false;
    }




    public interface Click {
        void Ok();

        void Cancel();
    }


    public static void showDialog(Context activity, String message, String title, Boolean hideCancel, final Click ok) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_network_connectivity);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView titleTV = (TextView) dialog.findViewById(R.id.titletext);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
        text.setText(message);

        if (hideCancel) {
            cancelBtn.setVisibility(View.INVISIBLE);
            cancelBtn.setClickable(false);
        } else {
            cancelBtn.setVisibility(View.VISIBLE);
            cancelBtn.setClickable(true);
        }

        titleTV.setText(title);
        titleTV.setVisibility(View.VISIBLE);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok.Ok();
                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok.Cancel();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static  void showSnackbarConnectivity(Context context, CoordinatorLayout coordinatorLayout){
        String message = context.getString(R.string.dialog_network_message);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }





}


