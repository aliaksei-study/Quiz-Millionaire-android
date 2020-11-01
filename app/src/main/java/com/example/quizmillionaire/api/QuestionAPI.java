package com.example.quizmillionaire.api;

import com.example.quizmillionaire.model.Question;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface QuestionAPI {
    @Headers("Content-Type: application/json")
    @GET("/api/v1/questions")
    Call<List<Question>> getQuestions(@Header("Authorization") String auth);
}
