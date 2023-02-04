package com.blackmirrror.movies_tinkoff.network;

import com.blackmirrror.movies_tinkoff.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=1")
    public Call<List<Movie>> getPosts();

    @GET("api/v2.2/films/{id}")
    public Call<Movie> getPostWithID(@Path("id") int id);
}
