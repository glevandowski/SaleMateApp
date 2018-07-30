package smartdash.salemate.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {

    private static SharedPreference sharedPreference;
    public static final String PREFS_NAME = "SaleMateLocalStorage";

    public static final String KEY_USER_NAME = "KeyUserName";
    public static final String KEY_USER_EMAIL = "KeyUserEmail";
    public static final String KEY_USER_TOKEN = "KeyUserToken";

    public static final String KEY_USER_CREDITCARD = "KeyUserCreditCard";
    public static final String KEY_USER_CREDITCARD_REPOSITORY = "KeyUserCreditCardRepository";

    public static final String KEY_USER_LOCATION_REPOSITORY = "KeyUserLocationRepository";

    public static final String KEY_USER_ORIGEM = "KeyUserOrigem";
    public static final String KEY_USER_ORIGEM_REPOSITORY = "KeyUserOrigemRepository";
    public static final String KEY_USER_RACE_REPOSITORY = "KeyUserRaceRepository";
    public static final String KEY_USER_CADASTRO = "KeyUserCadastro";

    public static SharedPreference getInstance()
    {
        if (sharedPreference == null)
        {
            sharedPreference = new SharedPreference();
        }
        return sharedPreference;
    }

    public SharedPreference() {
        super();
    }

    public void save(Context context, String Key, String text) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(mActivity);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(Key, text); //3

        editor.commit(); //4
    }

    public String getValue(Context context , String Key) {
        SharedPreferences settings;
        String text = "";
        //  settings = PreferenceManager.getDefaultSharedPreferences(mActivity);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(Key, "");
        return text;
    }

    public void clear(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(mActivity);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context , String value) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(value);
        editor.commit();
    }
}