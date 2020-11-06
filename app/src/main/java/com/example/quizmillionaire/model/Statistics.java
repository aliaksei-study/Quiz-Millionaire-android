package com.example.quizmillionaire.model;

import com.example.quizmillionaire.model.enumeration.Difficulty;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics implements Serializable {
    private Long id;
    private Difficulty highDifficulty;
    private int numberOfGames;
    private int score;
    private Player player;

    public Statistics() {

    }

    public Statistics(Difficulty difficulty, int numberOfGames, int score, Player player) {
        this.highDifficulty = difficulty;
        this.numberOfGames = numberOfGames;
        this.score = score;
        this.player = player;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Statistics statistics = (Statistics) obj;
        return this.highDifficulty == statistics.highDifficulty && this.numberOfGames == statistics.numberOfGames &&
                this.score == statistics.score && this.player != null && this.player.equals(statistics.player);
    }

    @Override
    public int hashCode() {
        return (31 * ((highDifficulty == null) ? 0 : highDifficulty.hashCode()) +
                31 * numberOfGames + 31 * score + ((player == null) ? 0 : player.hashCode()));
    }
}
