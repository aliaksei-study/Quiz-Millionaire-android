package com.example.quizmillionaire.api.request;

import com.example.quizmillionaire.model.Answer;
import com.example.quizmillionaire.model.TranslatedAnswer;
import com.example.quizmillionaire.model.TranslatedText;

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
    private String imagePath;
    private List<TranslatedText> questionTextTranslates;
    private List<TranslatedAnswer> answers;
}
