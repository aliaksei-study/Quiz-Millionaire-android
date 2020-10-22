package com.example.quizmillionaire.api;

import com.example.quizmillionaire.model.Question;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionAPI {
    @GET("/questions")
    Call<List<Question>> getQuestions();

}
