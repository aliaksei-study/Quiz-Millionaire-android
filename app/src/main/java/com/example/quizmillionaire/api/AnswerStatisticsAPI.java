package com.example.quizmillionaire.api;

import com.example.quizmillionaire.api.request.PlayerAnswerRequest;
import com.example.quizmillionaire.model.AnswerStatistics;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AnswerStatisticsAPI {
    @Headers("Content-Type: application/json")
    @POST("/api/v1/answer-statistics")
    Call<AnswerStatistics> saveAnswerStatistics(@Header("Authorization") String auth,
                                                @Body PlayerAnswerRequest playerAnswerRequest);
}
