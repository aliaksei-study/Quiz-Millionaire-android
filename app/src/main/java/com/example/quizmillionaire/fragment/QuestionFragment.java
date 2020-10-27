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
import com.example.quizmillionaire.model.Answer;
import com.example.quizmillionaire.model.Question;
import com.squareup.picasso.Picasso;

import java.util.List;

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
            setAnswerButtonClickListener(firstAnswer, 0);
        });
        secondAnswer.setOnClickListener((view) -> {
            setAnswerButtonClickListener(secondAnswer, 1);
        });
        thirdAnswer.setOnClickListener((view) -> {
            setAnswerButtonClickListener(thirdAnswer, 2);
        });
        fourthAnswer.setOnClickListener((view) -> {
            setAnswerButtonClickListener(fourthAnswer, 3);
        });
        QuestionActivity questionActivity = (QuestionActivity) getActivity();
        if(questionActivity != null) {
            questionActivity.setFiftyPercentHelperOnClickListener();
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

    private void fillQuestionElements() {
        QuestionActivity activity = (QuestionActivity) getActivity();
        if (activity != null) {
            activity.updateNumberOfCorrectAnswersTextView();
            int numberOfAnswer = 0;
            if (question.getImagePath() != null) {
                Picasso.get().load(this.question.getImagePath()).into(questionImage);
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
        while(changedAnswers < 2) {
            questionButtonIndex = getRandomNumberInBorders(0, maxBorderOfGeneratedValue);
            if(!question.getAnswers().get(questionButtonIndex).getIsCorrect()) {
                changeButtonColor(questionButtonIndex, Color.RED);
                disableButton(questionButtonIndex);
                changedAnswers++;
            }
        }
    }

    public int getRandomNumberInBorders(int min, int max) {
        return min + (int) (Math.random() * max);
    }
}