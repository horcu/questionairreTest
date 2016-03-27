package com.horcu.apps.peez.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Horatio on 3/25/2016.
 */
public interface QuestionService {

    @GET("{url}")
    Call<List<String>> GetQuestions(@Path("url") String url);
}
