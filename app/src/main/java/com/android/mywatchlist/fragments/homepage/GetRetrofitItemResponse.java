package com.android.mywatchlist.fragments.homepage;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.adapter.ItemRecyclerViewHolder;
import com.android.mywatchlist.models.ItemSearchModel;
import com.android.mywatchlist.models.ItemSearchResultModel;
import com.android.mywatchlist.request.Service;
import com.android.mywatchlist.utils.Credentials;
import com.android.mywatchlist.utils.ItemApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRetrofitItemResponse {

    private ItemRecyclerViewHolder ITEM_RECYCLE_VIEW_HOLDER = new ItemRecyclerViewHolder(true, true);
    private RecyclerView itemRecyclerView;
    private final ItemApi itemApi = Service.getItemApi();

    public GetRetrofitItemResponse() {
    }

    public void GetRetrofitItemResponse_(View view, String getItem, int item_id) {
        itemRecyclerView = Objects.equals(getItem, view.getResources().getString(R.string.popular_movies_title))
                ? view.findViewById(R.id.popular_movies_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.trending_movies_title))
                ? view.findViewById(R.id.trending_movies_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.upcoming_movies_title))
                ? view.findViewById(R.id.upcoming_movies_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.recommended_movies))
                ? view.findViewById(R.id.recommendations_movies_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.similar_movies))
                ? view.findViewById(R.id.similar_movies_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.trending_tv_shows_title))
                ? view.findViewById(R.id.trending_tv_shows_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.on_the_air_tv_shows_title))
                ? view.findViewById(R.id.on_the_air_tv_shows_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.popular_tv_shows_title))
                ? view.findViewById(R.id.popular_tv_shows_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.recommended_tv_shows))
                ? view.findViewById(R.id.recommendations_tv_shows_recycler_view)
                : Objects.equals(getItem, view.getResources().getString(R.string.similar_tv_shows))
                ? view.findViewById(R.id.similar_tv_shows_recycler_view)
                : null;
        ;

        ITEM_RECYCLE_VIEW_HOLDER = new ItemRecyclerViewHolder(true);


        Call<ItemSearchModel> responseCall = Objects.equals(getItem, view.getResources().getString(R.string.popular_movies_title))
                ? itemApi.getPopularMovies(Credentials.API_KEY, 1)
                : Objects.equals(getItem, view.getResources().getString(R.string.trending_movies_title))
                ? itemApi.getTrendingMovies(Credentials.API_KEY, 1)
                : Objects.equals(getItem, view.getResources().getString(R.string.upcoming_movies_title))
                ? itemApi.getUpcomingMovies(Credentials.API_KEY, 1)
                : Objects.equals(getItem, view.getResources().getString(R.string.recommended_movies))
                ? itemApi.getRecommendationsByMovieId(item_id, 1, Credentials.API_KEY)
                : Objects.equals(getItem, view.getResources().getString(R.string.similar_movies))
                ? itemApi.getSimilarByMovieId(item_id, 1, Credentials.API_KEY)
                : Objects.equals(getItem, view.getResources().getString(R.string.trending_tv_shows_title))
                ? itemApi.getTrendingTvShows(Credentials.API_KEY, 1)
                : Objects.equals(getItem, view.getResources().getString(R.string.on_the_air_tv_shows_title))
                ? itemApi.getOnTheAirTvShows(Credentials.API_KEY, 1)
                : Objects.equals(getItem, view.getResources().getString(R.string.popular_tv_shows_title))
                ? itemApi.getPopularTvShows(Credentials.API_KEY, 1)
                : Objects.equals(getItem, view.getResources().getString(R.string.recommended_tv_shows))
                ? itemApi.getRecommendationsByTvShowId(item_id, 1, Credentials.API_KEY)
                : Objects.equals(getItem, view.getResources().getString(R.string.similar_tv_shows))
                ? itemApi.getSimilarByTvShowId(item_id, 1, Credentials.API_KEY)
                : null;

        assert responseCall != null;
        responseCall.enqueue(new Callback<ItemSearchModel>() {
            @Override
            public void onResponse(@NonNull Call<ItemSearchModel> call, @NonNull Response<ItemSearchModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    final ArrayList<ItemSearchResultModel> item = new ArrayList<>(response.body().getResults());

                    if (item.isEmpty()) {
                        if (Objects.equals(getItem, view.getResources().getString(R.string.recommended_movies))) {
                            view.findViewById(R.id.recommendations_movies_LinearLayout).setVisibility(View.GONE);
                        } else if (Objects.equals(getItem, view.getResources().getString(R.string.similar_movies))) {
                            view.findViewById(R.id.similar_movies_LinearLayout).setVisibility(View.GONE);
                        } else if (Objects.equals(getItem, view.getResources().getString(R.string.recommended_tv_shows))) {
                            view.findViewById(R.id.recommendations_tv_shows_LinearLayout).setVisibility(View.GONE);
                        } else if (Objects.equals(getItem, view.getResources().getString(R.string.similar_tv_shows))) {
                            view.findViewById(R.id.similar_tv_shows_LinearLayout).setVisibility(View.GONE);
                        }
                    } else {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

                        ITEM_RECYCLE_VIEW_HOLDER.setItemModelList(item);
                        itemRecyclerView.setAdapter(ITEM_RECYCLE_VIEW_HOLDER);
                        itemRecyclerView.setLayoutManager(linearLayoutManager);
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.v("home_page", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemSearchModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setItemRecycleView(ArrayList<ItemSearchResultModel> item, @Nullable RecyclerView.LayoutManager layout) {

        ITEM_RECYCLE_VIEW_HOLDER.setItemModelList(item);
        itemRecyclerView.setAdapter(ITEM_RECYCLE_VIEW_HOLDER);
        itemRecyclerView.setLayoutManager(layout);

    }

    private ArrayList<ItemSearchResultModel> expand_item = null;

    public void GetRetrofitExpandItemResponse(View view, ItemSearchModel movieSearchResponse, String typeHomepage) {

        ITEM_RECYCLE_VIEW_HOLDER = new ItemRecyclerViewHolder(true, true);
        itemRecyclerView = Objects.equals(typeHomepage, view.getContext().getString(R.string.movies))
                ? view.findViewById(R.id.now_playing_movies_recycler_view)
                : Objects.equals(typeHomepage, view.getContext().getString(R.string.tv_shows))
                ? view.findViewById(R.id.airing_today_tv_shows_recycler_view)
                : null;

        Call<ItemSearchModel> responseCall = Objects.equals(typeHomepage, view.getContext().getString(R.string.movies))
                ? itemApi.getNowPlayingMovies(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : Objects.equals(typeHomepage, view.getContext().getString(R.string.tv_shows))
                ? itemApi.getAiringTodayTvShows(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : null;

        assert responseCall != null;
        responseCall.enqueue(new Callback<ItemSearchModel>() {
            @Override
            public void onResponse(@NonNull Call<ItemSearchModel> call, @NonNull Response<ItemSearchModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    expand_item = new ArrayList<>(response.body().getResults());
                    movieSearchResponse.setResult_size(expand_item.size());

                    setItemRecycleView(expand_item, new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.v("now_playing_movies", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemSearchModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
