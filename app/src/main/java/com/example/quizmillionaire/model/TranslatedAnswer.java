package com.example.quizmillionaire.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslatedAnswer implements Serializable {
    private Boolean isCorrect;
    private List<TranslatedText> localizedAnswers;
}
