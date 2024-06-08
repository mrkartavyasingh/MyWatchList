package com.android.mywatchlist.models.tv_shows_model;

import androidx.annotation.NonNull;

import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.EpisodeModel;

import java.util.ArrayList;

public class TvShowSeasonElementModel {
    private int id;
    private String name;
    private String poster_path;
    private String overview;
    private String air_date;
    private int season_number;
    private ArrayList<EpisodeModel> episodes;

    public TvShowSeasonElementModel() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getAir_date() {
        return air_date;
    }

    public int getSeason_number() {
        return season_number;
    }

    public ArrayList<EpisodeModel> getEpisodes() {
        return episodes;
    }

    @NonNull
    @Override
    public String toString() {
        return "TvShowSeasonElementModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", air_date='" + air_date + '\'' +
                ", season_number=" + season_number +
                ", episodes=" + episodes +
                '}';
    }
}
