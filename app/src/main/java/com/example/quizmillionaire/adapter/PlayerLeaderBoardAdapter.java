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

import java.util.List;

public class PlayerLeaderBoardAdapter extends RecyclerView.Adapter<PlayerLeaderBoardAdapter.PlayerLeaderBoardViewHolder> {
    private final List<Player> players;

    public PlayerLeaderBoardAdapter(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerLeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForLeaderBoardItem = R.layout.leaderboard_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForLeaderBoardItem, parent, false);

        return new PlayerLeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerLeaderBoardViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return players.size() + 1;
    }

    class PlayerLeaderBoardViewHolder extends RecyclerView.ViewHolder {
        private static final String POSITION_HEADER = "№";
        private static final String PLAYER_EMAIL_HEADER = "Электронная почта";
        private static final String PLAYER_SCORE_HEADER = "Баллы";

        private final TextView playerPosition;
        private final TextView playerEmail;
        private final TextView playerScore;

        public PlayerLeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            playerPosition = itemView.findViewById(R.id.player_position);
            playerEmail = itemView.findViewById(R.id.player_email);
            playerScore = itemView.findViewById(R.id.player_score);
        }

        public void bindView(int position) {
            if (position == 0) {
                fillPlayerLeaderBoard(POSITION_HEADER, PLAYER_EMAIL_HEADER, PLAYER_SCORE_HEADER);
            } else {
                Player player = players.get(position - 1);
                fillPlayerLeaderBoard(String.valueOf(position), player.getUsername(), String.valueOf(0));
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
