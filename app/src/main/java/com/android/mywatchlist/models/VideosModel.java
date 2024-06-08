package com.android.mywatchlist.models;

import androidx.annotation.NonNull;

import com.android.mywatchlist.models.submodels.TrailerAndClipsModel;

import java.util.ArrayList;

public class VideosModel {
    private int id;

    private ArrayList<TrailerAndClipsModel> results;

    public VideosModel() {
    }

    public int getId() {
        return id;
    }

    public ArrayList<TrailerAndClipsModel> getResults() {
        return results;
    }

    @NonNull
    @Override
    public String toString() {
        return "VideosModel{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
