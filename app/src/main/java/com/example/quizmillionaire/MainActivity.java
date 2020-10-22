package com.example.quizmillionaire;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizmillionaire.adapter.SectionsStatePagerAdapter;
import com.example.quizmillionaire.customviewpager.NonSwipeableViewPager;
import com.example.quizmillionaire.fragment.FinishGameFragment;
import com.example.quizmillionaire.fragment.MenuFragment;
import com.example.quizmillionaire.fragment.QuestionFragment;
import com.example.quizmillionaire.model.Question;
import com.example.quizmillionaire.utils.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NonSwipeableViewPager viewPager;
    private static int numberOfQuestion = 0;
    private List<Question> questions;
    private String temporalUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
        toolbar = findViewById(R.id.app_toolbar);
    }

    private void setupViewPager(NonSwipeableViewPager viewPager) {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager.setPagingEnabled(true);
        adapter.addFragment(new MenuFragment(1), "Menu fragment");
        for(int i = 0; i < Constants.NUMBER_OF_QUESTIONS; i++) {
            adapter.addFragment(new QuestionFragment(i + 2), "Question" + i + 1);
        }
        adapter.addFragment(new FinishGameFragment(), "Finish Game fragment");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        viewPager.setCurrentItem(fragmentNumber);
    }

    public void incrementNumberOfQuestion() {
        numberOfQuestion++;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestion() {
        return questions.get(numberOfQuestion - 1);
    }

    public void setTemporalUsername(String username) {
        this.temporalUsername = username;
    }
}