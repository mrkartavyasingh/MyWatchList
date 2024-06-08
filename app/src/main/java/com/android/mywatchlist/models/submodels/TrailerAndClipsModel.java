package com.android.mywatchlist.models.submodels;

import androidx.annotation.NonNull;

public class TrailerAndClipsModel {
    String iso_639_1;
    String iso_3166_1;
    String name;
    String key;
    String site;
    int size;
    String type;
    String official;
    String published_at;
    String id;

    public TrailerAndClipsModel() {
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getOfficial() {
        return official;
    }

    public String getPublished_at() {
        return published_at;
    }

    public String getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return "TrailerAndClipsModel{" +
                "iso_639_1='" + iso_639_1 + '\'' +
                ", iso_3166_1='" + iso_3166_1 + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", official='" + official + '\'' +
                ", published_at='" + published_at + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
