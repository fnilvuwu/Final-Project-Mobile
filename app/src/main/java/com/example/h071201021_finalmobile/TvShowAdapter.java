package com.example.h071201021_finalmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071201021_finalmobile.data.model.TvShow;
import com.example.h071201021_finalmobile.data.model.TvShowResponse;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {
    private List<TvShow> tvShows;

    private MovieAdapter.ClickListener clickListener;

    public TvShowAdapter(List<TvShow> tvShows) {
        this.tvShows = tvShows;
    }

    public interface ClickListener {
        void onUserClicked(TvShowResponse tvResponse);
    }

    public void setClickListener(MovieAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        TvShow tvShow = tvShows.get(position);
        holder.setData(tvShow, context);
    }

    @Override
    public int getItemCount() {
        return tvShows != null ? tvShows.size() : 0;
    }

    static class TvViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView yearTextView;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster);
            titleTextView = itemView.findViewById(R.id.tv_title);
            yearTextView = itemView.findViewById(R.id.tv_year);
        }

        public void setData(TvShow tvShow, Context context) {
            String title = tvShow.getName();
            String year = tvShow.getFirstAirDate().substring(0, 4);
            String poster = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2/" + tvShow.getPosterUrl();
            titleTextView.setText(title);
            yearTextView.setText(year);
            Glide.with(context)
                    .load(poster)
                    .into(posterImageView);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
                    intent.putExtra("show", tvShow);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}