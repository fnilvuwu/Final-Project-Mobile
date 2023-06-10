package com.example.h071201021_finalmobile.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvShow implements Parcelable {
    @SerializedName("backdrop_path")
    private final String backdropUrl;

    @SerializedName("first_air_date")
    private final String firstAirDate;

    @SerializedName("genre_ids")
    private final List<Integer> genreIds;

    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("overview")
    private final String overview;

    @SerializedName("poster_path")
    private final String posterUrl;

    @SerializedName("vote_average")
    private final Double voteAverage;

    public TvShow(String backdropUrl, String firstAirDate, List<Integer> genreIds, int id, String name, String overview,
              String posterUrl, Double voteAverage) {
        this.backdropUrl = backdropUrl;
        this.firstAirDate = firstAirDate;
        this.genreIds = genreIds;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
    }

    protected TvShow(Parcel in) {
        backdropUrl = in.readString();
        firstAirDate = in.readString();
        genreIds = new ArrayList<>();
        in.readList(genreIds, Integer.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
        overview = in.readString();
        posterUrl = in.readString();
        voteAverage = in.readDouble();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdropUrl);
        dest.writeString(firstAirDate);
        dest.writeList(genreIds);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeString(posterUrl);
        dest.writeDouble(voteAverage);
    }
}
