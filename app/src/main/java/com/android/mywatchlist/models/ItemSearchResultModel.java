package com.android.mywatchlist.models;

import androidx.annotation.NonNull;

public class ItemSearchResultModel {
    private int id;
    private String name;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String overview;

    public ItemSearchResultModel() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemSearchResultModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
