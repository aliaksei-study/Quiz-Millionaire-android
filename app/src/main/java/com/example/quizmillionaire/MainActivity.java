package com.example.quizmillionaire;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizmillionaire.adapter.SectionsStatePagerAdapter;
import com.example.quizmillionaire.api.QuestionAPI;
import com.example.quizmillionaire.customviewpager.NonSwipeableViewPager;
import com.example.quizmillionaire.fragment.FinishGameFragment;
import com.example.quizmillionaire.fragment.MenuFragment;
import com.example.quizmillionaire.fragment.QuestionFragment;
import com.example.quizmillionaire.model.Question;
import com.example.quizmillionaire.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NonSwipeableViewPager viewPager;
    private QuestionAPI questionAPI;
    private static int numberOfQuestion = 0;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
        toolbar = findViewById(R.id.app_toolbar);

        Thread thread = new Thread(() -> {
//            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//                URL url = new URL("http://192.168.0.100:8080/questions");
//                OkHttpClient okHttpClient = new OkHttpClient();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//                ResponseBody responseBody = okHttpClient.newCall(request).execute().body();
//                if(responseBody != null) {
//                    Question[] questions = objectMapper.readValue(responseBody.string(), Question[].class);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //Set<Question> questions = questionAPI.getQuestions();
            //System.out.println(questions);
        });
        thread.start();
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
}