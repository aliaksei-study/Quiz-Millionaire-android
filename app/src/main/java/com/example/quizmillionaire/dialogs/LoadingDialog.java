package com.example.quizmillionaire.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.quizmillionaire.R;

import org.jetbrains.annotations.NotNull;

public class LoadingDialog extends DialogFragment {
    private final Context context;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    @Override
    public @NotNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.loading);
        return builder.create();
    }
}
