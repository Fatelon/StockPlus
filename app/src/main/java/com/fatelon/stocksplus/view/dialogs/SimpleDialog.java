package com.fatelon.stocksplus.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.fatelon.stocksplus.view.callbacks.SimpleDialogCallback;

/**
 * Created by User on 21.01.2017.
 */

public class SimpleDialog {

    public static void showSimpleDialog(Context context, String title, String message) {
        AlertDialog.Builder simpleDialog = new AlertDialog.Builder(context);
        simpleDialog.setTitle(title);
        simpleDialog.setMessage(message);
        simpleDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        simpleDialog.create().show();
    }

    public static void showSimpleDialogWithCallback(Context context, String title, String message, final SimpleDialogCallback simpleDialogCallback) {
        AlertDialog.Builder simpleDialog = new AlertDialog.Builder(context);
        simpleDialog.setTitle(title);
        simpleDialog.setMessage(message);
        simpleDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (simpleDialogCallback != null) simpleDialogCallback.simpleDialogReaction();
                dialog.dismiss();
            }
        });
        simpleDialog.create().show();
    }
}
