package com.android.mywatchlist.models;

import androidx.annotation.NonNull;

import com.android.mywatchlist.models.submodels.CastMemberModel;
import com.android.mywatchlist.models.submodels.CrewMemberModel;

import java.util.ArrayList;

public class CreditsModel {
    private int id;
    private ArrayList<CastMemberModel> cast;
    private ArrayList<CrewMemberModel> crew;

    public int getId() {
        return id;
    }

    public ArrayList<CastMemberModel> getCast() {
        return cast;
    }

    public ArrayList<CrewMemberModel> getCrew() {
        return crew;
    }

    public CreditsModel() {
    }

    @NonNull
    @Override
    public String toString() {
        return "CreditsModel{" +
                "id=" + id +
                ", cast=" + cast +
                ", crew=" + crew +
                '}';
    }
}
