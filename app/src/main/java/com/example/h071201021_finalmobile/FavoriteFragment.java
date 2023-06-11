package com.example.h071201021_finalmobile;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h071201021_finalmobile.data.model.Favorite;
import com.example.h071201021_finalmobile.data.model.Movie;
import com.example.h071201021_finalmobile.data.model.TvShow;
import com.example.h071201021_finalmobile.database.DatabaseHelper;
import com.example.h071201021_finalmobile.database.MovieContract;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    List<Favorite> favoriteList;
    ProgressBar progressBar;
    ImageView sortIv;

    private TextInputLayout tfSearch;
    TextView tvError;
    FavoriteAdapter favoriteAdapter;
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
        tvError = view.findViewById(R.id.tv_error);
        tfSearch = view.findViewById(R.id.tf_search);
        sortIv = view.findViewById(R.id.sort_iv);

        hideLoading();
        favoriteList = getAllMoviesFromDatabase();
        recyclerView = view.findViewById(R.id.rv_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteAdapter = new FavoriteAdapter(favoriteList);
        recyclerView.setAdapter(favoriteAdapter);

        tfSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call the searchNotes method with the new search keyword
                performSearch(s.toString(), favoriteList);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
                performSearch(s.toString(), favoriteList);
            }
        });

        sortIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the sortFavorite method here
                sortFavorite(favoriteList);
            }
        });
    }

    public void refreshFavorites() {
        favoriteAdapter.notifyDataSetChanged();
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
                String title = (titleColumnIndex != -1) ? cursor.getString(titleColumnIndex) : "-";
                String releaseDate = (releaseDateColumnIndex != -1) ? cursor.getString(releaseDateColumnIndex) : "-";
                String overview = (overviewColumnIndex != -1) ? cursor.getString(overviewColumnIndex) : "-";
                String posterUrl = (posterUrlColumnIndex != -1) ? cursor.getString(posterUrlColumnIndex) : "-";
                String backdropUrl = (backdropUrlColumnIndex != -1) ? cursor.getString(backdropUrlColumnIndex) : "-";
                double voteAverage = (voteAverageColumnIndex != -1) ? cursor.getDouble(voteAverageColumnIndex) : 0.0;

                // Create a Movie object and add it to the list
                Favorite favorite = new Favorite(id, overview, posterUrl, releaseDate, title, voteAverage, backdropUrl);
                favoriteList.add(favorite);
            } while (cursor.moveToNext());

        }

        if (favoriteList.isEmpty()) {
            tvError.setVisibility(View.VISIBLE);
            sortIv.setVisibility(View.GONE);
            tfSearch.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.GONE);
            sortIv.setVisibility(View.VISIBLE);
            tfSearch.setVisibility(View.VISIBLE);
        }

        if (cursor != null) {
            cursor.close();
        }

        return favoriteList;
    }

    private void performSearch(String searchQuery, List<Favorite> favorites) {
        List<Favorite> searchFavorite = new ArrayList<>();
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchFavorite.add(favorites.get(i));
            }
        }
        favoriteAdapter.setFavorite(searchFavorite);
    }

    private void sortFavorite(List<Favorite> favorites) {
        // Sort the movies list based on your desired sorting logic
        // For example, sorting by title in ascending order
        Collections.sort(favorites, new Comparator<Favorite>() {
            @Override
            public int compare(Favorite favorite1, Favorite favorite2) {
                return favorite1.getTitle().compareToIgnoreCase(favorite2.getTitle());
            }
        });

        // Update the adapter with the sorted movies list
        favoriteAdapter.setFavorite(favorites);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}