package smartdash.salemate.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AppDialogs {

    private Activity mActivity;
    private ProgressDialog progress;

    public AppDialogs(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void showDialogOK(String title, String msg) {
        new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void showDialogOK(String title, String msg, final DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", listener)
                .show();
    }

    public void showDialogConfirm(String title, String msg, String positiveButton, String negativeButton, final DialogInterface.OnClickListener postiveListener, final DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveButton, postiveListener)
                .setNegativeButton(negativeButton, negativeListener)
                .show();
    }

    public void showDialogConfirm(String title, String msg, String positiveButton, String negativeButton, final DialogInterface.OnClickListener postiveListener) {
        new AlertDialog.Builder(mActivity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveButton, postiveListener)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void showProgress(String title, String msg) {
        //ProgressDialog.show(mActivity, title, msg);
        progress = new ProgressDialog(mActivity);
        progress.setTitle(title);
        progress.setMessage(msg);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }


    public void showProgress(String msg) {
        this.showProgress("", msg);
    }


    public void closeProgress()
    {
        progress.dismiss();
    }

}
