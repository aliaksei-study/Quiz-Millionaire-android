package com.example.quizmillionaire.fragment;

import android.graphics.Color;
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

import com.example.quizmillionaire.MainActivity;
import com.example.quizmillionaire.R;
import com.example.quizmillionaire.model.Answer;
import com.example.quizmillionaire.model.Question;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

public class QuestionFragment extends Fragment {
    private ImageView questionImage;
    private TextView questionText;
    private Button firstAnswer;
    private Button secondAnswer;
    private Button thirdAnswer;
    private Button fourthAnswer;
    private Question question;
    private int nextFragment;
    private Handler handler;
    private int numberOfEndFragment;
    private final Runnable runnable = () -> {
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            activity.setViewPager(this.nextFragment);
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
    }

    public QuestionFragment(int nextFragment) {
        this.nextFragment = nextFragment;
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
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            int numberOfAnswer = 0;
            this.question = activity.getQuestion(activity.getNumberOfQuestion());
            activity.incrementNumberOfQuestion();
            questionText.setText(this.question.getQuestionText());
            firstAnswer.setText(this.question.getAnswers().get(numberOfAnswer++).getAnswerText());
            secondAnswer.setText(this.question.getAnswers().get(numberOfAnswer++).getAnswerText());
            thirdAnswer.setText(this.question.getAnswers().get(numberOfAnswer++).getAnswerText());
            fourthAnswer.setText(this.question.getAnswers().get(numberOfAnswer).getAnswerText());
            this.numberOfEndFragment = activity.getNumberOfEndFragment();
        }
    }

    private boolean isCorrectAnswerSelected(int selectedAnswerId) {
        return this.question.getAnswers().get(selectedAnswerId).getIsCorrect();
    }

    private void changeColorOfCorrectAnswerButton() {
        List<Answer> answerList = question.getAnswers();
        for(int i = 0; i < answerList.size(); i++) {
            if(answerList.get(i).getIsCorrect()) {
                if(i == 0) {
                    firstAnswer.setBackgroundColor(Color.GREEN);
                } else if(i == 1) {
                    secondAnswer.setBackgroundColor(Color.GREEN);
                } else if(i == 2) {
                    thirdAnswer.setBackgroundColor(Color.GREEN);
                } else {
                    fourthAnswer.setBackgroundColor(Color.GREEN);
                }
            }
        }
    }

    private void setAnswerButtonClickListener(Button button, int numberOfAnswer) {
        if(isCorrectAnswerSelected(numberOfAnswer)) {
            button.setBackgroundColor(Color.GREEN);
        } else {
            button.setBackgroundColor(Color.RED);
            changeColorOfCorrectAnswerButton();
            this.nextFragment = this.numberOfEndFragment;
        }
        handler.postDelayed(runnable, 1000);
        firstAnswer.setEnabled(false);
        secondAnswer.setEnabled(false);
        thirdAnswer.setEnabled(false);
        fourthAnswer.setEnabled(false);
    }

}