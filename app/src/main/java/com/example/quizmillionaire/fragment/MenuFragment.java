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

public class MenuFragment extends Fragment {
    private Button signIn;
    private Button signUp;
    private EditText playerEmail;
    private EditText playerPassword;
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
    }

    private void setEditTextChangeListeners(View view) {

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
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
