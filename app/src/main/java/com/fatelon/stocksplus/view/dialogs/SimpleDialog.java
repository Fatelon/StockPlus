package com.fatelon.stocksplus.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fatelon.stocksplus.R;
import com.fatelon.stocksplus.view.callbacks.DialogMultiResponse;
import com.fatelon.stocksplus.view.callbacks.SimpleDialogCallback;

/**
 * Created by Fatelon on 21.01.2017.
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

    public static void showAddQuoteDialogWithCallback(Context context, final DialogMultiResponse dialogMultiResponse) {
        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setHint(context.getResources().getString(R.string.add_stock_dialog_edit_text_hint));

        AlertDialog.Builder simpleDialog = new AlertDialog.Builder(context);
        simpleDialog.setTitle(context.getResources().getString(R.string.add_stock_dialog_title));
        simpleDialog.setMessage(context.getResources().getString(R.string.add_stock_dialog_message));
        simpleDialog.setView(input);
        simpleDialog.setNegativeButton(context.getResources().getString(R.string.add_stock_dialog_button_add), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogMultiResponse != null) dialogMultiResponse.dialogMultiResponse(input.getText().toString());
                dialog.dismiss();
            }
        });
        simpleDialog.setPositiveButton(context.getResources().getString(R.string.add_stock_dialog__button_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        simpleDialog.create().show();
    }

    public static void showForgotPasswordDialogWithCallback(Context context, final DialogMultiResponse dialogMultiResponse) {
        LinearLayout newLayout = new LinearLayout(context);

        final EditText input1 = new EditText(context);
        final EditText input2 = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input1.setLayoutParams(lp);
        input2.setLayoutParams(lp);
        input1.setHint("Username");
        input2.setHint("Email");
        newLayout.setLayoutParams(lp);
        newLayout.setOrientation(LinearLayout.VERTICAL);
        newLayout.addView(input1);
        newLayout.addView(input2);
        AlertDialog.Builder simpleDialog = new AlertDialog.Builder(context);
        simpleDialog.setTitle("Forgot password");
        simpleDialog.setMessage("Enter username & email");
        simpleDialog.setView(newLayout);
        simpleDialog.setNegativeButton("Forgot", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogMultiResponse != null) dialogMultiResponse.dialogMultiResponse(input1.getText().toString(), input2.getText().toString());
                dialog.dismiss();
            }
        });
        simpleDialog.setPositiveButton(context.getResources().getString(R.string.add_stock_dialog__button_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        simpleDialog.create().show();
    }
}
