package com.example.quizmillionaire;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizmillionaire.adapter.SectionsStatePagerAdapter;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.customviewpager.NonSwipeableViewPager;
import com.example.quizmillionaire.fragment.FinishGameFragment;
import com.example.quizmillionaire.fragment.MenuFragment;
import com.example.quizmillionaire.fragment.QuestionFragment;
import com.example.quizmillionaire.model.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NonSwipeableViewPager viewPager;
    private static int numberOfQuestion = 0;
    private List<Question> questions;
    private String temporalUsername;
    private int numberOfQuestions;
    private int numberOfEndFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.container);
        loadQuestions(true);
        toolbar = findViewById(R.id.app_toolbar);
    }

    private void loadQuestions(boolean isFirstLoad) {
        NetworkConfiguration.getInstance()
                .getQuestionApi()
                .getQuestions()
                .enqueue(new Callback<List<Question>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Question>> call,
                                           @NotNull Response<List<Question>> response) {
                        setQuestions(response.body());
                        numberOfQuestions = questions.size();
                        numberOfEndFragment = numberOfQuestions + 1;
                        if (isFirstLoad) {
                            setupViewPager(viewPager);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Question>> call, @NotNull Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Can't connect to network!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
    }

    private void setupViewPager(NonSwipeableViewPager viewPager) {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        adapter.addFragment(new MenuFragment(1), "Menu fragment");
        for (int i = 0; i < numberOfQuestions; i++) {
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

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public int getNumberOfEndFragment() {
        return numberOfEndFragment;
    }

    public Question getQuestion(int questionId) {
        if (questions != null && questions.size() > questionId) {
            return questions.get(questionId);
        }
        return new Question();
    }

    public void setTemporalUsername(String username) {
        this.temporalUsername = username;
    }
}