package com.example.h071201021_finalmobile.database;

import android.provider.BaseColumns;

public class MovieContract {
    private MovieContract() {
    }

    public static class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_URL = "poster_url";
        public static final String COLUMN_BACKDROP_URL = "backdrop_url";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_TYPE = "type"; // New column for type

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_RELEASE_DATE + " TEXT," +
                        COLUMN_OVERVIEW + " TEXT," +
                        COLUMN_POSTER_URL + " TEXT," +
                        COLUMN_BACKDROP_URL + " TEXT," +
                        COLUMN_VOTE_AVERAGE + " REAL," +
                        COLUMN_GENRE_IDS + " TEXT," +
                        COLUMN_TYPE + " TEXT)"; // Include the new column in the CREATE TABLE statement

        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
