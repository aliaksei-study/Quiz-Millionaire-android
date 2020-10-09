package com.example.quizmillionaire.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerStatistics extends Statistics implements Serializable {
    private Long id;
    private Question question;
    private Answer answer;

    public AnswerStatistics(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
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
        return super.equals(answerStatistics) && this.question != null && this.question.equals(answerStatistics.question)
                && this.answer != null && this.answer.equals(answerStatistics.answer);
    }

    @Override
    public int hashCode() {
        return (super.hashCode() + ((null == question) ? 0 : question.hashCode()) + ((null == answer) ? 0 : answer.hashCode()));
    }
}
