package com.android.mywatchlist.fragments.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.adapter.ItemRecyclerViewHolder;
import com.android.mywatchlist.databinding.ActivityViewAllBinding;
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

public class ViewAllActivity extends AppCompatActivity {
    private ActivityViewAllBinding activityViewAllBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewAllBinding = ActivityViewAllBinding.inflate(getLayoutInflater());
        setContentView(activityViewAllBinding.getRoot());

        Toolbar toolbar = activityViewAllBinding.viewAllActivityToolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_button);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        activityViewAllBinding.viewAllActivityToolbarTitle.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));

        final ItemSearchModel itemSearchResponse = new ItemSearchModel();
        final ItemRecyclerViewHolder ITEM_RECYCLE_VIEW_HOLDER = new ItemRecyclerViewHolder(false);
        final ItemApi itemApi = Service.getItemApi();

        itemSearchResponse.setPage_no(1);
        GetRetrofitViewAllResponse(itemSearchResponse, itemApi, ITEM_RECYCLE_VIEW_HOLDER);

        activityViewAllBinding.viewAllActivityMoviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();

                if (firstVisibleItems + visibleItemCount >= totalItemCount && firstVisibleItems >= 0 && itemSearchResponse.getTotal_pages() > itemSearchResponse.getPage_no()) {
                    itemSearchResponse.setPage_no(itemSearchResponse.getPage_no() + 1);
                    GetRetrofitViewAllResponse(itemSearchResponse, itemApi, ITEM_RECYCLE_VIEW_HOLDER);
                }
            }
        });
    }

    private void setMoviesRecycleView(ArrayList<ItemSearchResultModel> movies, @Nullable RecyclerView.LayoutManager layout, ItemSearchModel movieSearchResponse, ItemRecyclerViewHolder MOVIES_RECYCLE_VIEW_HOLDER) {

        if (movieSearchResponse.getPage_no() == 1) {
            MOVIES_RECYCLE_VIEW_HOLDER.setItemModelList(movies);
            activityViewAllBinding.viewAllActivityMoviesRecyclerView.setAdapter(MOVIES_RECYCLE_VIEW_HOLDER);
            activityViewAllBinding.viewAllActivityMoviesRecyclerView.setLayoutManager(layout);

        } else if (movieSearchResponse.getPage_no() > 1
                && movieSearchResponse.getPage_no() <= movieSearchResponse.getTotal_pages()) {
            MOVIES_RECYCLE_VIEW_HOLDER.notifyItemInserted(previous_movies_list_size);
            MOVIES_RECYCLE_VIEW_HOLDER.notifyDataSetChanged();
        }
    }

    private ArrayList<ItemSearchResultModel> previous_movies = null;
    private int previous_movies_list_size;
    private ArrayList<ItemSearchResultModel> next_movies = null;

    private void GetRetrofitViewAllResponse(ItemSearchModel movieSearchResponse, ItemApi itemApi, ItemRecyclerViewHolder MOVIES_RECYCLE_VIEW_HOLDER) {

        Call<ItemSearchModel> responseCall = Objects.equals(getIntent().getStringExtra(Intent.EXTRA_TEXT), getApplicationContext().getResources().getString(R.string.trending_movies_title))
                ? itemApi.getTrendingMovies(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : Objects.equals(getIntent().getStringExtra(Intent.EXTRA_TEXT), getApplicationContext().getResources().getString(R.string.popular_movies_title))
                ? itemApi.getPopularMovies(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : Objects.equals(getIntent().getStringExtra(Intent.EXTRA_TEXT), getApplicationContext().getResources().getString(R.string.upcoming_movies_title))
                ? itemApi.getUpcomingMovies(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : Objects.equals(getIntent().getStringExtra(Intent.EXTRA_TEXT), getApplicationContext().getResources().getString(R.string.trending_tv_shows_title))
                ? itemApi.getTrendingTvShows(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : Objects.equals(getIntent().getStringExtra(Intent.EXTRA_TEXT), getApplicationContext().getResources().getString(R.string.on_the_air_tv_shows_title))
                ? itemApi.getOnTheAirTvShows(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : Objects.equals(getIntent().getStringExtra(Intent.EXTRA_TEXT), getApplicationContext().getResources().getString(R.string.popular_tv_shows_title))
                ? itemApi.getPopularTvShows(Credentials.API_KEY, movieSearchResponse.getPage_no())
                : null;

        assert responseCall != null;
        responseCall.enqueue(new Callback<ItemSearchModel>() {
            @Override
            public void onResponse(@NonNull Call<ItemSearchModel> call, @NonNull Response<ItemSearchModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    next_movies = new ArrayList<>(response.body().getResults());
                    movieSearchResponse.setTotal_pages(response.body().getTotal_pages());
                    movieSearchResponse.setResult_size(next_movies.size());

                    if (movieSearchResponse.getPage_no() == 1) {
                        previous_movies = new ArrayList<>(response.body().getResults());
                        previous_movies_list_size = previous_movies.size();
                    } else if (movieSearchResponse.getPage_no() > 1) {
                        previous_movies.addAll(next_movies);
                    }
                    setMoviesRecycleView(previous_movies, new GridLayoutManager(getApplicationContext(), 2), movieSearchResponse, MOVIES_RECYCLE_VIEW_HOLDER);

                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.v("view_all_popular_movies", "Error: " + response.errorBody().string());
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