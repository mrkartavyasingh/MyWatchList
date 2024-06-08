package com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels;

import androidx.annotation.NonNull;

public class CreatedByModel {
    private int id;
    private String credit_id;
    private String name;
    private String profile_path;

    public CreatedByModel() {
    }

    public int getId() {
        return id;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    @NonNull
    @Override
    public String toString() {
        return "CreatedByModel{" +
                "id=" + id +
                ", credit_id='" + credit_id + '\'' +
                ", name='" + name + '\'' +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}
