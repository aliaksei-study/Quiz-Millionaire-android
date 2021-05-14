package com.example.quizmillionaire;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmillionaire.adapter.StatisticsLeaderBoardAdapter;
import com.example.quizmillionaire.model.Statistics;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

        Intent intent = getIntent();
        List<Statistics> statistics = (List<Statistics>) intent.getSerializableExtra("statistics");

        if(statistics != null) {
            statistics.sort((o1, o2) -> o2.getScore() - o1.getScore());
        }

        ImageView backButton = findViewById(R.id.back_button);
        RecyclerView playerLeaderBoard = findViewById(R.id.player_leaderboard);

        StatisticsLeaderBoardAdapter adapter = new StatisticsLeaderBoardAdapter(statistics);
        playerLeaderBoard.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        playerLeaderBoard.setLayoutManager(layoutManager);
        playerLeaderBoard.setHasFixedSize(true);

        backButton.setOnClickListener((view) -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
