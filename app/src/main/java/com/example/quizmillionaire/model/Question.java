package com.example.quizmillionaire.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Question implements Serializable {
    private Long id;
    private String questionText;
    private String imagePath;
    private List<Answer> answers;

    public Question(String questionText, String imagePath, List<Answer> answers) {
        this.questionText = questionText;
        this.imagePath = imagePath;
        this.answers = answers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Question question = (Question) obj;
        return (null != this.imagePath) && this.imagePath.equals(question.imagePath) &&
                (null != this.questionText) && this.questionText.equals(question.questionText);
    }

    @Override
    public int hashCode() {
        return (31 * ((null == questionText) ? 0 : questionText.hashCode()) +
                31 * ((null == imagePath) ? 0 : imagePath.hashCode()));
    }
}
