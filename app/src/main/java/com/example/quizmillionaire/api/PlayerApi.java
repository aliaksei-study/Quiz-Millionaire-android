package com.example.quizmillionaire.api;

import com.example.quizmillionaire.api.request.LikedQuestionRequest;
import com.example.quizmillionaire.model.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PlayerApi {
    @Headers("Content-Type: application/json")
    @GET("/api/v1/players")
    Call<List<Player>> getPlayers(@Header("Authorization") String auth);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/players/liked-question")
    Call<Player> likeQuestion(@Header("Authorization") String auth,
                              @Body LikedQuestionRequest likedQuestionRequest);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/players/disliked-question")
    Call<Player> dislikeQuestion(@Header("Authorization") String auth,
                                 @Body LikedQuestionRequest likedQuestionRequest);
}
