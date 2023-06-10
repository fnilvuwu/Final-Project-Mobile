package com.example.h071201021_finalmobile.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {
    @SerializedName("results")
    private List<TvShow> tvShows;

    public List<TvShow> getTvShows() {
        return tvShows;
    }
}

