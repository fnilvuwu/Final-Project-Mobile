package com.example.h071201021_finalmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.h071201021_finalmobile.data.model.Favorite;
import com.example.h071201021_finalmobile.data.model.Movie;
import com.example.h071201021_finalmobile.data.model.TvShow;
import com.example.h071201021_finalmobile.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {
    // to do list memisahkan antara tvshow dan movie, icon movie menggunakan icon film, tv show menggunakan icon tvshow

    // membuat item layout untuk favorites

    // please check your internet connection kalau tidak ada jaringan
    // refresh pas pencet bottom navbar
    // favorite harus offline, gambar termasuk offline

    private DatabaseHelper dbHelper;
    private ImageView backdropImageView, backButton, favoriteButton, posterImageView, typeImageView;
    private TextView titleTextView, releaseDateTextView, ratingTextView, synopsisTextView;
    private boolean isFavorite = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdropImageView = findViewById(R.id.iv_backdrop);
        backButton = findViewById(R.id.btn_back);
        favoriteButton = findViewById(R.id.btn_favorite);
        posterImageView = findViewById(R.id.iv_poster);
        titleTextView = findViewById(R.id.tv_title);
        releaseDateTextView = findViewById(R.id.tv_release_date);
        ratingTextView = findViewById(R.id.tv_rating);
        typeImageView = findViewById(R.id.iv_type);
        synopsisTextView = findViewById(R.id.tv_synopsis);

        dbHelper = new DatabaseHelper(this);

        backButton.setOnClickListener(View -> {
            finish();
        });

        Intent intent = getIntent();
        if (intent.getParcelableExtra("movie") != null) {
            Movie movie = intent.getParcelableExtra("movie");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movie.getPosterPath();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movie.getBackdropUrl();

            titleTextView.setText(movie.getTitle());

            // Original date string
            String originalDate = movie.getReleaseDate();

            // Parse the original date string into a Date object
            Date date = parseDate(originalDate, "yyyy-MM-dd");

            // Format the date using the desired format
            String formattedDate = formatDate(date, "MMMM d, yyyy");

            // Set the new date
            releaseDateTextView.setText(formattedDate);

            typeImageView.setImageResource(R.drawable.baseline_movie_24);
            ratingTextView.setText(movie.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(posterImageView);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(backdropImageView);
            synopsisTextView.setText(movie.getOverview());

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFavorite) {
                        favoriteButton.setImageResource(R.drawable.baseline_favorite_24);
                        isFavorite = true;
                        addMovieToFavorites(movie.getId(), movie.getOverview(), posterUrl, movie.getReleaseDate(), movie.getTitle(), movie.getVoteAverage(), backdropUrl);
                    } else {
                        favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24);
                        isFavorite = false;
                        deleteMovieFromFavorites(movie.getTitle());
                    }
                }
            });


        } else if (intent.getParcelableExtra("show") != null) {
            TvShow show = intent.getParcelableExtra("show");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + show.getPosterUrl();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + show.getBackdropUrl();
            titleTextView.setText(show.getName());

            // Original date string
            String originalDate = show.getFirstAirDate();

            // Parse the original date string into a Date object
            Date date = parseDate(originalDate, "yyyy-MM-dd");

            // Format the date using the desired format
            String formattedDate = formatDate(date, "MMMM d, yyyy");

            // Set the new date
            releaseDateTextView.setText(formattedDate);

            typeImageView.setImageResource(R.drawable.baseline_tv_24);
            ratingTextView.setText(show.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(posterImageView);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(backdropImageView);
            synopsisTextView.setText(show.getOverview());

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFavorite) {
                        favoriteButton.setImageResource(R.drawable.baseline_favorite_24);
                        isFavorite = true;
                        addMovieToFavorites(show.getId(), show.getOverview(), posterUrl, show.getFirstAirDate(), show.getName(), show.getVoteAverage(), backdropUrl);
                    } else {
                        favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24);
                        isFavorite = false;
                        deleteMovieFromFavorites(show.getName());
                    }
                }
            });
        }
        else {
            Favorite favorite = intent.getParcelableExtra("favorite");
            String posterUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + favorite.getPosterPath();
            String backdropUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + favorite.getBackdropUrl();
            titleTextView.setText(favorite.getTitle());
            releaseDateTextView.setText(favorite.getReleaseDate());

            ratingTextView.setText(favorite.getVoteAverage().toString());
            Glide.with(this)
                    .load(posterUrl)
                    .into(posterImageView);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(backdropImageView);
            synopsisTextView.setText(favorite.getOverview());

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!dbHelper.isMovieInFavorites(favorite.getTitle())) {
                        favoriteButton.setImageResource(R.drawable.baseline_favorite_24);
                        isFavorite = true;
                        addMovieToFavorites(favorite.getId(), favorite.getOverview(), posterUrl, favorite.getTitle(), favorite.getTitle(), favorite.getVoteAverage(), backdropUrl);
                    } else {
                        favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24);
                        isFavorite = false;
                        deleteMovieFromFavorites(favorite.getTitle());
                    }
                }
            });
        }
    }

    private void addMovieToFavorites(int id, String overview, String posterUrl, String releaseDate, String title, double voteAverage, String backdropUrl) {
        Movie movie = new Movie(id, overview, posterUrl, releaseDate, title, voteAverage, backdropUrl);
        long result = dbHelper.insertMovie(movie);
        if (result != -1) {
            Toast.makeText(this, "Movie added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add movie", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteMovieFromFavorites(String title) {
        long result = dbHelper.deleteMovie(title);
        if (result != -1) {
            Toast.makeText(this, "Movie Deleted from favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete movie", Toast.LENGTH_SHORT).show();
        }
    }

    private static Date parseDate(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.format(date);
    }
}
