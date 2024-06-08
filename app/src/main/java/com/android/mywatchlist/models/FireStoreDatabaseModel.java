package com.android.mywatchlist.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.android.mywatchlist.models.submodels.GenreModel;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class FireStoreDatabaseModel implements Parcelable {
    private int id;
    private int season_id;
    private String name;
    private String title;
    private String poster_path;
    private int season_number;
    private Timestamp timeStamp;
    private ArrayList<GenreModel> genres;

    public FireStoreDatabaseModel() {
    }

    protected FireStoreDatabaseModel(Parcel in) {
        id = in.readInt();
        season_id = in.readInt();
        name = in.readString();
        title = in.readString();
        poster_path = in.readString();
        season_number = in.readInt();
        timeStamp = in.readParcelable(Timestamp.class.getClassLoader());
        genres = in.createTypedArrayList(GenreModel.CREATOR);
    }

    public static final Creator<FireStoreDatabaseModel> CREATOR = new Creator<FireStoreDatabaseModel>() {
        @Override
        public FireStoreDatabaseModel createFromParcel(Parcel in) {
            return new FireStoreDatabaseModel(in);
        }

        @Override
        public FireStoreDatabaseModel[] newArray(int size) {
            return new FireStoreDatabaseModel[size];
        }
    };

    // Getters
    public int getId() {
        return id;
    }

    public int getSeason_id() {
        return season_id;
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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public int getSeason_number() {
        return season_number;
    }

    public ArrayList<GenreModel> getGenres() {
        return genres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public void setGenres(ArrayList<GenreModel> genres) {
        this.genres = genres;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @NonNull
    @Override
    public String toString() {
        return "FireStoreDatabaseModel{" +
                "id=" + id +
                ", season_id=" + season_id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", season_number=" + season_number +
                ", timeStamp=" + timeStamp +
                ", genres=" + genres +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(season_id);
        parcel.writeString(name);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeInt(season_number);
        parcel.writeParcelable(timeStamp, i);
        parcel.writeTypedList(genres);
    }
}
