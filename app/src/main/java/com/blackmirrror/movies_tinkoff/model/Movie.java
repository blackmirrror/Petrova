package com.blackmirrror.movies_tinkoff.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    public static class Genre {
        @SerializedName("genre")
        private String genre;
        public Genre() {};

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }
    }

    public static class Country {
        @SerializedName("country")
        private String country;
        public Country() {}

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
    @SerializedName("nameRu")
    private String title;
    @SerializedName(value = "filmId", alternate = "kinopoiskId")
    private int id;
    @SerializedName("posterUrlPreview")
    private String posterUrlPreview;
    @SerializedName("posterUrl")
    private String posterUrl;
    @SerializedName("year")
    private int year;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("countries")
    private List<Country> countries;
    @SerializedName("description")
    private String description;

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

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public void setPosterUrlPreview(String posterUrlPreview) {
        this.posterUrlPreview = posterUrlPreview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
