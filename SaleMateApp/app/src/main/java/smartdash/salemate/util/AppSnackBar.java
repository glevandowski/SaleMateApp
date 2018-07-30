package smartdash.salemate.util;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import smartdash.salemate.views.interfaces.UpdateFragmentListener;

public class AppSnackBar {
    //region PROPRIEDADES
    boolean FLAG_CONNECTION = true;
    Activity mActivity;
    UpdateFragmentListener listenerUpdateFragment;

    CheckConnection checkConnection;
    CoordinatorLayout coordinatorLayout;
    //endregion

    //region Construtores
    /***
     *builders for different situations
     *@param mActivity
     */
    public AppSnackBar(Activity mActivity, CoordinatorLayout coordinatorLayout) {
        this.mActivity = mActivity;
        this.coordinatorLayout = coordinatorLayout;
    }

    /*CONSTRUTOR PARA USO DE LISTENER E SUAS INTERAÇÕES COM A APLICAÇÃO*/
    public AppSnackBar(Activity mActivity, CoordinatorLayout coordinatorLayout, CheckConnection checkConnection, UpdateFragmentListener listenerUpdateFragment) {
        this.mActivity = mActivity;
        this.coordinatorLayout = coordinatorLayout;
        this.checkConnection = checkConnection;
        this.listenerUpdateFragment = listenerUpdateFragment;
    }


    //endregion

    //region Snackbar Simples
    public void showSnackbar( String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackbarIndefiniteTime( String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE).show();
    }
    //endregion

    //region Snackbar para teste de conexão
    /***
     *
     * @return true ou false para conexão a internet
     */
    public boolean isConnectionExistSnackBar( String firstMessage, String buttonMessage, final String secondMessage) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, firstMessage, Snackbar.LENGTH_INDEFINITE).setAction(buttonMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar snackbarConfirm = Snackbar.make(coordinatorLayout, secondMessage, Snackbar.LENGTH_SHORT);
                listenerUpdateFragment.updateFragment();
                snackbarConfirm.show();
            }
        });
        if (!checkConnection.isOnline()){
            snackbar.show();
             FLAG_CONNECTION = false;
            return false;
        }else {
            snackbar.dismiss();
            FLAG_CONNECTION = true;
            return true;
        }
    }

   //TODO isConnectionExistSnackbar return true, and there is still a problem.
    public void snackbarTestConnection(String firstMessage, String buttonMessage, final String secondMessage){
        if(FLAG_CONNECTION == true) {
            setSnackbarManageConnection(firstMessage, buttonMessage, secondMessage);
        }
    }

    //Method aux
    private void setSnackbarManageConnection( String firstMessage, String buttonMessage, final String secondMessage) {
        Snackbar snackbar =  Snackbar.make(coordinatorLayout, firstMessage, Snackbar.LENGTH_INDEFINITE).setAction(buttonMessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbarConfirm = Snackbar.make(coordinatorLayout, secondMessage, Snackbar.LENGTH_SHORT);
                listenerUpdateFragment.updateFragment();
                snackbarConfirm.show();
            }
        });
        snackbar.show();
    }
    //endregion
}
