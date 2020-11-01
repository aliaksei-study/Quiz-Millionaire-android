package com.example.quizmillionaire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmillionaire.api.AuthAPI;
import com.example.quizmillionaire.api.request.PrincipalAuthRequest;
import com.example.quizmillionaire.api.response.JwtResponse;
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
    private CheckBox checkBox;
    private TextInputLayout playerEmail;
    private TextInputLayout playerPassword;
    private Button startGame;


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
        playerEmail = findViewById(R.id.email);
        playerPassword = findViewById(R.id.password);
        startGame = findViewById(R.id.start_game);
        mAdView = findViewById(R.id.adView);
        checkBox = findViewById(R.id.register_check_box);
    }

    private void setEditTextChangeListeners() {
        ErrorTextWatcher notEmptyStringTextWatcher = new NotEmptyStringTextWatcher(playerPassword,
                "Поле обязатиельно", startGame);
        ErrorTextWatcher emailTextWatcher = new EmailTextWatcher(playerEmail,
                "Неверный адрес", startGame);
        EditText passwordEditText = playerPassword.getEditText();
        EditText emailEditText = playerEmail.getEditText();
        if (passwordEditText != null) {
            passwordEditText.addTextChangedListener(notEmptyStringTextWatcher);
        }
        if (emailEditText != null) {
            emailEditText.addTextChangedListener(emailTextWatcher);
        }
    }

    private void loadQuestions(Intent intent, String jwtToken) {
        NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
        networkConfiguration
                .getQuestionApi()
                .getQuestions("Bearer " + jwtToken)
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
                                "Нет подключения к интернету!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
    }

    private void registerPrincipal(Intent intent) {
        PrincipalAuthRequest authRequest = new PrincipalAuthRequest
                (playerEmail.getEditText().getText().toString(),
                        playerPassword.getEditText().getText().toString());
        AuthAPI authAPI = NetworkConfiguration.getInstance().getAuthApi();
        Call<JwtResponse> responseCall = checkBox.isChecked() ? authAPI.register(authRequest) :
                authAPI.login(authRequest);
        responseCall.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if(response.body() != null) {
                    loadQuestions(intent, response.body().getJwtToken());
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Нет подключения к интернету", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setOnClickListeners() {
        startGame.setOnClickListener((v) -> {
            System.out.println(playerEmail.getEditText().getText().toString());
            System.out.println(playerPassword.getEditText().getText().toString());
            if(!(isFieldEmpty(playerEmail.getEditText().getText().toString()) ||
                    isFieldEmpty(playerPassword.getEditText().getText().toString()))) {
                Intent intent = new Intent(this, QuestionActivity.class);
                registerPrincipal(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Поля должны быть заполнены!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isFieldEmpty(String field) {
        return field.equals("");
    }
}
