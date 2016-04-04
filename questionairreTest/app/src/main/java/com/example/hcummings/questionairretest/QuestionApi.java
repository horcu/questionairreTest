package com.example.hcummings.questionairretest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by hcummings on 4/4/2016.
 */
public interface QuestionApi {

    @GET("{url}")
    Call<List<Question>> getRandomQuestions(@Path("url") String url, @QueryMap Map<String,String> options);
}
