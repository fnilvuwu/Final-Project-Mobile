package com.example.h071201021_finalmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MoviesFragment moviesFragment;
    TvShowFragment tvShowFragment;
    FavoriteFragment favoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.movies);

        moviesFragment = new MoviesFragment();
        tvShowFragment = new TvShowFragment();
        favoriteFragment = new FavoriteFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, moviesFragment)
                .commit();
    }

    private BottomNavigationView.OnItemSelectedListener onItemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.movies) {
                selectedFragment = moviesFragment;
            } else if (item.getItemId() == R.id.tv_shows) {
                selectedFragment = tvShowFragment;
            } else if (item.getItemId() == R.id.favorites) {
                selectedFragment = favoriteFragment;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, selectedFragment)
                        .commit();
                return true;
            }

            return false;
        }
    };
}
