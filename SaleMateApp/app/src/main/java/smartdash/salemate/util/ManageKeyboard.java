package smartdash.salemate.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ManageKeyboard {
    Activity mActivity;

    public ManageKeyboard(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void hideKeyboard() {
        View view = mActivity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
