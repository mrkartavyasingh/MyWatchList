package com.android.mywatchlist.models;

import androidx.annotation.NonNull;

public class UserModel {
    String UserId;
    String name;
    String profile;

    public UserModel() {
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserId() {
        return UserId;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserModel{" +
                "UserId='" + UserId + '\'' +
                ", name='" + name + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
