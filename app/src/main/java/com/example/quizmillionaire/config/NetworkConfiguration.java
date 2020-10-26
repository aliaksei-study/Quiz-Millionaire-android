package com.example.quizmillionaire.config;

import com.example.quizmillionaire.api.QuestionAPI;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkConfiguration {
    private static NetworkConfiguration networkConfiguration;
    private static final String BASE_URL = "http://192.168.0.100:8080";
    private Retrofit retrofit;

    private NetworkConfiguration() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static NetworkConfiguration getInstance() {
        if(networkConfiguration == null) {
            networkConfiguration = new NetworkConfiguration();
        }
        return networkConfiguration;
    }

    public QuestionAPI getQuestionApi() {
        return retrofit.create(QuestionAPI.class);
    }
}
