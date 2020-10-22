package com.example.quizmillionaire.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quizmillionaire.MainActivity;
import com.example.quizmillionaire.R;
import com.example.quizmillionaire.config.AdMobConfiguration;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.model.Question;
import com.example.quizmillionaire.utils.validation.EmailTextWatcher;
import com.example.quizmillionaire.utils.validation.ErrorTextWatcher;
import com.example.quizmillionaire.utils.validation.NotEmptyStringTextWatcher;
import com.google.android.gms.ads.AdView;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {
    private AdView mAdView;
    private Button signIn;
    private Button signUp;
    private TextInputLayout playerEmail;
    private TextInputLayout playerPassword;
    private Button startGame;
    private Button cancel;
    private Handler handler;
    private Runnable runnable = () -> {
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            activity.setViewPager(this.nextFragment);
        }
    };
    private int nextFragment;

    public MenuFragment(int nextFragment) {
        this.nextFragment = nextFragment;
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
            startGame.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        });
        signUp.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.VISIBLE);
            playerPassword.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
            startGame.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        });
        cancel.setOnClickListener((v) -> {
            playerEmail.setVisibility(View.GONE);
            playerPassword.setVisibility(View.GONE);
            signIn.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.VISIBLE);
            startGame.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        });
        startGame.setOnClickListener((v) -> NetworkConfiguration.getInstance()
                .getQuestionApi()
                .getQuestions()
                .enqueue(new Callback<List<Question>>(){
                    @Override
                    public void onResponse(@NotNull Call<List<Question>> call,
                                           @NotNull Response<List<Question>> response) {
                        MainActivity activity = (MainActivity) getActivity();
                        if(activity != null) {
                            activity.setQuestions(response.body());
                            activity.setTemporalUsername(playerEmail.toString());
                            handler.postDelayed(runnable, 1000);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Question>> call, @NotNull Throwable t) {
                        Toast toast = Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);
        handler = new Handler();
        findElementsByIds(view);
        setOnClickListeners();
        setEditTextChangeListeners();
        AdMobConfiguration.configureAdMob(getContext(), mAdView);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}