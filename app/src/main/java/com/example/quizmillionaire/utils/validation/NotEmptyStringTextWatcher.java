package com.example.quizmillionaire.utils.validation;

import android.text.Editable;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class NotEmptyStringTextWatcher extends ErrorTextWatcher {
    private boolean isEmptyString = false;

    public NotEmptyStringTextWatcher(final TextInputLayout textInputLayout, String errorMessage) {
        super(textInputLayout, errorMessage);
    }

    public NotEmptyStringTextWatcher(final TextInputLayout textInputLayout, String errorMessage,
                                     Button... hiddenButtons) {
        super(textInputLayout, errorMessage, hiddenButtons);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isEmptyString = s.length() == 0;
        super.showError(isEmptyString);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
