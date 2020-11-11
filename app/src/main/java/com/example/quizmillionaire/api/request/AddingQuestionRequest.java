package com.example.quizmillionaire.api.request;

import com.example.quizmillionaire.model.Answer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddingQuestionRequest {
    private String questionImageUrl;
    private String questionText;
    private List<Answer> answers;
}
