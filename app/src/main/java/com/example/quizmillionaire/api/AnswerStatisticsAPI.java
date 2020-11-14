package com.example.quizmillionaire.api;

import com.example.quizmillionaire.api.request.PlayerAnswerRequest;
import com.example.quizmillionaire.model.AnswerStatistics;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnswerStatisticsAPI {
    @Headers("Content-Type: application/json")
    @POST("/api/v1/answer-statistics")
    Call<AnswerStatistics> saveAnswerStatistics(@Header("Authorization") String auth,
                                                @Body PlayerAnswerRequest playerAnswerRequest);

    @Headers("Content-Type: application/json")
    @GET("/api/v1/answer-statistics/{id}")
    Call<Map<Long, Integer>> getAnswersHistogram(@Header("Authorization") String auth,
                                                 @Path("id") Long id);
}
