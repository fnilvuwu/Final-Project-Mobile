package com.example.h071201021_finalmobile;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.h071201021_finalmobile.data.model.Favorite;
import com.example.h071201021_finalmobile.database.DatabaseHelper;
import com.example.h071201021_finalmobile.database.MovieContract;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_bar);
        hideLoading();
        List<Favorite> favoriteList = getAllMoviesFromDatabase();
        recyclerView = view.findViewById(R.id.rv_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(favoriteList);
        recyclerView.setAdapter(favoriteAdapter);
    }

    private List<Favorite> getAllMoviesFromDatabase() {
        List<Favorite> favoriteList = new ArrayList<>();
        DatabaseHelper movieHelper = new DatabaseHelper(getActivity());
        Cursor cursor = movieHelper.getAllMovies();

        if (cursor != null && cursor.moveToFirst()) {

            int idColumnIndex = cursor.getColumnIndex(MovieContract.DatabaseEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(MovieContract.DatabaseEntry.COLUMN_TITLE);
            int releaseDateColumnIndex = cursor.getColumnIndex(MovieContract.DatabaseEntry.COLUMN_RELEASE_DATE);
            int overviewColumnIndex = cursor.getColumnIndex(MovieContract.DatabaseEntry.COLUMN_OVERVIEW);
            int posterUrlColumnIndex = cursor.getColumnIndex(MovieContract.DatabaseEntry.COLUMN_POSTER_URL);
            int backdropUrlColumnIndex = cursor.getColumnIndex(MovieContract.DatabaseEntry.COLUMN_BACKDROP_URL);
            int voteAverageColumnIndex = cursor.getColumnIndex(MovieContract.DatabaseEntry.COLUMN_VOTE_AVERAGE);

            do {
                int id = (idColumnIndex != -1) ? cursor.getInt(idColumnIndex) : -1;
                String title = (titleColumnIndex != -1) ? cursor.getString(titleColumnIndex) : null;
                String releaseDate = (releaseDateColumnIndex != -1) ? cursor.getString(releaseDateColumnIndex) : null;
                String overview = (overviewColumnIndex != -1) ? cursor.getString(overviewColumnIndex) : null;
                String posterUrl = (posterUrlColumnIndex != -1) ? cursor.getString(posterUrlColumnIndex) : null;
                String backdropUrl = (backdropUrlColumnIndex != -1) ? cursor.getString(backdropUrlColumnIndex) : null;
                double voteAverage = (voteAverageColumnIndex != -1) ? cursor.getDouble(voteAverageColumnIndex) : 0.0;

                // Create a Movie object and add it to the list
                Favorite favorite = new Favorite(id, overview, posterUrl, releaseDate, title, voteAverage, backdropUrl);
                favoriteList.add(favorite);
            } while (cursor.moveToNext());

        }

        if (cursor != null) {
            cursor.close();
        }

        return favoriteList;
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}