package com.example.quizmillionaire.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.quizmillionaire.MainActivity;
import com.example.quizmillionaire.R;

public class MenuFragment extends Fragment {
    private Button signIn;
    private Button createNewAccount;
    private EditText playerEmail;
    private EditText playerPassword;
    private LinearLayout playerFormButtons;
    private Button startGame;
    private Button cancel;
    private Button settings;
    private Handler handler;
    private Runnable runnable = () -> ((MainActivity)getActivity()).setViewPager(1);

    public MenuFragment() {
    }

    private void findElementsByIds(View view) {
        signIn = view.findViewById(R.id.sign_in);
        createNewAccount = view.findViewById(R.id.new_account);
        playerEmail = view.findViewById(R.id.email);
        playerPassword = view.findViewById(R.id.password);
        playerFormButtons = view.findViewById(R.id.player_form_buttons);
        startGame = view.findViewById(R.id.start_game);
        cancel = view.findViewById(R.id.cancel);
        settings = view.findViewById(R.id.settings);
    }



    private void setOnClickListeners() {
        signIn.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.VISIBLE);
            playerFormButtons.setVisibility(View.VISIBLE);
        });
        createNewAccount.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.VISIBLE);
            playerPassword.setVisibility(View.VISIBLE);
            playerFormButtons.setVisibility(View.VISIBLE);
        });
        cancel.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.GONE);
            playerPassword.setVisibility(View.GONE);
            playerFormButtons.setVisibility(View.GONE);
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
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
