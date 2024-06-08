package com.android.mywatchlist.utils;

import com.android.mywatchlist.models.CreditsModel;
import com.android.mywatchlist.models.ItemSearchModel;
import com.android.mywatchlist.models.VideosModel;
import com.android.mywatchlist.models.movies_model.MovieElementModel;
import com.android.mywatchlist.models.submodels.GenreModel;
import com.android.mywatchlist.models.submodels.ProductionCompaniesModel;
import com.android.mywatchlist.models.tv_shows_model.TvShowElementModel;
import com.android.mywatchlist.models.tv_shows_model.TvShowSeasonElementModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemApi {

    //https://api.themoviedb.org/3/genre/movie/list?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/genre/movie/list?")
    Call<GenreModel.GenreModelList> getMoviesGenre(
            @Query("api_key") String key
    );


    //https://api.themoviedb.org/3/search/movie?api_key=94dbfb1226f3ca1d16aecb852f53d4d8&query=stranger+things
    @GET("/3/search/movie?")
    Call<ItemSearchModel> getSearchMovieByName(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/movie/569094?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/movie/{movie_id}?")
    Call<MovieElementModel> getMovieById(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/movie/569094/credits?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/movie/{movie_id}/credits?")
    Call<CreditsModel> getCreditsByMovieId(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/movie/569094/recommendations?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/movie/{movie_id}/recommendations?")
    Call<ItemSearchModel> getRecommendationsByMovieId(
            @Path("movie_id") int movie_id,
            @Query("page") int page,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/movie/569094/similar?api_key=94dbfb1226f3ca1d16aecb852f53d4d8&page=1
    @GET("/3/movie/{movie_id}/similar?")
    Call<ItemSearchModel> getSimilarByMovieId(
            @Path("movie_id") int movie_id,
            @Query("page") int page,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/movie/667538/videos?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/movie/{movie_id}/videos?")
    Call<VideosModel> getVideosByMovieId(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/movie/popular?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/movie/popular?")
    Call<ItemSearchModel> getPopularMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/trending/movie/day?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/trending/movie/day?")
    Call<ItemSearchModel> getTrendingMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/movie/upcoming?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/movie/upcoming?")
    Call<ItemSearchModel> getUpcomingMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/movie/now_playing?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/movie/now_playing?")
    Call<ItemSearchModel> getNowPlayingMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/company/5?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/company/{production_company_id}?")
    Call<ProductionCompaniesModel> getProductionCompany(
            @Path("production_company_id") int production_company_id,
            @Query("api_key") String api_key
    );

    //*************************************************************************************************

    //https://api.themoviedb.org/3/genre/tv/list?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/genre/tv/list?")
    Call<GenreModel.GenreModelList> getTvShowsGenre(
            @Query("api_key") String key
    );

    //https://api.themoviedb.org/3/search/tv?api_key=94dbfb1226f3ca1d16aecb852f53d4d8&query=stranger+things
    @GET("/3/search/tv?")
    Call<ItemSearchModel> getSearchTvShowsByName(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/tv/66732?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/{tv_show_id}?")
    Call<TvShowElementModel> getTvShowById(
            @Path("tv_show_id") int tv_show_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/tv/71446/season/1?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/{tv_show_id}/season/{season_number}?")
    Call<TvShowSeasonElementModel> getTvShowSeasonById(
            @Path("tv_show_id") int tv_show_id,
            @Path("season_number") int season_number,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/tv/71446/videos?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/{tv_show_id}/videos?")
    Call<VideosModel> getVideosByTvShowId(
            @Path("tv_show_id") int tv_show_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/tv/71446/season/1/videos?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/{tv_show_id}/season/{season_number}/videos?")
    Call<VideosModel> getVideosSeasonByTvShowId(
            @Path("tv_show_id") int tv_show_id,
            @Path("season_number") int season_number,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/tv/114461/credits?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/{tv_show_id}/credits?")
    Call<CreditsModel> getCreditsByTvShowId(
            @Path("tv_show_id") int tv_show_id,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/tv/114461/recommendations?api_key=94dbfb1226f3ca1d16aecb852f53d4d8&page=1
    @GET("/3/tv/{tv_show_id}/recommendations?")
    Call<ItemSearchModel> getRecommendationsByTvShowId(
            @Path("tv_show_id") int tv_show_id,
            @Query("page") int page,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/tv/114461/similar?api_key=94dbfb1226f3ca1d16aecb852f53d4d8&page=1
    @GET("/3/tv/{tv_show_id}/similar?")
    Call<ItemSearchModel> getSimilarByTvShowId(
            @Path("tv_show_id") int tv_show_id,
            @Query("page") int page,
            @Query("api_key") String api_key
    );

    //https://api.themoviedb.org/3/tv/airing_today?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/airing_today?")
    Call<ItemSearchModel> getAiringTodayTvShows(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/trending/tv/day?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/trending/tv/day?")
    Call<ItemSearchModel> getTrendingTvShows(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/tv/on_the_air?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/on_the_air?")
    Call<ItemSearchModel> getOnTheAirTvShows(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //https://api.themoviedb.org/3/tv/popular?api_key=94dbfb1226f3ca1d16aecb852f53d4d8
    @GET("/3/tv/popular?")
    Call<ItemSearchModel> getPopularTvShows(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
