package com.example.quizmillionaire.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizmillionaire.QuestionActivity;
import com.example.quizmillionaire.R;
import com.example.quizmillionaire.api.request.PlayerAnswerRequest;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.model.Answer;
import com.example.quizmillionaire.model.AnswerStatistics;
import com.example.quizmillionaire.model.Question;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionFragment extends Fragment {
    private ImageView questionImage;
    private TextView questionText;
    private Button firstAnswer;
    private Button secondAnswer;
    private Button thirdAnswer;
    private Button fourthAnswer;
    private final Question question;
    private int nextFragment;
    private Handler handler;
    private int numberOfQuestions;
    private final Runnable runnable = () -> {
        QuestionActivity activity = (QuestionActivity) getActivity();
        if (activity != null) {
            if (this.nextFragment != this.numberOfQuestions) {
                activity.setViewPager(this.nextFragment);
            } else {
                activity.setFinishGameActivity();
            }
        }
    };

    private void findElementsById(View view) {
        questionImage = view.findViewById(R.id.question_image);
        questionText = view.findViewById(R.id.question);
        firstAnswer = view.findViewById(R.id.first_answer);
        secondAnswer = view.findViewById(R.id.second_answer);
        thirdAnswer = view.findViewById(R.id.third_answer);
        fourthAnswer = view.findViewById(R.id.fourth_answer);
    }

    private void setOnClickListeners() {
        firstAnswer.setOnClickListener((view) -> {
            Long numberOfAnswer = getNumberOfAnswerByAnswerText(firstAnswer.getText().toString());
            setAnswerButtonClickListener(firstAnswer, 0);
            sendAnswerStatistics(this.question.getId(), numberOfAnswer);
        });
        secondAnswer.setOnClickListener((view) -> {
            Long numberOfAnswer = getNumberOfAnswerByAnswerText(secondAnswer.getText().toString());
            setAnswerButtonClickListener(secondAnswer, 1);
            sendAnswerStatistics(this.question.getId(), numberOfAnswer);
        });
        thirdAnswer.setOnClickListener((view) -> {
            Long numberOfAnswer = getNumberOfAnswerByAnswerText(thirdAnswer.getText().toString());
            setAnswerButtonClickListener(thirdAnswer, 2);
            sendAnswerStatistics(this.question.getId(), numberOfAnswer);
        });
        fourthAnswer.setOnClickListener((view) -> {
            Long numberOfAnswer = getNumberOfAnswerByAnswerText(fourthAnswer.getText().toString());
            setAnswerButtonClickListener(fourthAnswer, 3);
            sendAnswerStatistics(this.question.getId(), numberOfAnswer);
        });
        QuestionActivity questionActivity = (QuestionActivity) getActivity();
        if (questionActivity != null) {
            questionActivity.unhideLikeDislikeImageButton();
            questionActivity.setFiftyPercentHelperOnClickListener();
            questionActivity.setLikeQuestionOnClickListener();
            questionActivity.setDislikeQuestionOnClickListener();
        }
    }

    public QuestionFragment(int nextFragment, int numberOfQuestions, Question question) {
        this.nextFragment = nextFragment;
        this.numberOfQuestions = numberOfQuestions;
        this.question = question;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment, container, false);
        handler = new Handler();
        findElementsById(view);
        fillQuestionElements();
        setOnClickListeners();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public Long getCurrentQuestionId() {
        return this.question.getId();
    }

    private void fillQuestionElements() {
        QuestionActivity activity = (QuestionActivity) getActivity();
        if (activity != null) {
            activity.updateNumberOfCorrectAnswersTextView();
            int numberOfAnswer = 0;
            if (question.getImagePath() != null) {
                Picasso.get().load(this.question.getImagePath()).resize(300, 200)
                        .into(questionImage);
            }
            questionText.setText(this.question.getQuestionText());
            firstAnswer.setText(this.question.getAnswers().get(numberOfAnswer++).getAnswerText());
            secondAnswer.setText(this.question.getAnswers().get(numberOfAnswer++).getAnswerText());
            thirdAnswer.setText(this.question.getAnswers().get(numberOfAnswer++).getAnswerText());
            fourthAnswer.setText(this.question.getAnswers().get(numberOfAnswer).getAnswerText());
        }
    }

    private boolean isCorrectAnswerSelected(int selectedAnswerId) {
        return this.question.getAnswers().get(selectedAnswerId).getIsCorrect();
    }

    private void changeColorOfCorrectAnswerButton() {
        List<Answer> answerList = question.getAnswers();
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).getIsCorrect()) {
                changeButtonColor(i, Color.GREEN);
            }
        }
    }

    private void changeButtonColor(int index, int color) {
        if (index == 0) {
            firstAnswer.setBackgroundColor(color);
        } else if (index == 1) {
            secondAnswer.setBackgroundColor(color);
        } else if (index == 2) {
            thirdAnswer.setBackgroundColor(color);
        } else {
            fourthAnswer.setBackgroundColor(color);
        }
    }

    private void disableButton(int index) {
        if (index == 0) {
            firstAnswer.setEnabled(false);
        } else if (index == 1) {
            secondAnswer.setEnabled(false);
        } else if (index == 2) {
            thirdAnswer.setEnabled(false);
        } else {
            fourthAnswer.setEnabled(false);
        }
    }

    private void setAnswerButtonClickListener(Button button, int numberOfAnswer) {
        if (isCorrectAnswerSelected(numberOfAnswer)) {
            QuestionActivity activity = (QuestionActivity) getActivity();
            if (activity != null) {
                button.setBackgroundColor(Color.GREEN);
                activity.incrementNumberOfQuestion();
            }
        } else {
            button.setBackgroundColor(Color.RED);
            changeColorOfCorrectAnswerButton();
            this.nextFragment = this.numberOfQuestions;
        }
        handler.postDelayed(runnable, 1000);
        firstAnswer.setEnabled(false);
        secondAnswer.setEnabled(false);
        thirdAnswer.setEnabled(false);
        fourthAnswer.setEnabled(false);
    }

    public void setRedBackgroundInIncorrectAnswers() {
        final int maxBorderOfGeneratedValue = 4;
        int questionButtonIndex;
        int changedAnswers = 0;
        while (changedAnswers < 2) {
            questionButtonIndex = getRandomNumberInBorders(0, maxBorderOfGeneratedValue);
            if (!question.getAnswers().get(questionButtonIndex).getIsCorrect()) {
                changeButtonColor(questionButtonIndex, Color.RED);
                disableButton(questionButtonIndex);
                changedAnswers++;
            }
        }
    }

    public int getRandomNumberInBorders(int min, int max) {
        return min + (int) (Math.random() * max);
    }

    private Long getNumberOfAnswerByAnswerText(String answerText) {
        List<Answer> answers = question.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            if (answer.getAnswerText().equals(answerText)) {
                return answer.getId();
            }
        }
        return 0L;
    }

    private void sendAnswerStatistics(Long questionId, Long answerId) {
        PlayerAnswerRequest playerAnswerRequest = new PlayerAnswerRequest(answerId, questionId);
        NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
        networkConfiguration.getAnswerStatisticsApi()
                .saveAnswerStatistics(networkConfiguration.getJwtToken(),
                        playerAnswerRequest)
                .enqueue(new Callback<AnswerStatistics>() {
                    @Override
                    public void onResponse(@NotNull Call<AnswerStatistics> call, @NotNull Response<AnswerStatistics> response) {
                        //todo debug message
                    }

                    @Override
                    public void onFailure(@NotNull Call<AnswerStatistics> call, @NotNull Throwable t) {
                        //todo debug message
                    }
                });
    }
}