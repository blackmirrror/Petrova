package com.blackmirrror.movies_tinkoff.network;

import androidx.annotation.NonNull;

import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.model.TopMovies;
import com.blackmirrror.movies_tinkoff.ui.popular.PopularAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkHelper {

    public List<Movie> getTopMovies() {
        List<Movie> res = new ArrayList<>();
        NetworkService.getInstance()
                .getJSONApi()
                .getPosts(2, "TOP_100_POPULAR_FILMS")
                .enqueue(new Callback<TopMovies>() {
                    @Override
                    public void onResponse(@NonNull Call<TopMovies> call, @NonNull Response<TopMovies> response) {
                        TopMovies movies = response.body();
                        for (Movie m : movies.getMovies())
                            res.add(m);
                    }

                    @Override
                    public void onFailure(@NonNull Call<TopMovies> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        return res;
    }
}
