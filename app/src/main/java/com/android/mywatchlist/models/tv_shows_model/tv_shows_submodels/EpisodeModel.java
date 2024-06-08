package com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels;

public class EpisodeModel {
    private int id;
    private int episode_number;
    private String air_date;
    private String name;
    private String overview;
    private String still_path;
    private int runtime;

    public EpisodeModel() {
    }

    public int getId() {
        return id;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStill_path() {
        return still_path;
    }

}
