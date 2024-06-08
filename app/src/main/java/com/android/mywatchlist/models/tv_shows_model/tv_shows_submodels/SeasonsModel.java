package com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels;

import androidx.annotation.NonNull;

public class SeasonsModel {
    private int id;
    private String air_date;
    private String name;
    private int episode_count;
    private int season_number;
    private String poster_path;

    public SeasonsModel() {
    }

    public int getId() {
        return id;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getName() {
        return name;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public int getSeason_number() {
        return season_number;
    }

    public String getPoster_path() {
        return poster_path;
    }

    @NonNull
    @Override
    public String toString() {
        return "SeasonsModel{" +
                "id=" + id +
                ", air_date='" + air_date + '\'' +
                ", name='" + name + '\'' +
                ", episode_count=" + episode_count +
                ", season_number=" + season_number +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }
}
