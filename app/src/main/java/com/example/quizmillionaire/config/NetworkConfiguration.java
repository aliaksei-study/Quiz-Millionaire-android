package com.example.quizmillionaire.config;

import com.example.quizmillionaire.api.AnswerStatisticsAPI;
import com.example.quizmillionaire.api.AuthAPI;
import com.example.quizmillionaire.api.QuestionAPI;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkConfiguration {
    private static NetworkConfiguration networkConfiguration;
    private static final String BASE_URL = "http://192.168.0.101:8080";
    private String jwtToken;
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

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public QuestionAPI getQuestionApi() {
        return retrofit.create(QuestionAPI.class);
    }

    public AuthAPI getAuthApi() {
        return retrofit.create(AuthAPI.class);
    }

    public AnswerStatisticsAPI getAnswerStatisticsApi() {
        return retrofit.create(AnswerStatisticsAPI.class);
    }
}
