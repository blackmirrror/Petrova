package com.blackmirrror.movies_tinkoff.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopMovies {
    @SerializedName("pagesCount")
    private int count;
    @SerializedName("films")
    private List<Movie> movies;

    public TopMovies() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
