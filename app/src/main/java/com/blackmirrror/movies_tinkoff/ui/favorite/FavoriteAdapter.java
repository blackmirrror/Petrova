package com.blackmirrror.movies_tinkoff.ui.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackmirrror.movies_tinkoff.R;
import com.blackmirrror.movies_tinkoff.database.FavoriteContentProvider;
import com.blackmirrror.movies_tinkoff.database.FavoriteContract;
import com.blackmirrror.movies_tinkoff.database.FavoriteDbHelper;
import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.ui.popular.PopularAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    List<Movie> listMovies = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView ivPoster;
        final TextView tvTitle;
        final TextView tvGenre;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_item_poster);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvGenre = itemView.findViewById(R.id.tv_item_genre);
        }
    }

    @SuppressLint("Range")
    public FavoriteAdapter(Context context, Cursor cursor) {
//        FavoriteContentProvider provider = new FavoriteContentProvider();
//        Cursor cursor = provider.query(FavoriteContract.Favorite.CONTENT_URI, new String[]{
//                FavoriteContract.Favorite.KEY_TITLE,
//                FavoriteContract.Favorite.KEY_YEAR,
//                FavoriteContract.Favorite.KEY_GENRES,
//                FavoriteContract.Favorite.KEY_POSTER_URL_PREVIEW
//        }, null, null, null, null);
        if ( cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_TITLE)));
                movie.setYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_YEAR))));
                movie.setPosterUrlPreview(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_POSTER_URL_PREVIEW)));
                List<Movie.Genre> l = new ArrayList<>();
                Movie.Genre g = new Movie.Genre();
                g.setGenre(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_GENRES)));
                l.add(g);
                movie.setGenres(l);
                listMovies.add(movie);
            } while (cursor.moveToNext());
        }
//        Movie movie = new Movie();
//        movie.setTitle("Title");
//        List<Movie.Genre> l = new ArrayList<>();
//        Movie.Genre g = new Movie.Genre();
//        g.setGenre("gg");
//        l.add(g);
//        movie.setGenres(l);
//        movie.setPosterUrlPreview("gfgd");
//        listMovies.add(movie);
//        listMovies.add(movie);
//        listMovies.add(movie);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }
}
