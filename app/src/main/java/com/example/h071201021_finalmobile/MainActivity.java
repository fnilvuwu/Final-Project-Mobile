package com.example.h071201021_finalmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.movies);
    }
    MoviesFragment moviesFragment = new MoviesFragment();
    TvShowFragment tvShowFragment = new TvShowFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.movies) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, moviesFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.tv_shows) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, tvShowFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.favorites) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, favoriteFragment)
                    .commit();
            return true;
        }
        return false;
    }

}
