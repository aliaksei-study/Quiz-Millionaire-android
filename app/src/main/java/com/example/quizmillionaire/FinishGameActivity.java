package com.example.quizmillionaire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmillionaire.config.AdMobConfiguration;
import com.google.android.gms.ads.AdView;

public class FinishGameActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView firstStar;
    private ImageView secondStar;
    private ImageView thirdStar;
    private Button leaderBoard;
    private Button tryAgain;
    private AdView adView;
    private int numberOfAnsweredQuestions;
    private int numberOfQuestions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_game_activity);
        Intent intent = getIntent();
        this.numberOfAnsweredQuestions = intent.getIntExtra("answeredQuestions", 0);
        this.numberOfQuestions = intent.getIntExtra("numberOfQuestions", 0);
        findViewsById();
        AdMobConfiguration.configureAdMob(getApplicationContext(), adView);
        initializeView();
        setOnClickListeners();
    }

    @Override
    public void onBackPressed() {
    }

    private void initializeView() {
        if(numberOfAnsweredQuestions == 0) {
            firstStar.setVisibility(View.GONE);
            secondStar.setVisibility(View.GONE);
            thirdStar.setVisibility(View.GONE);
        } else {
            if(numberOfAnsweredQuestions < numberOfQuestions / 3) {
                secondStar.setVisibility(View.GONE);
                thirdStar.setVisibility(View.GONE);
            } else if(numberOfAnsweredQuestions < (numberOfQuestions / 3) * 2) {
                thirdStar.setVisibility(View.GONE);
            }
        }
        textView.setText(getString(R.string.finish_game_results_text_view, numberOfAnsweredQuestions,
                numberOfQuestions));
    }

    private void setOnClickListeners() {
        tryAgain.setOnClickListener((view) -> {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        });
        leaderBoard.setOnClickListener((view) -> {
            Intent intent = new Intent(this, LeaderBoardActivity.class);
            startActivity(intent);
        });
    }

    private void findViewsById() {
        textView = findViewById(R.id.result_text);
        firstStar = findViewById(R.id.first_star);
        secondStar = findViewById(R.id.second_star);
        thirdStar = findViewById(R.id.third_star);
        leaderBoard = findViewById(R.id.leaderboard);
        tryAgain = findViewById(R.id.try_again);
        adView = findViewById(R.id.finish_adView);
    }
}
