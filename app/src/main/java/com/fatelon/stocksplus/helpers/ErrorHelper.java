package com.fatelon.stocksplus.helpers;

import android.content.Context;
import android.util.Log;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.dialogs.SimpleDialog;

/**
 * Created by User on 21.01.2017.
 */

public class ErrorHelper {

    private static String TAG = ErrorHelper.class.getSimpleName();

    /*
    * Failed to connect
    * */
    public static void failedToConnectSimpleDialog(Context context, Throwable e) {
        Log.d(TAG, "onError - " + e.toString());
        try {
            String title = context.getResources().getString(R.string.failed_to_connect_title);
            String message = context.getResources().getString(R.string.failed_to_connect_message);
            SimpleDialog.showSimpleDialog(context, title, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    * Username or password is empty
    * */
    public static void uNameOrPassIsEmpty(Context context) {
        try {
            String title = context.getResources().getString(R.string.warning);
            String message = context.getResources().getString(R.string.un_or_pass_is_empty);
            SimpleDialog.showSimpleDialog(context, title, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    * Registration filled incorrect
    * */
    public static void registrationFillingIncorrect(Context context) {
        try {
            String title = context.getResources().getString(R.string.fields_filled_incorrect_title);
            String message = context.getResources().getString(R.string.fields_filled_incorrect_message);
            SimpleDialog.showSimpleDialog(context, title, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    * Registration error server response
    * */
    public static void registrationErrorFromServer(Context context) {
        try {
            String title = context.getResources().getString(R.string.register_error_title);
            String message = context.getResources().getString(R.string.register_error_message);
            SimpleDialog.showSimpleDialog(context, title, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    * Auth error
    * */
    public static void authError(Context context) {
        try {
            String title = context.getResources().getString(R.string.auth_error_title);
            String message = context.getResources().getString(R.string.auth_error_message);
            SimpleDialog.showSimpleDialog(context, title, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
