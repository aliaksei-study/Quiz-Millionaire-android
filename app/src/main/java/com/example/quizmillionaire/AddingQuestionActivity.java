package com.example.quizmillionaire;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class AddingQuestionActivity extends AppCompatActivity {
    private ImageView backButton;
    private TextView addQuestionImage;
    private Spinner questionCategory;
    private TextInputLayout addQuestionText;
    private TextInputLayout addFirstAnswer;
    private TextInputLayout addSecondAnswer;
    private TextInputLayout addThirdAnswer;
    private TextInputLayout addFourthAnswer;
    private CheckBox firstCorrectAnswer;
    private CheckBox secondCorrectAnswer;
    private CheckBox thirdCorrectAnswer;
    private CheckBox fourthCorrectAnswer;
    private Button clearForm;
    private Button addNewQuestion;

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
        backButton.setOnClickListener((view) -> onBackPressed());
    }

    private void findViewsById() {
        this.backButton = findViewById(R.id.back_button_finish_game);
        this.addQuestionImage = findViewById(R.id.add_question_image);
        this.questionCategory = findViewById(R.id.question_category);
        this.addQuestionText = findViewById(R.id.add_question_text);
        this.addFirstAnswer = findViewById(R.id.add_first_answer);
        this.addSecondAnswer = findViewById(R.id.add_second_answer);
        this.addThirdAnswer = findViewById(R.id.add_third_answer);
        this.addFourthAnswer = findViewById(R.id.add_fourth_answer);
        this.firstCorrectAnswer = findViewById(R.id.first_correct_answer);
        this.secondCorrectAnswer = findViewById(R.id.second_correct_answer);
        this.thirdCorrectAnswer = findViewById(R.id.third_correct_answer);
        this.fourthCorrectAnswer = findViewById(R.id.fourth_correct_answer);
        this.clearForm = findViewById(R.id.clear_from);
        this.addNewQuestion = findViewById(R.id.add_new_question);
    }
}
