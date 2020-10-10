package com.example.quizmillionaire.utils.validation;

import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizmillionaire.utils.Constants;
import com.google.android.material.textfield.TextInputLayout;

import lombok.NonNull;

public abstract class ErrorTextWatcher implements TextWatcher {
    private TextInputLayout textInputLayout;
    private String errorMessage;
    private Button[] hiddenButtons;

    protected ErrorTextWatcher(@NonNull final TextInputLayout textInputLayout,
                               @NonNull final String errorMessage,
                               final Button... hiddenButtons) {
        this.textInputLayout = textInputLayout;
        this.errorMessage = errorMessage;
        this.hiddenButtons = hiddenButtons;
    }

    protected void hideButtons() {
        for(Button button: hiddenButtons) {
            button.setVisibility(View.GONE);
        }
    }

    protected void unhideButtons() {
        for(Button button: hiddenButtons) {
            button.setVisibility(View.VISIBLE);
        }
    }

    protected void showError(final boolean error) {
        if (!error) {
            unhideButtons();
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
        } else {
            hideButtons();
            textInputLayout.setError(errorMessage);
            textInputLayout.requestFocus();
        }
    }

}
