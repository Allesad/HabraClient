package com.allesad.habraclient.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.allesad.habraclient.R;

/**
 * Created by Allesad on 25.03.2014.
 */
public class DialogUtil {

    public static void showAlertDialog(Context context, String message){
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
}
