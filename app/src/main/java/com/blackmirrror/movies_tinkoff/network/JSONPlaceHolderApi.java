package com.blackmirrror.movies_tinkoff.network;

import com.blackmirrror.movies_tinkoff.model.Movie;
import com.blackmirrror.movies_tinkoff.model.TopMovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("api/v2.2/films/top")
    public Call<TopMovies> getPosts(@Query("page") int page, @Query("type") String type);

    @GET("api/v2.2/films/{id}")
    public Call<Movie> getPostWithID(@Path("id") int id);

}
