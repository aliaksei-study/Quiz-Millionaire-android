package com.example.quizmillionaire.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {

    public static void showPopUp(Context context, String title, String msg, String positiveBtnTxt,
                                 String negativeBtnTxt,
                                 DialogInterface.OnClickListener positiveBtnListener,
                                 DialogInterface.OnClickListener negativeBtnListener){
        final AlertDialog customDialog;
        AlertDialog.Builder customDialogBuilder = new AlertDialog.Builder(context);
        if(title != null) {
            customDialogBuilder.setTitle(title);
        }
        customDialogBuilder.setMessage(msg);
        customDialogBuilder.setPositiveButton(positiveBtnTxt, positiveBtnListener);
        customDialogBuilder.setNegativeButton(negativeBtnTxt, negativeBtnListener);
        customDialog = customDialogBuilder.create();
        customDialog.show();
    }
}
