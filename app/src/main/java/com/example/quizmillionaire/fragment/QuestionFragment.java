package com.example.quizmillionaire.fragment;

import android.os.Bundle;
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
import com.example.quizmillionaire.model.Question;

public class QuestionFragment extends Fragment {
    private ImageView questionImage;
    private TextView questionText;
    private TextView questionCost;
    private Button firstAnswer;
    private Button secondAnswer;
    private Button thirdAnswer;
    private Button fourthAnswer;

    private Question question;
    private int nextFragment;
    private Runnable runnable = () -> {
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            activity.setViewPager(this.nextFragment);
        }
    };

    private void findElementsById(View view) {
        questionImage = view.findViewById(R.id.question_image);
        questionText = view.findViewById(R.id.question);
        questionCost = view.findViewById(R.id.question_cost);
        firstAnswer = view.findViewById(R.id.first_answer);
        secondAnswer = view.findViewById(R.id.second_answer);
        thirdAnswer = view.findViewById(R.id.third_answer);
        fourthAnswer = view.findViewById(R.id.fourth_answer);
    }

    private void setOnClickListeners() {
        firstAnswer.setOnClickListener((view) -> {
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
        findElementsById(view);
        setOnClickListeners();
        return view;
    }

}