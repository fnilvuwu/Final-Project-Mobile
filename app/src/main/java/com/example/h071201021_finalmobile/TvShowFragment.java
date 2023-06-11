package com.example.h071201021_finalmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.example.h071201021_finalmobile.data.TvShowService;
import com.example.h071201021_finalmobile.data.model.Movie;
import com.example.h071201021_finalmobile.data.model.TvShow;
import com.example.h071201021_finalmobile.data.model.TvShowResponse;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowFragment extends Fragment {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "2e626bcae95608ffd595f9111601e798";
    ProgressBar progressBar;
    TextView tvAlert;
    ImageView btnRefresh;
    private TextInputLayout tfSearch;

    private RecyclerView recyclerView;
    private TvShowAdapter tvAdapter;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        tvAlert = view.findViewById(R.id.tv_alert);
        btnRefresh = view.findViewById(R.id.btn_refresh);
        recyclerView = view.findViewById(R.id.rv_tv_shows);
        tfSearch = view.findViewById(R.id.tf_search);

        showLoading();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TvShowService tvShowService = retrofit.create(TvShowService.class);

        Call<TvShowResponse> call = tvShowService.getAiringTodayTV(API_KEY);

        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    hideLoading();
                    TvShowResponse tvResponse = response.body();
                    List<TvShow> tvShow = tvResponse.getTvShows();
                    tvAdapter = new TvShowAdapter(tvShow);
                    recyclerView.setAdapter(tvAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

                    tfSearch.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // Do nothing
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // Call the searchNotes method with the new search keyword
                            performSearch(s.toString(), tvShow);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            // Do nothing
                            performSearch(s.toString(), tvShow);
                        }
                    });
                } else {
                    showAlert();
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                showAlert();
                Toast.makeText(getActivity(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void performSearch(String searchQuery, List<TvShow> tvShows) {
        List<TvShow> searchTvShow = new ArrayList<>();
        for (int i = 0; i < tvShows.size(); i++) {
            if (tvShows.get(i).getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchTvShow.add(tvShows.get(i));
            }
        }
        tvAdapter.setTvShows(searchTvShow);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        tvAlert.setVisibility(View.INVISIBLE);
        btnRefresh.setVisibility(View.INVISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        tvAlert.setVisibility(View.INVISIBLE);
        btnRefresh.setVisibility(View.INVISIBLE);
        tfSearch.setVisibility(View.VISIBLE);
    }

    private void showAlert() {
        tvAlert.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        tfSearch.setVisibility(View.GONE);
    }
}
