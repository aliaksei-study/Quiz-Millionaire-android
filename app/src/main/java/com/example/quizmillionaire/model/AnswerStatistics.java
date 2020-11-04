package com.example.quizmillionaire.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerStatistics implements Serializable {
    private Long id;
    private Question question;
    private Answer answer;
    private Player player;

    public AnswerStatistics(Question question, Answer answer, Player player) {
        this.question = question;
        this.answer = answer;
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
        AnswerStatistics answerStatistics = (AnswerStatistics) obj;
        return this.question != null && this.question.equals(answerStatistics.question)
                && this.answer != null && this.answer.equals(answerStatistics.answer) &&
                this.player != null && this.player.equals(answerStatistics.player);
    }

    @Override
    public int hashCode() {
        return ((null == question) ? 0 : question.hashCode()) + ((null == answer) ? 0 : answer.hashCode()) +
                ((null == player) ? 0 : player.hashCode());
    }
}
