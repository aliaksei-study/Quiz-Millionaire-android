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
import com.example.quizmillionaire.config.AdMobConfiguration;
import com.google.android.gms.ads.AdView;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FinishGameFragment extends Fragment {
    private TextView textView;
    private ImageView firstStar;
    private ImageView secondStar;
    private ImageView thirdStar;
    private Button leaderBoard;
    private Button tryAgain;
    private AdView adView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finish_game_fragment, container, false);
        findViewsById(view);
        AdMobConfiguration.configureAdMob(getContext(), adView);
        MainActivity activity = (MainActivity)getActivity();
        if(activity != null) {
            initializeView(activity);
            setOnClickListeners(activity);
        }
        return view;
    }

    private void initializeView(MainActivity activity) {
        int correctAnswers = activity.getNumberOfQuestion() - 2;
        int numberOfQuestions = activity.getNumberOfQuestions();
        if(correctAnswers == 0) {
            firstStar.setVisibility(View.GONE);
            secondStar.setVisibility(View.GONE);
            thirdStar.setVisibility(View.GONE);
        } else {
            if(correctAnswers < numberOfQuestions / 3) {
                secondStar.setVisibility(View.GONE);
                thirdStar.setVisibility(View.GONE);
            } else if(correctAnswers < (numberOfQuestions / 3) * 2) {
                thirdStar.setVisibility(View.GONE);
            }
        }
        textView.setText("Поздравляем! Вы набрали " + correctAnswers + " баллов из " + numberOfQuestions);
    }

    private void setOnClickListeners(MainActivity activity) {
        tryAgain.setOnClickListener((v) -> activity.setViewPager(0));
    }

    private void findViewsById(View view) {
        textView = view.findViewById(R.id.result_text);
        firstStar = view.findViewById(R.id.first_star);
        secondStar = view.findViewById(R.id.second_star);
        thirdStar = view.findViewById(R.id.third_star);
        leaderBoard = view.findViewById(R.id.leaderboard);
        tryAgain = view.findViewById(R.id.try_again);
        adView = view.findViewById(R.id.finish_adView);
    }
}
