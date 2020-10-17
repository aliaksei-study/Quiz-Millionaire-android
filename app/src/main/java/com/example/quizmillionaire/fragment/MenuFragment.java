package com.example.quizmillionaire.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.quizmillionaire.MainActivity;
import com.example.quizmillionaire.R;
import com.example.quizmillionaire.utils.validation.EmailTextWatcher;
import com.example.quizmillionaire.utils.validation.ErrorTextWatcher;
import com.example.quizmillionaire.utils.validation.NotEmptyStringTextWatcher;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;
import java.util.List;

public class MenuFragment extends Fragment {
    private AdView mAdView;
    private Button signIn;
    private Button signUp;
    private TextInputLayout playerEmail;
    private TextInputLayout playerPassword;
    private Button startGame;
    private Button cancel;
    private Handler handler;
    private Runnable runnable = () -> ((MainActivity)getActivity()).setViewPager(1);

    public MenuFragment() {
    }

    private void findElementsByIds(View view) {
        signIn = view.findViewById(R.id.sign_in);
        signUp = view.findViewById(R.id.sign_up);
        playerEmail = view.findViewById(R.id.email);
        playerPassword = view.findViewById(R.id.password);
        startGame = view.findViewById(R.id.start_game);
        cancel = view.findViewById(R.id.cancel);
        mAdView = view.findViewById(R.id.adView);
    }

    private void setEditTextChangeListeners() {
        ErrorTextWatcher notEmptyStringTextWatcher = new NotEmptyStringTextWatcher(playerPassword,
                "This field is required", startGame, cancel);
        ErrorTextWatcher emailTextWatcher = new EmailTextWatcher(playerEmail,
                "Invalid email", startGame, cancel);
        EditText passwordEditText = playerPassword.getEditText();
        EditText emailEditText = playerEmail.getEditText();
        if(passwordEditText != null) {
            passwordEditText.addTextChangedListener(notEmptyStringTextWatcher);
        }
        if(emailEditText != null) {
            emailEditText.addTextChangedListener(emailTextWatcher);
        }
    }

    private void setOnClickListeners() {
        signIn.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
            signUp.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        });
        signUp.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.VISIBLE);
            playerPassword.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
            signUp.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        });
        cancel.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.GONE);
            playerPassword.setVisibility(View.GONE);
            signIn.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
        });
        startGame.setOnClickListener((v) -> {
            handler.postDelayed(runnable, 5000);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);
        handler = new Handler();
        findElementsByIds(view);
        setOnClickListeners();
        setEditTextChangeListeners();
        MobileAds.initialize(getContext(), initializationStatus -> {});
        List<String> testDeviceIds = Collections.singletonList("B33C81ED35A25990C8EC1268B686C1F0");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
