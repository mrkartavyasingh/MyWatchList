package com.android.mywatchlist;

import android.content.Context;
import android.content.Intent;

import com.android.mywatchlist.fragments.homepage.movies.MovieElementActivity;
import com.android.mywatchlist.fragments.homepage.tv_shows.TvShowActivity;
import com.android.mywatchlist.models.CreditsModel;
import com.android.mywatchlist.models.FireStoreDatabaseModel;
import com.android.mywatchlist.models.VideosModel;
import com.android.mywatchlist.models.movies_model.MovieElementModel;
import com.android.mywatchlist.models.submodels.GenreModel;

import java.util.ArrayList;

public class GlobalVariable {
    private static FireStoreDatabaseModel fireStoreDatabaseObject;
    private static ArrayList<FireStoreDatabaseModel> mwl;
    private static MovieElementModel movieItemResultObject;
    private static ArrayList<GenreModel> movieGenreModel;
    private static ArrayList<GenreModel> tvshowGenreModel;
    private static CreditsModel creditsObject;
    private static VideosModel videosItemObject;

    public static FireStoreDatabaseModel getFireStoreDatabaseObject() {
        return fireStoreDatabaseObject;
    }

    public static ArrayList<FireStoreDatabaseModel> getMwl() {
        return mwl;
    }

    public static MovieElementModel getMovieItemResultObject() {
        return movieItemResultObject;
    }

    public static CreditsModel getCreditsObject() {
        return creditsObject;
    }

    public static VideosModel getVideosItemObject() {
        return videosItemObject;
    }

    public static ArrayList<GenreModel> getMovieGenreModel() {
        return movieGenreModel;
    }

    public static ArrayList<GenreModel> getTvshowGenreModel() {
        return tvshowGenreModel;
    }

    public static void setFireStoreDatabaseObject(FireStoreDatabaseModel fireStoreDatabaseObject) {
        GlobalVariable.fireStoreDatabaseObject = fireStoreDatabaseObject;
    }

    public static void setMwl(ArrayList<FireStoreDatabaseModel> mwl) {
        GlobalVariable.mwl = mwl;
    }

    public static void setMovieItemResultObject(MovieElementModel movieItemResultObject) {
        GlobalVariable.movieItemResultObject = movieItemResultObject;
    }

    public static void setCreditsObject(CreditsModel creditsObject) {
        GlobalVariable.creditsObject = creditsObject;
    }

    public static void setVideoItemObject(VideosModel videosItemObject) {
        GlobalVariable.videosItemObject = videosItemObject;
    }

    public static void setMovieGenreModel(ArrayList<GenreModel> movieGenreModel) {
        GlobalVariable.movieGenreModel = movieGenreModel;
    }

    public static void setTvshowGenreModel(ArrayList<GenreModel> tvshowGenreModel) {
        GlobalVariable.tvshowGenreModel = tvshowGenreModel;
    }

    public static void openItemElementActivity(Context context, String typeHomepage, int item_id, boolean mwl, ArrayList<FireStoreDatabaseModel> tvShowFireStoreModel) {
        Intent intent = null;
        if (context.getString(R.string.movies).equals(typeHomepage)) {
            intent = new Intent(context, MovieElementActivity.class);
            intent.putExtra("movie_id", item_id);
        } else {
            intent = new Intent(context, TvShowActivity.class);
            intent.putExtra("tv_show_id", item_id);
        }

        if (mwl) {
            intent.putExtra("mwl", mwl);
            intent.putParcelableArrayListExtra("firestore_tv_show_mwl", tvShowFireStoreModel);
        }
        context.startActivity(intent);
    }
}
