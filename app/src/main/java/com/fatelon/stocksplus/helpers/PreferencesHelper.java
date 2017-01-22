package com.fatelon.stocksplus.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by User on 21.01.2017.
 */

public class PreferencesHelper {

    private static final String KEY_USER_SESSION_ID = "user_session_id";

    private static final String KEY_IS_USER_LOGIN = "is_user_login";

    private static final String KEY_USER_NAME = "user_name";

    private static final String KEY_USER_PASS = "user_pass";

    public static void storeIsUserLogin(Context context, boolean mark) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putBoolean(KEY_IS_USER_LOGIN, mark).apply();
    }

    public static boolean getIsUserLogin(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getBoolean(KEY_IS_USER_LOGIN, false);
    }

    public static void storeUserSessionId(Context context, String sessionId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putString(KEY_USER_SESSION_ID, sessionId).apply();
    }

    public static String getUserSessionId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getString(KEY_USER_SESSION_ID, "");
    }

    public static void storeUserName(Context context, String name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putString(KEY_USER_NAME, name).apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getString(KEY_USER_NAME, "");
    }

    public static void storeUserPass(Context context, String pass) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putString(KEY_USER_PASS, pass).apply();
    }

    public static String getUserPass(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getString(KEY_USER_PASS, "");
    }
}
