package com.android.mywatchlist.models.submodels;

import androidx.annotation.NonNull;

public class ProductionCompaniesModel {
    private int id;
    private String logo_path;
    private String name;
    private String origin_country;
    private String homepage;

    public ProductionCompaniesModel() {
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

    public String getOrigin_country() {
        return origin_country;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductionCompaniesModel{" +
                "id=" + id +
                ", logo_path='" + logo_path + '\'' +
                ", name='" + name + '\'' +
                ", origin_country='" + origin_country + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}
