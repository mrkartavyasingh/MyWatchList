package com.android.mywatchlist.fragments;

import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.GlobalVariable;
import com.android.mywatchlist.R;
import com.android.mywatchlist.adapter.ItemRecyclerViewHolder;
import com.android.mywatchlist.adapter.MyWatchListViewHolder;
import com.android.mywatchlist.databinding.ActivitySearchItemBinding;
import com.android.mywatchlist.fragments.homepage.AdvanceSearchFragment;
import com.android.mywatchlist.models.FireStoreDatabaseModel;
import com.android.mywatchlist.models.ItemSearchModel;
import com.android.mywatchlist.models.ItemSearchResultModel;
import com.android.mywatchlist.models.submodels.GenreModel;
import com.android.mywatchlist.request.Service;
import com.android.mywatchlist.utils.Credentials;
import com.android.mywatchlist.utils.ItemApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchItemActivity extends AppCompatActivity implements AdvanceSearchFragment.OnCallbackListener {
    private ActivitySearchItemBinding activitySearchItemBinding;
    private ItemRecyclerViewHolder ITEM_RECYCLE_VIEW_HOLDER;
    private MyWatchListViewHolder MWL_ITEM_RECYCLE_VIEW_HOLDER;
    private RecyclerView search_itemRecyclerView;
    private TextView no_result_available_TextView;
    private final ItemSearchModel itemSearchResponse = new ItemSearchModel();
    private final ItemApi itemApi = Service.getItemApi();
    private final Bundle args = new Bundle();
    private ArrayList<GenreModel> selectedGenreModelArrayList;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchItemBinding = ActivitySearchItemBinding.inflate(getLayoutInflater());
        setContentView(activitySearchItemBinding.getRoot());

        Toolbar appBar = activitySearchItemBinding.searchMovieActivityToolbar;
        setSupportActionBar(appBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        appBar.setNavigationIcon(R.drawable.icon_back_button);
        appBar.setNavigationOnClickListener(v -> onBackPressed());

        no_result_available_TextView = activitySearchItemBinding.noResultAvailableTextView;
        search_itemRecyclerView = activitySearchItemBinding.searchMovieRecyclerView;

        ITEM_RECYCLE_VIEW_HOLDER = new ItemRecyclerViewHolder(false);
        MWL_ITEM_RECYCLE_VIEW_HOLDER = new MyWatchListViewHolder();

        if (Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.movies)) || Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.tv_shows)))
            activitySearchItemBinding.advanceSearchButton.setVisibility(View.INVISIBLE);

        AdvanceSearchFragment dialogFragment = AdvanceSearchFragment.newInstance();

        SearchView searchView = activitySearchItemBinding.searchMovieActivitySearchView;
        if (Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.my_watch_list)) && !GlobalVariable.getMwl().isEmpty()) {
            searchView.setQueryHint(
                    GlobalVariable.getMwl().get(0).getTitle() != null
                            ? getString(R.string.search_view_movies_query_hint)
                            : getString(R.string.search_view_tv_shows_query_hint)
            );
        } else {
            searchView.setQueryHint(
                    Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.movies))
                            ? getString(R.string.search_view_movies_query_hint)
                            : getString(R.string.search_view_tv_shows_query_hint)
            );
        }

        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.requestFocusFromTouch();

        args.putString("item_type", getIntent().getStringExtra("item_type"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setSearchText(newText);

                if (Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.movies)) || Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.tv_shows))) {
                    itemSearchResponse.setPage_no(1);
                    GetRetrofitItemSearchResponse(newText);

                    search_itemRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                            assert layoutManager != null;
                            int visibleItemCount = layoutManager.getChildCount();
                            int totalItemCount = layoutManager.getItemCount();
                            int firstVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();

                            if (firstVisibleItems + visibleItemCount >= totalItemCount && firstVisibleItems >= 0 && itemSearchResponse.getTotal_pages() > itemSearchResponse.getPage_no()) {

                                if (Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.movies)) || Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.tv_shows))) {
                                    itemSearchResponse.setPage_no(itemSearchResponse.getPage_no() + 1);
                                    GetRetrofitItemSearchResponse(newText);
                                }
                            }
                            super.onScrollStateChanged(recyclerView, newState);
                        }
                    });
                } else if (Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.my_watch_list)) && !GlobalVariable.getMwl().isEmpty()) {
                    setMwlSearchData();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select the Category...", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                return true;
            }
        });

        activitySearchItemBinding.advanceSearchButton.setOnClickListener(view -> {
            dialogFragment.setArguments(args);
            dialogFragment.show(getSupportFragmentManager(), "adv_search_fragment");
        });

    }

    private void setItemsRecycleView(ArrayList<ItemSearchResultModel> items, @Nullable RecyclerView.LayoutManager layout, ItemSearchModel itemSearchResponse) {

        if (itemSearchResponse.getPage_no() == 1) {
            ITEM_RECYCLE_VIEW_HOLDER.setItemModelList(items);
            search_itemRecyclerView.setAdapter(ITEM_RECYCLE_VIEW_HOLDER);
            search_itemRecyclerView.setLayoutManager(layout);
        } else if (itemSearchResponse.getPage_no() > 1 && itemSearchResponse.getPage_no() <= itemSearchResponse.getTotal_pages()) {
            ITEM_RECYCLE_VIEW_HOLDER.notifyItemInserted(previous_movies_list_size);
            ITEM_RECYCLE_VIEW_HOLDER.notifyDataSetChanged();
        }
    }

    private void setMwlRecycleView(ArrayList<FireStoreDatabaseModel> items, @Nullable RecyclerView.LayoutManager layout) {
        if (items.isEmpty()) {
            no_result_available_TextView.setVisibility(View.VISIBLE);
        } else {
            no_result_available_TextView.setVisibility(View.GONE);
        }
        MWL_ITEM_RECYCLE_VIEW_HOLDER.setFireStoreDatabaseModelList(items);
        search_itemRecyclerView.setAdapter(MWL_ITEM_RECYCLE_VIEW_HOLDER);
        search_itemRecyclerView.setLayoutManager(layout);
    }

    private static ArrayList<ItemSearchResultModel> previous_movies = null;
    private static int previous_movies_list_size;
    private static ArrayList<ItemSearchResultModel> next_movies = null;

    private void GetRetrofitItemSearchResponse(String item_name) {

        Call<ItemSearchModel> responseCall = Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.movies))
                ? itemApi.getSearchMovieByName(Credentials.API_KEY, item_name, itemSearchResponse.getPage_no())
                : Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.tv_shows))
                ? itemApi.getSearchTvShowsByName(Credentials.API_KEY, item_name, itemSearchResponse.getPage_no())
                : null;

        assert responseCall != null;
        responseCall.enqueue(new Callback<ItemSearchModel>() {
            @Override
            public void onResponse(@NonNull Call<ItemSearchModel> call, @NonNull Response<ItemSearchModel> response) {

                if (response.code() == 200) {

                    assert response.body() != null;
                    next_movies = new ArrayList<>(response.body().getResults());
                    itemSearchResponse.setTotal_pages(response.body().getTotal_pages());
                    itemSearchResponse.setResult_size(next_movies.size());

                    if (itemSearchResponse.getPage_no() == 1) {
                        previous_movies = new ArrayList<>(response.body().getResults());
                    } else if (itemSearchResponse.getPage_no() > 1) {
                        previous_movies.addAll(next_movies);
                    }
                    previous_movies_list_size = previous_movies.size();

                    setItemsRecycleView(previous_movies, new GridLayoutManager(getApplicationContext(), 2), itemSearchResponse);

                    if (next_movies.isEmpty() && itemSearchResponse.getPage_no() == 1) {
                        no_result_available_TextView.setVisibility(View.VISIBLE);
                    } else {
                        no_result_available_TextView.setVisibility(View.GONE);
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.v("search_movies", "Error: " + response.errorBody().string());
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

    private void setMwlSearchData() {
        String searchText = getSearchText();
        ArrayList<GenreModel> selectedGenreModelArrayList = getSelectedGenreModelArrayList();
        ArrayList<FireStoreDatabaseModel> mwl_items = new ArrayList<>();

        if (searchText != null && (selectedGenreModelArrayList == null || selectedGenreModelArrayList.isEmpty())) {
            Log.d("search_activity", "hello1");
            if (GlobalVariable.getMwl().get(0).getTitle() != null) {
                for (FireStoreDatabaseModel fireStoreDatabaseModel : GlobalVariable.getMwl()) {
                    if ((fireStoreDatabaseModel.getTitle().toLowerCase()).contains(searchText) && !searchText.isEmpty()) {
                        mwl_items.add(fireStoreDatabaseModel);
                    }
                }
            } else if (GlobalVariable.getMwl().get(0).getName() != null) {
                for (FireStoreDatabaseModel fireStoreDatabaseModel : GlobalVariable.getMwl()) {
                    if ((fireStoreDatabaseModel.getName().toLowerCase()).contains(searchText) && !searchText.isEmpty()) {
                        mwl_items.add(fireStoreDatabaseModel);
                        Log.d("search_item", fireStoreDatabaseModel.getName());
                    }
                }
            }
        } else if ((searchText == null || searchText.equals("")) && selectedGenreModelArrayList != null) {
            Log.d("search_activity", "hello2");
            for (FireStoreDatabaseModel fireStoreDatabaseModel : GlobalVariable.getMwl()) {
                int cnt = 0;
                for (GenreModel movieGenreModel : fireStoreDatabaseModel.getGenres()) {
                    for (GenreModel genreModel : selectedGenreModelArrayList) {
                        if (Objects.equals(movieGenreModel.getName(), genreModel.getName()) && genreModel.isSelected()) {
                            cnt++;
                        }
                    }
                }
                if (cnt == selectedGenreModelArrayList.size()) {
                    mwl_items.add(fireStoreDatabaseModel);
                }
            }
        } else if (searchText != null && selectedGenreModelArrayList != null) {
            Log.d("search_activity", "hello3");
            for (FireStoreDatabaseModel fireStoreDatabaseModel : GlobalVariable.getMwl()) {
                int cnt = 0;
                for (GenreModel movieGenreModel : fireStoreDatabaseModel.getGenres()) {
                    for (GenreModel genreModel : selectedGenreModelArrayList) {
                        if (Objects.equals(movieGenreModel.getName(), genreModel.getName()) && genreModel.isSelected()) {
                            cnt++;
                        }
                    }
                }
                if (cnt == selectedGenreModelArrayList.size()) {
                    if (fireStoreDatabaseModel.getTitle() != null && fireStoreDatabaseModel.getTitle().toLowerCase().contains(searchText)) {
                        mwl_items.add(fireStoreDatabaseModel);
                    } else if (fireStoreDatabaseModel.getName() != null && fireStoreDatabaseModel.getName().toLowerCase().contains(searchText)) {
                        mwl_items.add(fireStoreDatabaseModel);
                    }
                }
            }
        }

        Log.d("search_activityz", mwl_items.toString());
        setMwlRecycleView(mwl_items, new GridLayoutManager(getApplicationContext(), 2));
    }

    @Override
    public void onActionClick(ArrayList<GenreModel> selectedGenreModelArrayList) {
        setSelectedGenreModelArrayList(selectedGenreModelArrayList);
        if (Objects.equals(getIntent().getStringExtra("item_type"), getString(R.string.my_watch_list)) && !GlobalVariable.getMwl().isEmpty()) {
            setMwlSearchData();
        }
    }

    private ArrayList<GenreModel> getSelectedGenreModelArrayList() {
        return selectedGenreModelArrayList;
    }

    private void setSelectedGenreModelArrayList(ArrayList<GenreModel> selectedGenreModelArrayList) {
        this.selectedGenreModelArrayList = selectedGenreModelArrayList;
    }

    private String getSearchText() {
        return searchText;
    }

    private void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}