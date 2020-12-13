package com.example.quizmillionaire.api;

import com.example.quizmillionaire.api.request.AddingQuestionRequest;
import com.example.quizmillionaire.api.response.AddedQuestionResponse;
import com.example.quizmillionaire.model.Question;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface QuestionAPI {
    @Headers("Content-Type: application/json")
    @GET("/api/v1/questions/random-questions")
    Call<List<Question>> getQuestions(@Header("Authorization") String auth);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/questions")
    Call<AddedQuestionResponse> saveQuestion(@Header("Authorization") String auth,
                                             @Body AddingQuestionRequest addingQuestionRequest);
}
