package com.example.quizmillionaire;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmillionaire.adapter.PlayerLeaderBoardAdapter;
import com.example.quizmillionaire.model.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeaderBoardActivity extends AppCompatActivity {
    private RecyclerView playerLeaderBoard;
    private ImageView backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Player> players = Stream.of(new Player("ff")).collect(Collectors.toList());
        setContentView(R.layout.leaderboard_activity);
        backButton = findViewById(R.id.back_button);
        playerLeaderBoard = findViewById(R.id.player_leaderboard);
        PlayerLeaderBoardAdapter adapter = new PlayerLeaderBoardAdapter(players);
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
