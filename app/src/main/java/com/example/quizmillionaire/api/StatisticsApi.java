package com.example.quizmillionaire.api;

import com.example.quizmillionaire.api.request.StatisticsRequest;
import com.example.quizmillionaire.model.Statistics;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface StatisticsApi {
    @Headers("Content-Type: application/json")
    @GET("/api/v1/statistics")
    Call<List<Statistics>> getStatistics(@Header("Authorization") String auth);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/statistics")
    Call<Statistics> saveStatistics(@Header("Authorization") String auth,
                                    @Body StatisticsRequest statisticsRequest);
}
