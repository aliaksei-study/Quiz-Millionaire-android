package com.example.quizmillionaire;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizmillionaire.adapter.SectionsStatePagerAdapter;
import com.example.quizmillionaire.api.request.LikedQuestionRequest;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.customviewpager.NonSwipeableViewPager;
import com.example.quizmillionaire.fragment.QuestionFragment;
import com.example.quizmillionaire.model.Player;
import com.example.quizmillionaire.model.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {
    private NonSwipeableViewPager viewPager;
    private int numberOfQuestion = 0;
    private List<Question> questions;
    private ImageButton fiftyPercentHelper;
    private ImageButton folksHelper;
    private ImageButton dislikeQuestion;
    private ImageButton likeQuestion;
    private TextView numberOfCorrectAnswers;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        findElementsById();
        Intent intent = getIntent();
        this.questions = (List<Question>) intent.getSerializableExtra("questions");
        setupViewPager(viewPager);
    }

    private void findElementsById() {
        viewPager = findViewById(R.id.container);
        fiftyPercentHelper = findViewById(R.id.fifty_percent_helper_image);
        folksHelper = findViewById(R.id.folks_helper_image);
        dislikeQuestion = findViewById(R.id.dislike_question_image);
        likeQuestion = findViewById(R.id.like_question_image);
        numberOfCorrectAnswers = findViewById(R.id.correct_question_answers_text);
    }

    @Override
    public void onBackPressed() {
    }

    private void setupViewPager(NonSwipeableViewPager viewPager) {
        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        for (int i = 0; i < this.questions.size(); i++) {
            sectionsStatePagerAdapter.addFragment(new QuestionFragment(i + 1,
                    getNumberOfQuestions(), getQuestion(i)), "Question" + i + 1);
        }
        viewPager.setAdapter(sectionsStatePagerAdapter);
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

    public int getNumberOfQuestions() {
        return this.questions.size();
    }

    public Question getQuestion(int questionId) {
        if (questions != null && questions.size() > questionId) {
            return questions.get(questionId);
        }
        return new Question();
    }

    public void setFinishGameActivity() {
        Intent intent = new Intent(this, FinishGameActivity.class);
        intent.putExtra("answeredQuestions", getNumberOfQuestion());
        intent.putExtra("numberOfQuestions", getNumberOfQuestions());
        startActivity(intent);
    }

    public void updateNumberOfCorrectAnswersTextView() {
        numberOfCorrectAnswers.setText(getString(R.string.correct_answers_text_view, getNumberOfQuestion(),
                getNumberOfQuestions()));
    }

    public void setFiftyPercentHelperOnClickListener() {
        this.fiftyPercentHelper.setOnClickListener((view) -> {
            QuestionFragment questionFragment = (QuestionFragment) sectionsStatePagerAdapter
                    .getItem(numberOfQuestion);
            questionFragment.setRedBackgroundInIncorrectAnswers();
            fiftyPercentHelper.setVisibility(ImageButton.GONE);
        });
    }

    public void setLikeQuestionOnClickListener() {
        this.likeQuestion.setOnClickListener((view) -> {
            QuestionFragment questionFragment = (QuestionFragment) sectionsStatePagerAdapter
                    .getItem(numberOfQuestion);
            likeQuestion.setVisibility(ImageButton.GONE);
            sendLikeQuestion(questionFragment.getCurrentQuestionId());
        });
    }

    public void setDislikeQuestionOnClickListener() {
        this.dislikeQuestion.setOnClickListener((view) -> {
            QuestionFragment questionFragment = (QuestionFragment) sectionsStatePagerAdapter
                    .getItem(numberOfQuestion);
            dislikeQuestion.setVisibility(ImageButton.GONE);
            sendDislikeQuestion(questionFragment.getCurrentQuestionId());
        });
    }

    public void unhideLikeDislikeImageButton() {
        likeQuestion.setVisibility(ImageButton.VISIBLE);
        dislikeQuestion.setVisibility(ImageButton.VISIBLE);
    }

    private void sendLikeQuestion(Long questionId) {
        NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
        networkConfiguration.getPlayerApi()
                .likeQuestion(networkConfiguration.getJwtToken(), new LikedQuestionRequest(questionId))
                .enqueue(new Callback<Player>() {
                    @Override
                    public void onResponse(@NotNull Call<Player> call,
                                           @NotNull Response<Player> response) {
                        //todo logger
                    }

                    @Override
                    public void onFailure(@NotNull Call<Player> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Error happened while was trying to like question!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void sendDislikeQuestion(Long questionId) {
        NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
        networkConfiguration.getPlayerApi()
                .dislikeQuestion(networkConfiguration.getJwtToken(), new LikedQuestionRequest(questionId))
                .enqueue(new Callback<Player>() {
                    @Override
                    public void onResponse(@NotNull Call<Player> call,
                                           @NotNull Response<Player> response) {
                        //todo logger
                    }

                    @Override
                    public void onFailure(@NotNull Call<Player> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Error happened while was trying to dislike question!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}