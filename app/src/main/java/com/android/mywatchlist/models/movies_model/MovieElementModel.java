package com.android.mywatchlist.models.movies_model;

import androidx.annotation.NonNull;

import com.android.mywatchlist.models.submodels.GenreModel;
import com.android.mywatchlist.models.submodels.ProductionCompaniesModel;

import java.util.ArrayList;

public class MovieElementModel {
    private int id;
    private String title;
    private String poster_path;
    private String homepage;
    private String overview;
    private ArrayList<GenreModel> genres;
    private ArrayList<ProductionCompaniesModel> production_companies;
    private String release_date;
    private int runtime;
    private String status;

    //Constructor
    public MovieElementModel() {
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getOverview() {
        return overview;
    }

    public ArrayList<GenreModel> getGenres() {
        return genres;
    }

    public ArrayList<ProductionCompaniesModel> getProduction_companies() {
        return production_companies;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }


    @NonNull
    @Override
    public String toString() {
        return "MovieElementModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", homepage='" + homepage + '\'' +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                ", production_companies=" + production_companies +
                ", release_date='" + release_date + '\'' +
                ", runtime=" + runtime +
                ", status='" + status + '\'' +
                '}';
    }
}
