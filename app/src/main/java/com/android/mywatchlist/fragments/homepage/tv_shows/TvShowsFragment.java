package com.android.mywatchlist.fragments.homepage.tv_shows;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.mywatchlist.R;
import com.android.mywatchlist.databinding.FragmentTvShowsBinding;
import com.android.mywatchlist.fragments.homepage.GetRetrofitItemResponse;
import com.android.mywatchlist.fragments.homepage.ViewAllActivity;
import com.android.mywatchlist.models.ItemSearchModel;

public class TvShowsFragment extends Fragment {

    View view;
    FragmentTvShowsBinding fragmentTvShowsBinding;
    private final ItemSearchModel airing_today_tv_shows_SearchResponse = new ItemSearchModel();
    private final GetRetrofitItemResponse trending_tv_shows = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse on_the_air_tv_shows = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse popular_tv_shows = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse airing_today_tv_shows = new GetRetrofitItemResponse();
    private int airing_today_item_index = 0;
    Thread airing_today_auto_slide_bg_thread;
    boolean isExit;

    public TvShowsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tv_shows, container, false);
        fragmentTvShowsBinding = FragmentTvShowsBinding.bind(view);

        on_the_air_tv_shows.GetRetrofitItemResponse_(view, getResources().getString(R.string.trending_tv_shows_title),0);
        trending_tv_shows.GetRetrofitItemResponse_(view, getResources().getString(R.string.on_the_air_tv_shows_title),0);
        popular_tv_shows.GetRetrofitItemResponse_(view, getResources().getString(R.string.popular_tv_shows_title),0);

        airing_today_tv_shows_SearchResponse.setPage_no(1);
        airing_today_tv_shows.GetRetrofitExpandItemResponse(view, airing_today_tv_shows_SearchResponse,getString(R.string.tv_shows));

        Button view_all_button_trending_tv_shows = fragmentTvShowsBinding.viewAllButtonTrendingTvShows;
        Button view_all_button_on_the_air_tv_shows = fragmentTvShowsBinding.viewAllButtonOnTheAirTvShows;
        Button view_all_button_popular_tv_shows = fragmentTvShowsBinding.viewAllButtonPopularTvShows;

        view_all_button_trending_tv_shows.setOnClickListener(onClickListener_view_all_button_trending_tv_shows);
        view_all_button_on_the_air_tv_shows.setOnClickListener(onClickListener_view_all_button_on_the_air_tv_shows);
        view_all_button_popular_tv_shows.setOnClickListener(onClickListener_view_all_button_popular_tv_shows);

        final RecyclerView now_playing_movieRecyclerView = fragmentTvShowsBinding.airingTodayTvShowsRecyclerView;
        isExit = false;
        Runnable airing_today_auto_slide_runnable = () -> {
            int airing_today_item_result_index = 1;
            while (!isExit) {
                now_playing_movieRecyclerView.post(() ->
                        now_playing_movieRecyclerView.smoothScrollToPosition(airing_today_item_index));
                if (airing_today_item_result_index == airing_today_tv_shows_SearchResponse.getResult_size())
                    break;
                airing_today_item_result_index++;
                airing_today_item_index++;

                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        };
        airing_today_auto_slide_bg_thread = new Thread(airing_today_auto_slide_runnable);
        airing_today_auto_slide_bg_thread.start();
        return view;
    }

    private final View.OnClickListener onClickListener_view_all_button_trending_tv_shows = v -> {
        loadViewAllActivity(getResources().getString(R.string.trending_tv_shows_title));
        Toast.makeText(view.getContext(), "View All Popular Button is clicked", Toast.LENGTH_SHORT).show();
    };

    private final View.OnClickListener onClickListener_view_all_button_on_the_air_tv_shows = v -> {
        loadViewAllActivity(getResources().getString(R.string.on_the_air_tv_shows_title));
        Toast.makeText(view.getContext(), "View All Top Rated Button is clicked", Toast.LENGTH_SHORT).show();
    };

    private final View.OnClickListener onClickListener_view_all_button_popular_tv_shows = v -> {
        loadViewAllActivity(getResources().getString(R.string.popular_tv_shows_title));
        Toast.makeText(view.getContext(), "View All Upcoming Button is clicked", Toast.LENGTH_SHORT).show();
    };

    private void loadViewAllActivity(String value) {
        Intent intent = new Intent(getActivity(), ViewAllActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, value);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        fragmentTvShowsBinding = null;
        airing_today_item_index = 0;
        isExit = true;
        super.onDestroyView();
    }
}