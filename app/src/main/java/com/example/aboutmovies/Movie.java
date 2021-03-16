package com.example.aboutmovies;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Movie {


    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("video")
    private boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("revenue")
    private int revenue;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("popularity")
    private double popularity;


    @SerializedName("id")
    private int id;

    @SerializedName("vote_count")
    private int voteCount;


    @SerializedName("overview")
    private String overview;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("poster_path")
    private String posterPath;


    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private String voteAverage;


    @SerializedName("adult")
    private boolean adult;


    @SerializedName("status")
    private String status;


    public Movie() {
    }

    public Movie(String title, List<Genre> genres, int runtime, String posterPath, String releaseDate, String voteAverage, int id) {
        this.title = title;
        this.genres = genres;
        this.runtime = runtime;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.id = id;
    }

    public Movie(String overview, String title, String posterPath, String releaseDate, String voteAverage, int id, int voteCount) {
        this.overview = overview;

        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.id = id;
        this.voteCount = voteCount;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isVideo() {
        return video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }


    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }


    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteAverage() {
        return voteAverage;
    }


    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}