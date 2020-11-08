package com.example.quizmillionaire.api;


import com.example.quizmillionaire.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CategoryApi {
    @Headers("Content-Type: application/json")
    @GET("/api/v1/categories")
    Call<List<Category>> getAllCategories(@Header("Authorization") String auth);
}
