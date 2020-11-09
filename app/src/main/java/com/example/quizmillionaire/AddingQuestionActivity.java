package com.example.quizmillionaire;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class AddingQuestionActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private ImageView backButton;
    private ImageView addQuestionImage;
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
        addQuestionImage.setOnClickListener((view) -> loadInternalImage());
        clearForm.setOnClickListener((view) -> clearFormScreen());
        addNewQuestion.setOnClickListener((view) -> {
            if(isFieldsFilled()) {
                System.out.println("Good job");
            } else {
                Toast.makeText(getApplicationContext(), "Неверные данные! Должны быть " +
                        "заполнены все текстовые поля и выбран один правильный ответ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isFieldsFilled() {
        EditText addQuestionEditText = addQuestionText.getEditText();
        EditText addFirstAnswerEditText = addFirstAnswer.getEditText();
        EditText addSecondAnswerEditText = addSecondAnswer.getEditText();
        EditText addThirdAnswerEditText = addThirdAnswer.getEditText();
        EditText addFourthAnswerEditText = addFourthAnswer.getEditText();
        return isEditTextFilled(addQuestionEditText, addFirstAnswerEditText, addSecondAnswerEditText,
                addThirdAnswerEditText, addFourthAnswerEditText) && isOneCheckBoxFilled(firstCorrectAnswer,
                secondCorrectAnswer, thirdCorrectAnswer, fourthCorrectAnswer);
    }

    private boolean isEditTextFilled(EditText... editTexts) {
        boolean isFilled = true;
        for(EditText editText: editTexts) {
            if(editText != null) {
                isFilled = !editText.getText().toString().equals("");
            }
        }
        return isFilled;
    }

    private boolean isOneCheckBoxFilled(CheckBox... checkBoxes) {
        byte numberOfCheckedCheckBoxes = 0;
        for(CheckBox checkBox: checkBoxes) {
            if(checkBox.isChecked()) {
                numberOfCheckedCheckBoxes++;
            }
        }
        return numberOfCheckedCheckBoxes == 1;
    }

    private void findViewsById() {
        this.backButton = findViewById(R.id.back_button_finish_game);
        this.addQuestionImage = findViewById(R.id.add_question_image);
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

    private void loadInternalImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void clearFormScreen() {
        EditText addQuestionEditText = addQuestionText.getEditText();
        EditText addFirstAnswerEditText = addFirstAnswer.getEditText();
        EditText addSecondAnswerEditText = addSecondAnswer.getEditText();
        EditText addThirdAnswerEditText = addThirdAnswer.getEditText();
        EditText addFourthAnswerEditText = addFourthAnswer.getEditText();
        resetEditTextValues(addQuestionEditText, addFirstAnswerEditText, addSecondAnswerEditText,
                addThirdAnswerEditText, addFourthAnswerEditText);
        addQuestionImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_add_photo_alternate_white_24dp));
        resetCheckboxes(firstCorrectAnswer, secondCorrectAnswer, thirdCorrectAnswer,
                fourthCorrectAnswer);
    }

    private void resetEditTextValues(EditText... editTexts) {
        for(EditText editText: editTexts) {
            if(editText != null) {
                editText.setText("");
            }
        }
    }

    private void resetCheckboxes(CheckBox... checkBoxes) {
        for(CheckBox checkBox: checkBoxes) {
            if(checkBox != null) {
                checkBox.setChecked(false);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageURI = data.getData();

                Picasso.get().load(selectedImageURI).into(addQuestionImage);
            }
        }
    }
}
