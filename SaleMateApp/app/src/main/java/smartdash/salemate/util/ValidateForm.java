package smartdash.salemate.util;

import android.app.Activity;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.regex.Pattern;

import smartdash.salemate.R;


public class ValidateForm {

    private static Pattern PATTERN_GENERIC = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
    private static Pattern PATTERN_NUMBERS = Pattern.compile("(?=^((?!((([0]{11})|([1]{11})|([2]{11})|([3]{11})|([4]{11})|([5]{11})|([6]{11})|([7]{11})|([8]{11})|([9]{11})))).)*$)([0-9]{11})");

    private Activity activity;

    public ValidateForm(Activity activity) {
        this.activity = activity;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // region validate Cpf
    public boolean validateCPF(TextInputEditText editText, TextInputLayout textInputLayout) {
        String CPF = editText.getText().toString().trim();
        if (CPF.isEmpty() || !isValidCPF(CPF)) {
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_cpf));
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidCPF(String cpf) {
        if (cpf != null && PATTERN_GENERIC.matcher(cpf).matches()) {
            cpf = cpf.replaceAll("-|\\.", "");
            if (cpf != null && PATTERN_NUMBERS.matcher(cpf).matches()) {
                int[] numbers = new int[11];
                for (int i = 0; i < 11; i++) numbers[i] = Character.getNumericValue(cpf.charAt(i));
                int i;
                int sum = 0;
                int factor = 100;
                for (i = 0; i < 9; i++) {
                    sum += numbers[i] * factor;
                    factor -= 10;
                }
                int leftover = sum % 11;
                leftover = leftover == 10 ? 0 : leftover;
                if (leftover == numbers[9]) {
                    sum = 0;
                    factor = 110;
                    for (i = 0; i < 10; i++) {
                        sum += numbers[i] * factor;
                        factor -= 10;
                    }
                    leftover = sum % 11;
                    leftover = leftover == 10 ? 0 : leftover;
                    return leftover == numbers[10];
                }
            }
        }
        return false;
    }

    // endregion

    // region validate email

    public boolean validateEmail(TextInputEditText editText, TextInputLayout textInputLayout) {
        String email = editText.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            textInputLayout.setError(this.activity.getString(R.string.activity_cadastro_err_msg_email));
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // endregion

    // region validate date

    public boolean validateDate(TextInputEditText editText, TextInputLayout textInputLayout, String current) {
        String date = editText.getText().toString().trim();
        if(date.isEmpty() || isNotValidateDate(date, current)) {
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_date_birthday));
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isNotValidateDate(String date, String current){
        return !TextUtils.isEmpty(date) && current.contains("Y")||current.contains("M")||current.contains("D");
    }

    // endregion

    // region validate username

    public boolean validateNome(TextInputEditText editText, TextInputLayout textInputLayout) {
        String nome = editText.getText().toString().trim();
        if (nome.isEmpty() || nome.length() <2) {
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_full_name));
            requestFocus(editText);
            return false;
        }else if(!isNotValidateName(nome)){
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_invalid_name));
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isNotValidateName(String str) {
        String expression = "^[A-zÀ-ú][A-zÀ-ú]+([ ][A-zÀ-ú][A-zÀ-ú]+)*$";
        return str.matches(expression) && str.contains(" ");
    }

    public boolean validateNomeLocalizacao( EditText editText) {
       String Nome = editText.getText().toString();
        if (Nome.length()<2) {
            editText.setError(activity.getString(R.string.fragment_suaslocalizacoes_err_msg_nome_localizacao));
            requestFocus(editText);
            return false;
        } else {
            editText.setError(null);
        }
        return true;
    }


    // endregion

    // region validate phone number

    public boolean validatePhoneNumber(TextInputEditText editText, TextInputLayout textInputLayout) {
        String phone = editText.getText().toString().trim();
        if (phone.isEmpty() || !isValidPhoneNumber(phone)) {
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_telephone));
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches()&& phoneNumber.length()==15;
    }

    // endregion

    // region validate password

    public boolean validatePassword(TextInputEditText editText, TextInputLayout textInputLayout) {
        if(editText.getText().length()<6){
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_length_password));
            requestFocus(editText);
            return false;
        }else if (editText.getText().toString().trim().isEmpty()){
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_password));
            requestFocus(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateConfirmPassword(TextInputEditText editSenha, TextInputEditText editConfirmaSenha, TextInputLayout textInputLayout) {
        if (!editSenha.getText().toString().contentEquals(editConfirmaSenha.getText().toString())) {
            textInputLayout.setError(activity.getString(R.string.activity_cadastro_err_msg_confirm_password));
            requestFocus(editConfirmaSenha);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    // endregion
}
