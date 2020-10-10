package com.example.quizmillionaire.utils.validation;

import android.text.Editable;
import android.util.Patterns;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class EmailTextWatcher extends ErrorTextWatcher {
    private boolean isInvalidEmail = false;

    public EmailTextWatcher(final TextInputLayout textInputLayout, String errorMessage,
                            Button... hiddenButtons) {
        super(textInputLayout, errorMessage, hiddenButtons);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isInvalidEmail = !Patterns.EMAIL_ADDRESS.matcher(s).matches();
        showError(isInvalidEmail);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
