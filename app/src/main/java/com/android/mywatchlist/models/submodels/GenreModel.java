package com.android.mywatchlist.models.submodels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GenreModel implements Parcelable {
    private int id;
    private String name;
    private boolean selected;

    public GenreModel() {
    }

    protected GenreModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<GenreModel> CREATOR = new Creator<GenreModel>() {
        @Override
        public GenreModel createFromParcel(Parcel in) {
            return new GenreModel(in);
        }

        @Override
        public GenreModel[] newArray(int size) {
            return new GenreModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeByte((byte) (selected ? 1 : 0));
    }

    public static class GenreModelList{
        private ArrayList<GenreModel> genres;

        public GenreModelList() {
        }

        public ArrayList<GenreModel> getGenres() {
            return genres;
        }

        @NonNull
        @Override
        public String toString() {
            return "GenreModelList{" +
                    "genres=" + genres +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "GenreModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", selected=" + selected +
                '}';
    }
}
