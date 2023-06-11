package com.example.h071201021_finalmobile.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private final int id;
    private final String overview;
    private final String posterPath;
    private final String releaseDate;
    private final String title;
    private final Double voteAverage;
    private final String backdropUrl;
    private final String type;

    public Favorite(int id, String overview, String posterPath, String releaseDate, String title, Double voteAverage, String backdropUrl, String type) {
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
        this.backdropUrl = backdropUrl;
        this.type = type;
    }

    protected Favorite(Parcel in) {
        backdropUrl = in.readString();
        id = in.readInt();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        voteAverage = in.readDouble();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdropUrl);
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(title);
        dest.writeDouble(voteAverage);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getType() {
        return type;
    }
}
