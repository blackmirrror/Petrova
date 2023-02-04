package com.blackmirrror.movies_tinkoff.ui.popular;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackmirrror.movies_tinkoff.R;
import com.blackmirrror.movies_tinkoff.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private List<Movie> listMovies;

    static class PopularViewHolder extends RecyclerView.ViewHolder {

        final ImageView ivPoster;
        final TextView tvTitle;
        final TextView tvGenre;
        final TextView tvAddFavorite;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_item_poster);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvGenre = itemView.findViewById(R.id.tv_item_genre);
            tvAddFavorite = itemView.findViewById(R.id.tv_add_favorite);
        }
    }

    public PopularAdapter() {
    }

    public PopularAdapter(List<Movie> listMovies) {
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        String title = listMovies.get(position).getTitle();
        if (title.length() > 15)
            title = title.substring(0, 14) + "...";
        holder.tvTitle.setText(title);
        holder.tvGenre.setText(listMovies.get(position).getGenres().get(0).getGenre() +
                " (" + listMovies.get(position).getYear() + ")");
        Picasso.get()
                .load(listMovies.get(position).getPosterUrlPreview())
                .into(holder.ivPoster);
        holder.tvAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvAddFavorite.setBackgroundResource(R.drawable.ic_dashboard_black_24dp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }
}
