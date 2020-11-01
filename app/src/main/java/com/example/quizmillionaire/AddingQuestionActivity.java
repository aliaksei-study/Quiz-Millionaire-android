package com.example.quizmillionaire;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddingQuestionActivity extends AppCompatActivity {
    private ImageView backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_question_activity);
        findViewsById();
        setOnClickListeners();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void setOnClickListeners() {
        backButton.setOnClickListener((view) -> {
            onBackPressed();
        });
    }

    private void findViewsById() {
        this.backButton = findViewById(R.id.back_button_finish_game);
    }
}
