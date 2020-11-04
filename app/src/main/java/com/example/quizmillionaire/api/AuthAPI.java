package com.example.quizmillionaire.api;

import com.example.quizmillionaire.api.request.PlayerAuthRequest;
import com.example.quizmillionaire.api.response.JwtResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("/api/v1/signin")
    Call<JwtResponse> login(@Body PlayerAuthRequest playerAuthRequest);
    @POST("/api/v1/signup")
    Call<JwtResponse> register(@Body PlayerAuthRequest playerAuthRequest);
}
