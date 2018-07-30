package smartdash.salemate.util;

import android.content.Context;
import android.content.Intent;

public class ActivityNavigator {
    public static void openActivity(Context context, Class<?> calledActivity, boolean clearBackStack) {
        Intent myIntent = new Intent(context, calledActivity);
        if (clearBackStack) {
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }
    }
}
