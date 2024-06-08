package com.android.mywatchlist.models.tv_shows_model;

import androidx.annotation.NonNull;

import com.android.mywatchlist.models.submodels.GenreModel;
import com.android.mywatchlist.models.submodels.ProductionCompaniesModel;
import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.CreatedByModel;
import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.NetworksModel;
import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.SeasonsModel;

import java.util.ArrayList;

public class TvShowElementModel {
    private int id;
    private ArrayList<CreatedByModel> created_by;
    private String name;
    private String poster_path;
    private String homepage;
    private String overview;
    private ArrayList<GenreModel> genres;
    private ArrayList<ProductionCompaniesModel> production_companies;
    private String first_air_date;
    private String last_air_date;
    private ArrayList<NetworksModel> networks;
    private int number_of_episodes;
    private int number_of_seasons;
    private ArrayList<SeasonsModel> seasons;
    private String status;

    public TvShowElementModel() {
    }

    public int getId() {
        return id;
    }

    public ArrayList<CreatedByModel> getCreated_by() {
        return created_by;
    }

    public String getName() {
        return name;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public ArrayList<NetworksModel> getNetworks() {
        return networks;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public ArrayList<SeasonsModel> getSeasons() {
        return seasons;
    }

    public String getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return "TvShowElementModel{" +
                "id=" + id +
                ", created_by=" + created_by +
                ", name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", homepage='" + homepage + '\'' +
                ", overview='" + overview + '\'' +
                ", genres=" + genres +
                ", production_companies=" + production_companies +
                ", first_air_date='" + first_air_date + '\'' +
                ", last_air_date='" + last_air_date + '\'' +
                ", networks=" + networks +
                ", number_of_episodes=" + number_of_episodes +
                ", number_of_seasons=" + number_of_seasons +
                ", seasons=" + seasons +
                ", status='" + status + '\'' +
                '}';
    }
}
