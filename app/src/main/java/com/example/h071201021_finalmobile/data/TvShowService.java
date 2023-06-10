package com.example.h071201021_finalmobile.data;

import com.example.h071201021_finalmobile.data.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowService {
    @GET("tv/on_the_air")
    Call<TvShowResponse> getAiringTodayTV(
            @Query("api_key") String apiKey
    );
}
