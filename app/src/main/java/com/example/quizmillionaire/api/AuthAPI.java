package com.example.quizmillionaire.api;

import com.example.quizmillionaire.api.request.PrincipalAuthRequest;
import com.example.quizmillionaire.api.response.JwtResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("/api/v1/signin")
    Call<JwtResponse> login(@Body PrincipalAuthRequest principalAuthRequest);
    @POST("/api/v1/signup")
    Call<JwtResponse> register(@Body PrincipalAuthRequest principalAuthRequest);
}
