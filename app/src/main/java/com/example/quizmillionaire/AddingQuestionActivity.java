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
import androidx.fragment.app.FragmentManager;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.quizmillionaire.api.request.AddingQuestionRequest;
import com.example.quizmillionaire.api.response.AddedQuestionResponse;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.dialogs.LoadingDialog;
import com.example.quizmillionaire.model.Answer;
import com.example.quizmillionaire.model.Question;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Uri selectedImage;
    private EditText addQuestionEditText;
    private EditText addFirstAnswerEditText;
    private EditText addSecondAnswerEditText;
    private EditText addThirdAnswerEditText;
    private EditText addFourthAnswerEditText;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_question_activity);
        dialog = new LoadingDialog(AddingQuestionActivity.this);
        this.setFinishOnTouchOutside(false);
        configureCloudinary();
        findViewsById();
        setOnClickListeners();
    }

    private void configureCloudinary() {
        try {
            Map<String, Object> cloudinaryConfig = new HashMap<>();
            cloudinaryConfig.put("cloud_name", "dsnsf4ukx");
            MediaManager.init(this, cloudinaryConfig);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
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
            if (isFieldsFilled()) {
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "Question loading");
                if (selectedImage != null) {
                    uploadToCloudinary();
                } else {
                    sendQuestionToTheServer(null);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Неверные данные! Должны быть " +
                        "заполнены все текстовые поля и выбран один правильный ответ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isFieldsFilled() {
        return isEditTextFilled(addQuestionEditText, addFirstAnswerEditText, addSecondAnswerEditText,
                addThirdAnswerEditText, addFourthAnswerEditText) && isOneCheckBoxFilled(firstCorrectAnswer,
                secondCorrectAnswer, thirdCorrectAnswer, fourthCorrectAnswer);
    }

    private boolean isEditTextFilled(EditText... editTexts) {
        boolean isFilled = true;
        for (EditText editText : editTexts) {
            if (editText != null) {
                isFilled = !editText.getText().toString().equals("");
            }
        }
        return isFilled;
    }

    private boolean isOneCheckBoxFilled(CheckBox... checkBoxes) {
        byte numberOfCheckedCheckBoxes = 0;
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
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
        this.addQuestionEditText = addQuestionText.getEditText();
        this.addFirstAnswerEditText = addFirstAnswer.getEditText();
        this.addSecondAnswerEditText = addSecondAnswer.getEditText();
        this.addThirdAnswerEditText = addThirdAnswer.getEditText();
        this.addFourthAnswerEditText = addFourthAnswer.getEditText();
    }

    private void loadInternalImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void clearFormScreen() {
        resetEditTextValues(addQuestionEditText, addFirstAnswerEditText, addSecondAnswerEditText,
                addThirdAnswerEditText, addFourthAnswerEditText);
        addQuestionImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_add_photo_alternate_white_24dp));
        this.selectedImage = null;
        resetCheckboxes(firstCorrectAnswer, secondCorrectAnswer, thirdCorrectAnswer,
                fourthCorrectAnswer);
    }

    private void resetEditTextValues(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText != null) {
                editText.setText("");
            }
        }
    }

    private void resetCheckboxes(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox != null) {
                checkBox.setChecked(false);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                this.selectedImage = data.getData();

                Picasso.get().load(selectedImage).into(addQuestionImage);
            }
        }
    }

    private void uploadToCloudinary() {
        MediaManager.get().upload(selectedImage).unsigned("ixvorzq8").callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                sendQuestionToTheServer(resultData.get("url").toString());
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                dialog.dismiss();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    private void sendQuestionToTheServer(String uploadedImageUrl) {
        AddingQuestionRequest addingQuestionRequest = new AddingQuestionRequest(uploadedImageUrl,
                addQuestionEditText.getText().toString(), getListOfAnswers());
        NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
        networkConfiguration
                .getQuestionApi()
                .saveQuestion(networkConfiguration.getJwtToken(), addingQuestionRequest)
                .enqueue(new Callback<AddedQuestionResponse>() {
                    @Override
                    public void onResponse(Call<AddedQuestionResponse> call, Response<AddedQuestionResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddingQuestionActivity.this, "Вопрос успешно добавлен",
                                    Toast.LENGTH_LONG).show();
                        }
                        clearFormScreen();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AddedQuestionResponse> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(AddingQuestionActivity.this, "Ошибка при добавлении вопроса. Проверьте подключение к интернету",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private List<Answer> getListOfAnswers() {
        Answer firstAnswer = new Answer(addFirstAnswerEditText.getText().toString(),
                firstCorrectAnswer.isChecked());
        Answer secondAnswer = new Answer(addSecondAnswerEditText.getText().toString(),
                secondCorrectAnswer.isChecked());
        Answer thirdAnswer = new Answer(addThirdAnswerEditText.getText().toString(),
                thirdCorrectAnswer.isChecked());
        Answer fourthAnswer = new Answer(addFourthAnswerEditText.getText().toString(),
                fourthCorrectAnswer.isChecked());
        return Stream.of(firstAnswer, secondAnswer, thirdAnswer, fourthAnswer)
                .collect(Collectors.toList());
    }
}
