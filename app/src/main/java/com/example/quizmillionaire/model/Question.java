package com.example.quizmillionaire.model;

import com.example.quizmillionaire.model.enumeration.Difficulty;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Question implements Serializable {
    private Long id;
    private String questionText;
    private Boolean isTemporal;
    private String imagePath;
    private Difficulty difficulty;
    private Category category;
    private Set<Answer> answers;

    public Question(String questionText, String imagePath, Boolean isTemporal, Difficulty difficulty, Category category, Set<Answer> answers) {
        this.questionText = questionText;
        this.isTemporal = isTemporal;
        this.imagePath = imagePath;
        this.difficulty = difficulty;
        this.category = category;
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
        return this.category == question.category && this.difficulty == question.difficulty &&
                (null != this.imagePath) && this.imagePath.equals(question.imagePath) &&
                (null != this.questionText) && this.questionText.equals(question.questionText);
    }

    @Override
    public int hashCode() {
        return (31 * ((null == questionText) ? 0 : questionText.hashCode()) +
                31 * ((null == imagePath) ? 0 : imagePath.hashCode()) +
                31 * ((null == difficulty) ? 0 : difficulty.hashCode()) +
                31 * ((null == category) ? 0 : category.hashCode()));
    }
}
