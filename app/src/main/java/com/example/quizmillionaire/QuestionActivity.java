package com.example.quizmillionaire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.quizmillionaire.adapter.SectionsStatePagerAdapter;
import com.example.quizmillionaire.api.request.LikedQuestionRequest;
import com.example.quizmillionaire.config.NetworkConfiguration;
import com.example.quizmillionaire.customviewpager.NonSwipeableViewPager;
import com.example.quizmillionaire.dialogs.AnswerHistogramDialog;
import com.example.quizmillionaire.dialogs.DialogUtils;
import com.example.quizmillionaire.fragment.QuestionFragment;
import com.example.quizmillionaire.model.Player;
import com.example.quizmillionaire.model.Question;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import lombok.NonNull;
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
    private RewardedAd rewardedAd;
    private int numberOfRewardedAds = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        findElementsById();
        loadRewardedAd();
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
            if (numberOfRewardedAds != 0) {
                DialogUtils.showPopUp(QuestionActivity.this, null,
                        "Оставить подсказку и посмотреть рекламу?", "Использовать подсказку", "Посмотреть рекламу",
                        (dialog, which) -> {
                            useFiftyPercentHelper();
                            fiftyPercentHelper.setVisibility(ImageButton.GONE);
                        }, (dialog, which) -> {
                            RewardedAdCallback adCallback = new RewardedAdCallback() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem reward) {
                                    useFiftyPercentHelper();
                                    numberOfRewardedAds = 0;
                                }
                            };
                            rewardedAd.show(QuestionActivity.this, adCallback);
                        });
            } else {
                useFiftyPercentHelper();
                fiftyPercentHelper.setVisibility(ImageButton.GONE);
            }
        });
    }

    public void useFiftyPercentHelper() {
        QuestionFragment questionFragment = (QuestionFragment) sectionsStatePagerAdapter
                .getItem(numberOfQuestion);
        questionFragment.setRedBackgroundInIncorrectAnswers();
    }

    public void setFolksHelperOnClickListener() {
        this.folksHelper.setOnClickListener((view) -> {
            QuestionFragment questionFragment = (QuestionFragment) sectionsStatePagerAdapter
                    .getItem(numberOfQuestion);

            getAnswersHistogramByQuestion(questionFragment.getCurrentQuestion());

            folksHelper.setVisibility(ImageButton.GONE);
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

    private void getAnswersHistogramByQuestion(Question question) {
        NetworkConfiguration networkConfiguration = NetworkConfiguration.getInstance();
        networkConfiguration.getAnswerStatisticsApi()
                .getAnswersHistogram(networkConfiguration.getJwtToken(), question.getId())
                .enqueue(new Callback<Map<Long, Integer>>() {
                    @Override
                    public void onResponse(Call<Map<Long, Integer>> call, Response<Map<Long, Integer>> response) {
                        if (response.isSuccessful()) {
                            FragmentManager manager = getSupportFragmentManager();
                            AnswerHistogramDialog myDialogFragment = new AnswerHistogramDialog(response.body(), question);
                            myDialogFragment.show(manager, "myDialog");
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<Long, Integer>> call, Throwable t) {

                    }
                });
    }

    private void loadRewardedAd() {
        MobileAds.initialize(QuestionActivity.this, initializationStatus -> {
        });
        this.rewardedAd = new RewardedAd(QuestionActivity.this, "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                super.onRewardedAdFailedToLoad(adError);
            }
        };
        this.rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }
}