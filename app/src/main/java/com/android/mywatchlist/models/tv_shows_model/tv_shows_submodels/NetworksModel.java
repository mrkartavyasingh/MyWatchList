package com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels;

import androidx.annotation.NonNull;

public class NetworksModel {
    private int id;
    private String logo_path;
    private String name;

    public NetworksModel() {
    }

    public int getId() {
        return id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "NetworksModel{" +
                "id=" + id +
                ", logo_path='" + logo_path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
