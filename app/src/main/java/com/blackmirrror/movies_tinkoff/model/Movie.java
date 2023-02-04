package com.blackmirrror.movies_tinkoff.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("nameRu")
    private String title;
    @SerializedName(value = "filmId", alternate = "kinopoiskId")
    private int id;

    public Movie() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
