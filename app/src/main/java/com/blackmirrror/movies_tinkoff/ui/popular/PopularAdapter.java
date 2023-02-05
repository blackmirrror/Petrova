package com.blackmirrror.movies_tinkoff.ui.popular;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackmirrror.movies_tinkoff.MovieActivity;
import com.blackmirrror.movies_tinkoff.R;
import com.blackmirrror.movies_tinkoff.database.FavoriteContract;
import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.network.NetworkService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    private List<Movie> listMovies;
    static Context context;

    static class PopularViewHolder extends RecyclerView.ViewHolder {

        Context context;
        final ImageView ivPoster;
        final TextView tvTitle;
        final TextView tvGenre;
        final TextView tvAddFavorite;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ivPoster = itemView.findViewById(R.id.iv_item_poster);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvGenre = itemView.findViewById(R.id.tv_item_genre);
            tvAddFavorite = itemView.findViewById(R.id.tv_add_favorite);
            itemView.setOnLongClickListener(v -> {
                addToFavorite((Integer) itemView.getTag());
                tvAddFavorite.setVisibility(View.VISIBLE);
                return true;
            });
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra("id", (Integer) itemView.getTag());
                intent.putExtra("isFavorite", false);
                context.startActivity(intent);
            });
        }
        private void addToFavorite(int id) {
            NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID(id)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                        Movie post = response.body();
                        addMovie(post);
                    }
                    @Override
                    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
        }

        private void addMovie(Movie movie) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(FavoriteContract.Favorite.KEY_ID, movie.getId());
            contentValues.put(FavoriteContract.Favorite.KEY_TITLE, movie.getTitle());
            String genres = "";
            for (int i = 0; i < movie.getGenres().size() - 1; i++)
                genres += movie.getGenres().get(i).getGenre() + " ";
            genres += movie.getGenres().get(movie.getGenres().size() - 1).getGenre();
            contentValues.put(FavoriteContract.Favorite.KEY_GENRES, genres);
            contentValues.put(FavoriteContract.Favorite.KEY_POSTER_URL, movie.getPosterUrl());
            contentValues.put(FavoriteContract.Favorite.KEY_POSTER_URL_PREVIEW, movie.getPosterUrlPreview());
            String countries = "";
            for (int i = 0; i < movie.getCountries().size() - 1; i++)
                countries += movie.getCountries().get(i).getCountry() + " ";
            countries += movie.getCountries().get(movie.getGenres().size() - 1).getCountry();
            contentValues.put(FavoriteContract.Favorite.KEY_COUNTRIES, countries);
            contentValues.put(FavoriteContract.Favorite.KEY_YEAR, movie.getYear());
            contentValues.put(FavoriteContract.Favorite.KEY_DESCRIPTION, movie.getDescription());

            ContentResolver contentResolver = this.itemView.getContext().getContentResolver();
            Uri uri = contentResolver.insert(FavoriteContract.Favorite.CONTENT_URI, contentValues);
            if (uri == null)
                Toast.makeText(this.itemView.getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.itemView.getContext(), "Добавлено в избранное", Toast.LENGTH_SHORT).show();
        }
    }

    public PopularAdapter(List<Movie> listMovies, Context context) {
        this.context = context;
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
        holder.itemView.setTag(listMovies.get(position).getId());
        String title = listMovies.get(position).getTitle();
        if (title.length() > 15)
            title = title.substring(0, 14) + "...";
        holder.tvTitle.setText(title);
        holder.tvGenre.setText(listMovies.get(position).getGenres().get(0).getGenre() +
                " (" + listMovies.get(position).getYear() + ")");
        Picasso.get()
                .load(listMovies.get(position).getPosterUrlPreview())
                .into(holder.ivPoster);

        if (isFavorite(listMovies.get(position).getId()))
            holder.tvAddFavorite.setVisibility(View.VISIBLE);
        else
            holder.tvAddFavorite.setVisibility(View.INVISIBLE);
    }

    private boolean isFavorite(int id) {
        Cursor cursor = context.getContentResolver().query(FavoriteContract.Favorite.CONTENT_URI, new String[]{
                FavoriteContract.Favorite.KEY_TITLE,
                FavoriteContract.Favorite.KEY_YEAR,
                FavoriteContract.Favorite.KEY_GENRES,
                FavoriteContract.Favorite.KEY_POSTER_URL_PREVIEW
        }, FavoriteContract.Favorite.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null);
        if (cursor == null) return false;
        else return cursor.moveToFirst();
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }
}
