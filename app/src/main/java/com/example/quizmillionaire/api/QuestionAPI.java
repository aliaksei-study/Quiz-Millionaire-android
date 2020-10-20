package com.example.quizmillionaire.api;

import com.example.quizmillionaire.model.Question;
import com.example.quizmillionaire.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class QuestionAPI {
    private ObjectMapper objectMapper;
    private URL webURL;
    private OkHttpClient okHttpClient;
    private Request request;
    private ResponseBody responseBody;

    public QuestionAPI() {
        objectMapper = new ObjectMapper();
        okHttpClient = new OkHttpClient();
    }

    public Set<Question> getQuestions() throws IOException {
        webURL = new URL(Constants.GET_QUESTION_URL);
        request = new Request.Builder()
                .url(webURL)
                .build();
        responseBody = okHttpClient.newCall(request).execute().body();
        if(responseBody != null) {
            CollectionType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(Set.class, Question.class);
            return objectMapper.readValue(responseBody.string(), javaType);
        }
        return Collections.emptySet();
    }
}
