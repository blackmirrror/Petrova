package com.blackmirrror.movies_tinkoff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirrror.movies_tinkoff.database.FavoriteContract;
import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.network.NetworkService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {

    ImageView ivPoster;
    TextView tvTitle;
    TextView tvDescription;
    TextView tvGenres;
    TextView tvCountries;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setTitle("Описание");
        initViews();
    }

    private void initViews() {
        ivPoster = findViewById(R.id.iv_movie_poster);
        tvTitle = findViewById(R.id.tv_movie_title);
        tvDescription = findViewById(R.id.tv_movie_description);
        tvGenres = findViewById(R.id.tv_movie_genres);
        tvCountries = findViewById(R.id.tv_movie_countries);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        boolean isFavorite = intent.getBooleanExtra("isFavorite", false);
        if (isFavorite)
            getFromDb(id);
        else
            getFromApi(id);
    }

    private void getFromApi(int id) {
                NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID(id)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                        Movie post = response.body();
                        tvTitle.setText(post.getTitle());
                        tvDescription.setText(post.getDescription());
                        for (int i = 0; i < post.getGenres().size() - 1; i++)
                            tvGenres.append(post.getGenres().get(i).getGenre() + ", ");
                        tvGenres.append(post.getGenres().get(
                                post.getGenres().size() - 1).getGenre());
                        for (int i = 0; i < post.getCountries().size() - 1; i++)
                            tvCountries.append(post.getCountries().get(i).getCountry() + ", ");
                        tvCountries.append(post.getCountries().get(
                                post.getCountries().size() - 1).getCountry());
                        Picasso.get()
                                .load(post.getPosterUrl())
                                .into(ivPoster);
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                        tvTitle.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }

    @SuppressLint("Range")
    private void getFromDb(int id) {
        Cursor cursor = this.getContentResolver().query(FavoriteContract.Favorite.CONTENT_URI, new String[]{
                FavoriteContract.Favorite.KEY_ID,
                FavoriteContract.Favorite.KEY_TITLE,
                FavoriteContract.Favorite.KEY_YEAR,
                FavoriteContract.Favorite.KEY_GENRES,
                FavoriteContract.Favorite.KEY_POSTER_URL,
                FavoriteContract.Favorite.KEY_COUNTRIES,
                        FavoriteContract.Favorite.KEY_DESCRIPTION
        },
                FavoriteContract.Favorite.KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null);

        if (cursor != null && cursor.moveToFirst()) {
            tvTitle.setText(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_TITLE)));
            tvDescription.setText(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_DESCRIPTION)));
            String[] genres = cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_GENRES)).split(" ");
            for (int i = 0; i < genres.length - 1; i++)
                tvGenres.append(genres[i] + ", ");
            tvGenres.append(genres[genres.length - 1]);
            String[] countries = cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_COUNTRIES)).split(" ");
            for (int i = 0; i < countries.length - 1; i++)
                tvCountries.append(countries[i] + ", ");
            tvCountries.append(countries[countries.length - 1]);
            Picasso.get()
                    .load(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.KEY_POSTER_URL)))
                    .into(ivPoster);
        }
    }
}