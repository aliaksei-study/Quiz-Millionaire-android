package com.example.quizmillionaire.api;

import com.example.quizmillionaire.model.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface PlayerApi {
    @Headers("Content-Type: application/json")
    @GET("/api/v1/players")
    Call<List<Player>> getPlayers(@Header("Authorization") String auth);
}
