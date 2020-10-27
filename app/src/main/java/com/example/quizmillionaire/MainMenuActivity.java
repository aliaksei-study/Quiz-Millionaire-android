package com.example.quizmillionaire;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmillionaire.config.AdMobConfiguration;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.model.Question;
import com.example.quizmillionaire.utils.validation.EmailTextWatcher;
import com.example.quizmillionaire.utils.validation.ErrorTextWatcher;
import com.example.quizmillionaire.utils.validation.NotEmptyStringTextWatcher;
import com.google.android.gms.ads.AdView;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends AppCompatActivity {
    private AdView mAdView;
    private Button signIn;
    private Button signUp;
    private TextInputLayout playerEmail;
    private TextInputLayout playerPassword;
    private Button startGame;
    private Button cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findElementsByIds();
        setOnClickListeners();
        setEditTextChangeListeners();
        AdMobConfiguration.configureAdMob(getApplicationContext(), mAdView);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }

    private void findElementsByIds() {
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        playerEmail = findViewById(R.id.email);
        playerPassword = findViewById(R.id.password);
        startGame = findViewById(R.id.start_game);
        cancel = findViewById(R.id.cancel);
        mAdView = findViewById(R.id.adView);
    }

    private void setEditTextChangeListeners() {
        ErrorTextWatcher notEmptyStringTextWatcher = new NotEmptyStringTextWatcher(playerPassword,
                "This field is required", startGame, cancel);
        ErrorTextWatcher emailTextWatcher = new EmailTextWatcher(playerEmail,
                "Invalid email", startGame, cancel);
        EditText passwordEditText = playerPassword.getEditText();
        EditText emailEditText = playerEmail.getEditText();
        if (passwordEditText != null) {
            passwordEditText.addTextChangedListener(notEmptyStringTextWatcher);
        }
        if (emailEditText != null) {
            emailEditText.addTextChangedListener(emailTextWatcher);
        }
    }

    private void loadQuestions(Intent intent) {
        NetworkConfiguration.getInstance()
                .getQuestionApi()
                .getQuestions()
                .enqueue(new Callback<List<Question>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Question>> call,
                                           @NotNull Response<List<Question>> response) {
                        intent.putExtra("questions", (Serializable) response.body());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Question>> call, @NotNull Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Can't connect to network!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
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
        startGame.setOnClickListener((v) -> {
            Intent intent = new Intent(this, QuestionActivity.class);
            loadQuestions(intent);
        });
    }
}
