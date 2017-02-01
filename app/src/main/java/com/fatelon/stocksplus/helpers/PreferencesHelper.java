package com.fatelon.stocksplus.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Fatelon on 21.01.2017.
 */

public class PreferencesHelper {

    private static final String KEY_USER_SESSION_ID = "user_session_id";

    private static final String KEY_USER_ID = "user_id";

    private static final String KEY_IS_USER_LOGIN = "is_user_login";

    private static final String KEY_USER_NAME = "user_name";

    private static final String KEY_USER_PASS = "user_pass";

    private static final String KEY_FIRST_INDICATORS = "first_indicators";

    private static final String KEY_SECOND_INDICATORS = "second_indicators";

    private static final String KEY_CURRENT_WATCHLIST_ID = "current_watchlist_id";

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

    public static void storeUserId(Context context, Integer userId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public static Integer getUserId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getInt(KEY_USER_ID, -1);
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

    public static void storeFirstIndicators(Context context, String indicators) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putString(KEY_FIRST_INDICATORS, indicators).apply();
    }

    public static String getFirstIndicators(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getString(KEY_FIRST_INDICATORS, "");
    }

    public static void storeSecondIndicators(Context context, String indicators) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putString(KEY_SECOND_INDICATORS, indicators).apply();
    }

    public static String getSecondIndicators(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getString(KEY_SECOND_INDICATORS, "");
    }

    public static void storeCurrentWatchlistId(Context context, String wlId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        preferences.edit().putString(KEY_CURRENT_WATCHLIST_ID, wlId).apply();
    }

    public static String getCurrentWatchlistId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return preferences.getString(KEY_CURRENT_WATCHLIST_ID, "");
    }
}
