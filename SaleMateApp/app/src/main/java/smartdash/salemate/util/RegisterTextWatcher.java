package smartdash.salemate.util;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import smartdash.salemate.R;

public class RegisterTextWatcher implements TextWatcher {

    private Activity mActivity;
    private TextInputEditText genericField;
    private TextInputEditText confirmGenericField;
    private TextInputLayout textInputLayout;
    private ValidateForm validateForm;

    /***
     * CONSTRUTOR DEFAULT
     * @param activity
     * @param genericField
     * @param textInputLayout
     */
    public RegisterTextWatcher(Activity activity, TextInputEditText genericField, TextInputLayout textInputLayout) {
        this.mActivity = activity;
        this.genericField = genericField;
        this.textInputLayout = textInputLayout;
        this.validateForm = new ValidateForm(mActivity);
    }

    /***
     *CONSTRUTOR PARA GERENCIAR CONFIRMAÇÃO DE SENHA
     */
    public RegisterTextWatcher(Activity activity,TextInputEditText genericField, TextInputEditText confirmGenericField, TextInputLayout textInputLayout) {
        this.mActivity = activity;
        this.genericField = genericField;
        this.confirmGenericField = confirmGenericField;
        this.textInputLayout = textInputLayout;
        this.validateForm = new ValidateForm(mActivity);
    }


    @Override
    public void afterTextChanged(Editable editable) {


        switch (genericField.getId()) {

            /***
             * TELA DE LOGIN
             */
            case R.id.text_email_login:
                validateForm.validateEmail(genericField, textInputLayout);
                break;
            case R.id.text_senha_login:
                validateForm.validatePassword(genericField, textInputLayout);
                break;
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
}
