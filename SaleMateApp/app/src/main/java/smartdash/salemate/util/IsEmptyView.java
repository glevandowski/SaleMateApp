package smartdash.salemate.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class IsEmptyView {
    public Context context;
    TextView textView;

    public IsEmptyView(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

       public void isEmpty(String message){
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
             }

       public void isNotEmpty() {
        textView.setVisibility(View.GONE);
        }
}
