package com.example.quizmillionaire.api.response;

import com.example.quizmillionaire.model.Answer;
import com.example.quizmillionaire.model.Category;
import com.example.quizmillionaire.model.enumeration.Difficulty;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddedQuestionResponse {
    private Long id;
    private String questionText;
    private String imagePath;
    private Boolean isTemporal;
    private Difficulty difficulty;
    private Category category;
    private List<Answer> answers;
    private String createdDate;
    private String lastModifiedDate;
}
