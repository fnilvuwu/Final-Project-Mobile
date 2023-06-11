package com.example.h071201021_finalmobile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071201021_finalmobile.data.model.Favorite;
import com.example.h071201021_finalmobile.data.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Favorite> favorites;
    private final ArrayList<Favorite> favorite = new ArrayList<>();
    private ClickListener clickListener;

    public FavoriteAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public void setFavorite(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onUserClicked(Favorite favoriteResponse);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Favorite favorite = favorites.get(position);
        holder.setData(favorite, context);
    }


    @Override
    public int getItemCount() {
        return favorites != null ? favorites.size() : 0;
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView, ivType;
        private TextView titleTextView;
        private TextView yearTextView;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster);
            titleTextView = itemView.findViewById(R.id.tv_title);
            yearTextView = itemView.findViewById(R.id.tv_year);
            ivType = itemView.findViewById(R.id.iv_type);
        }

        public void setData(Favorite favorite, Context context) {
            String title = favorite.getTitle();
            String year = favorite.getReleaseDate();
            String poster = favorite.getPosterPath();
            titleTextView.setText(title);
            year = year.substring(0,4);
            yearTextView.setText(year);
            Glide.with(context)
                    .load(poster)
                    .into(posterImageView);

            if (favorite.getType().equals("movie")) {
                ivType.setImageResource(R.drawable.baseline_movie_24);
            }
            else {
                ivType.setImageResource(R.drawable.baseline_tv_24);
            }

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), MovieDetailActivity.class);
                    intent.putExtra("favorite", favorite);
                    itemView.getContext().startActivity(intent);
                }
            });
        }


    }
}
