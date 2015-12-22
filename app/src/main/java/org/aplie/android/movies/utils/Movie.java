package org.aplie.android.movies.utils;

public class Movie {
    private long id;
    private String title;
    private String originaTitle;
    private String posterPath;
    private String overview;
    private String backdropPath;
    private double voteAverage;

    public Movie(String title, String originaTitle, String posterPath, String overview, String backdropPath, double voteAverage) {
        this.title = title;
        this.originaTitle = originaTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
    }

    public Movie(int id, String title, String originaTitle, String posterPath, String overview, String backdropPath, double voteAverage) {
        this.id = id;
        this.title = title;
        this.originaTitle = originaTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginaTitle() {
        return originaTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
