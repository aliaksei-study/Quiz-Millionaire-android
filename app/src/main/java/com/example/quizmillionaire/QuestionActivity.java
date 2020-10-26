package com.example.quizmillionaire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizmillionaire.adapter.SectionsStatePagerAdapter;
import com.example.quizmillionaire.customviewpager.NonSwipeableViewPager;
import com.example.quizmillionaire.fragment.QuestionFragment;
import com.example.quizmillionaire.model.Question;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private NonSwipeableViewPager viewPager;
    private int numberOfQuestion = 0;
    private List<Question> questions;
    private ImageButton fiftyPercentHelper;
    private ImageButton folksHelper;
    private ImageButton dislikeQuestion;
    private ImageButton likeQuestion;
    private TextView numberOfCorrectAnswers;

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
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        for (int i = 0; i < this.questions.size(); i++) {
            adapter.addFragment(new QuestionFragment(i + 1, getNumberOfQuestions(), getQuestion(i)), "Question" + i + 1);
        }
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
}