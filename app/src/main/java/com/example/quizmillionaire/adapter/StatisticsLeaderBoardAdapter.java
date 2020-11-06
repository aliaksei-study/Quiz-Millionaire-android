package com.example.quizmillionaire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmillionaire.R;
import com.example.quizmillionaire.model.Player;
import com.example.quizmillionaire.model.Statistics;

import java.util.List;

public class StatisticsLeaderBoardAdapter extends RecyclerView.Adapter<StatisticsLeaderBoardAdapter.StatisticsLeaderBoardViewHolder> {
    private final List<Statistics> statistics;

    public StatisticsLeaderBoardAdapter(List<Statistics> statistics) {
        this.statistics = statistics;
    }

    @NonNull
    @Override
    public StatisticsLeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForLeaderBoardItem = R.layout.leaderboard_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForLeaderBoardItem, parent, false);

        return new StatisticsLeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsLeaderBoardViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return statistics.size() + 1;
    }

    class StatisticsLeaderBoardViewHolder extends RecyclerView.ViewHolder {
        private static final String POSITION_HEADER = "№";
        private static final String PLAYER_EMAIL_HEADER = "Электронная почта";
        private static final String PLAYER_SCORE_HEADER = "Баллы";

        private final TextView playerPosition;
        private final TextView playerEmail;
        private final TextView playerScore;

        public StatisticsLeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            playerPosition = itemView.findViewById(R.id.player_position);
            playerEmail = itemView.findViewById(R.id.player_email);
            playerScore = itemView.findViewById(R.id.player_score);
        }

        public void bindView(int position) {
            if (position == 0) {
                fillPlayerLeaderBoard(POSITION_HEADER, PLAYER_EMAIL_HEADER, PLAYER_SCORE_HEADER);
            } else {
                Statistics statistic = statistics.get(position - 1);
                fillPlayerLeaderBoard(String.valueOf(position), statistic.getPlayer().getUsername(),
                        String.valueOf(statistic.getScore()));
            }
        }

        private void fillPlayerLeaderBoard(String playerPositionString, String playerEmailString,
                                           String playerScoreString) {
            playerPosition.setText(playerPositionString);
            playerEmail.setText(playerEmailString);
            playerScore.setText(playerScoreString);
        }
    }
}
