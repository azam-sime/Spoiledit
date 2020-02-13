package com.spoiledit.models;

import android.os.Parcel;

import java.util.Arrays;

public class MovieModel extends RootSelectionModel {
    private String title;
    private String releaseDate;
    private String posterPath;
    private int totalPages;
    private String overview;
    private int popularity;
    private String[] genres;
    private String runTime;
    private int voteAverage;
    private String[] directors;
    private String[] casts;
    private String pgNames;

    public MovieModel() {

    }

    protected MovieModel(Parcel in) {
        super(in);
        title = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        totalPages = in.readInt();
        overview = in.readString();
        popularity = in.readInt();
        genres = in.createStringArray();
        runTime = in.readString();
        voteAverage = in.readInt();
        directors = in.createStringArray();
        casts = in.createStringArray();
        pgNames = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeInt(totalPages);
        dest.writeString(overview);
        dest.writeInt(popularity);
        dest.writeStringArray(genres);
        dest.writeString(runTime);
        dest.writeInt(voteAverage);
        dest.writeStringArray(directors);
        dest.writeStringArray(casts);
        dest.writeString(pgNames);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String[] getCasts() {
        return casts;
    }

    public void setCasts(String[] casts) {
        this.casts = casts;
    }

    public String getPgNames() {
        return pgNames;
    }

    public void setPgNames(String pgNames) {
        this.pgNames = pgNames;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", totalPages=" + totalPages +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", genres=" + Arrays.toString(genres) +
                ", runTime='" + runTime + '\'' +
                ", voteAverage=" + voteAverage +
                ", directors=" + Arrays.toString(directors) +
                ", casts=" + Arrays.toString(casts) +
                ", pgNames='" + pgNames + '\'' +
                super.toString() +
                '}';
    }
}
