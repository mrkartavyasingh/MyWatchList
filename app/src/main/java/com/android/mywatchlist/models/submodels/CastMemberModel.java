package com.android.mywatchlist.models.submodels;

import androidx.annotation.NonNull;

public class CastMemberModel {
    private int id;
    private int cast_id;
    private int gender;
    private String known_for_department;
    private String name;
    private String original_name;
    private String profile_path;
    private String character;
    private String credit_id;

    // Getters
    public int getId() {
        return id;
    }

    public int getCast_id() {
        return cast_id;
    }

    public int getGender() {
        return gender;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getCharacter() {
        return character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    @NonNull
    @Override
    public String toString() {
        return "CastMemberModel{" +
                "id=" + id +
                ", cast_id=" + cast_id +
                ", gender=" + gender +
                ", known_for_department='" + known_for_department + '\'' +
                ", name='" + name + '\'' +
                ", original_name='" + original_name + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", character='" + character + '\'' +
                ", credit_id='" + credit_id + '\'' +
                '}';
    }
}
