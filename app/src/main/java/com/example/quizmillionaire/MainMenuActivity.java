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
import com.example.quizmillionaire.api.request.PlayerAuthRequest;
import com.example.quizmillionaire.api.response.ApiExceptionResponse;
import com.example.quizmillionaire.api.response.JwtResponse;
import com.example.quizmillionaire.config.AdMobConfiguration;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.model.Question;
import com.example.quizmillionaire.utils.validation.EmailTextWatcher;
import com.example.quizmillionaire.utils.validation.ErrorTextWatcher;
import com.example.quizmillionaire.utils.validation.NotEmptyStringTextWatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.ads.AdView;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
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
                "Поле обязательно", startGame);
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
                        if(response.isSuccessful()) {
                            intent.putExtra("questions", (Serializable) response.body());
                            startActivity(intent);
                        } else {
                            if(response.errorBody() != null) {
                                showResponseErrorMessage(response.errorBody());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Question>> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "Ошибка сервера", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void registerPrincipal(Intent intent) {
        PlayerAuthRequest authRequest = new PlayerAuthRequest
                (playerEmail.getEditText().getText().toString(),
                        playerPassword.getEditText().getText().toString());
        AuthAPI authAPI = NetworkConfiguration.getInstance().getAuthApi();
        Call<JwtResponse> responseCall = checkBox.isChecked() ? authAPI.register(authRequest) :
                authAPI.login(authRequest);
        responseCall.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(@NotNull Call<JwtResponse> call, @NotNull Response<JwtResponse> response) {
                if(response.isSuccessful()) {
                    String jwtToken = response.body().getJwtToken();
                    NetworkConfiguration.getInstance().setJwtToken("Bearer " + jwtToken);
                    loadQuestions(intent, jwtToken);
                } else {
                    if(response.errorBody() != null) {
                        showResponseErrorMessage(response.errorBody());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<JwtResponse> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Нет подключения к интернету", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showResponseErrorMessage(ResponseBody responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ApiExceptionResponse exceptionResponse = objectMapper.readValue(responseBody.string(), ApiExceptionResponse.class);
            Toast.makeText(getApplicationContext(), exceptionResponse.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOnClickListeners() {
        startGame.setOnClickListener((v) -> {
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
