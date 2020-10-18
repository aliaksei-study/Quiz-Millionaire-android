package com.example.quizmillionaire.model;

import com.example.quizmillionaire.model.enumeration.Difficulty;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Statistics implements Serializable {
    private Long id;
    private Difficulty preferredDifficulty;
    private int numberOfGames;
    private int score;

    public Statistics() {

    }

    public Statistics(Difficulty difficulty, int numberOfGames, int score) {
        this.preferredDifficulty = difficulty;
        this.numberOfGames = numberOfGames;
        this.score = score;
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
        return this.preferredDifficulty == statistics.preferredDifficulty && this.numberOfGames == statistics.numberOfGames &&
                this.score == statistics.score;
    }

    @Override
    public int hashCode() {
        return (31 * ((preferredDifficulty == null) ? 0 : preferredDifficulty.hashCode()) +
                31 * numberOfGames + 31 * score);
    }
}
