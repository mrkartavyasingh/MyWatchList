package com.android.mywatchlist.fragments.homepage.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.databinding.FragmentMoviesBinding;
import com.android.mywatchlist.fragments.homepage.GetRetrofitItemResponse;
import com.android.mywatchlist.fragments.homepage.ViewAllActivity;
import com.android.mywatchlist.models.ItemSearchModel;

public class MoviesFragment extends Fragment {
    View view;
    FragmentMoviesBinding fragmentMoviesBinding;
    private final ItemSearchModel now_playing_movieSearchResponse = new ItemSearchModel();
    private final GetRetrofitItemResponse popular_movie = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse top_rated_movie = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse upcoming_movie = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse now_playing_movie = new GetRetrofitItemResponse();
    private int now_playing_item_index = 0;
    Thread now_playing_auto_slide_bg_thread;
    boolean isExit;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movies, container, false);
        fragmentMoviesBinding = FragmentMoviesBinding.bind(view);

        popular_movie.GetRetrofitItemResponse_(view, getResources().getString(R.string.popular_movies_title),0);
        top_rated_movie.GetRetrofitItemResponse_(view, getResources().getString(R.string.trending_movies_title),0);
        upcoming_movie.GetRetrofitItemResponse_(view, getResources().getString(R.string.upcoming_movies_title),0);

        now_playing_movieSearchResponse.setPage_no(1);
        now_playing_movie.GetRetrofitExpandItemResponse(view, now_playing_movieSearchResponse, getString(R.string.movies));


        Button view_all_button_popular_movies = fragmentMoviesBinding.viewAllButtonPopularMovies;
        Button view_all_button_top_rated_movies = fragmentMoviesBinding.viewAllButtonTrendingMovies;
        Button view_all_button_upcoming_movies = fragmentMoviesBinding.viewAllButtonUpcomingMovies;

        view_all_button_top_rated_movies.setOnClickListener(onClickListener_view_all_button_top_rated_movies);
        view_all_button_popular_movies.setOnClickListener(onClickListener_view_all_button_popular_movies);
        view_all_button_upcoming_movies.setOnClickListener(onClickListener_view_all_button_upcoming_movies);

        final RecyclerView now_playing_movieRecyclerView = fragmentMoviesBinding.nowPlayingMoviesRecyclerView;
        isExit = false;
        Runnable now_playing_auto_slide_runnable = () -> {
            int now_playing_item_result_index = 0;
            while (!isExit) {
                now_playing_movieRecyclerView.post(() ->
                        now_playing_movieRecyclerView.smoothScrollToPosition(now_playing_item_index));

                if (now_playing_item_result_index == now_playing_movieSearchResponse.getResult_size() - 1)
                    break;

                now_playing_item_result_index++;
                now_playing_item_index++;

                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        };
        now_playing_auto_slide_bg_thread = new Thread(now_playing_auto_slide_runnable);
        now_playing_auto_slide_bg_thread.start();
        return view;
    }


    private final View.OnClickListener onClickListener_view_all_button_popular_movies = v -> {
        loadViewAllActivity(getResources().getString(R.string.popular_movies_title));
        Toast.makeText(view.getContext(), "View All Popular Button is clicked", Toast.LENGTH_SHORT).show();
    };

    private final View.OnClickListener onClickListener_view_all_button_top_rated_movies = v -> {
        loadViewAllActivity(getResources().getString(R.string.trending_movies_title));
        Toast.makeText(view.getContext(), "View All Top Rated Button is clicked", Toast.LENGTH_SHORT).show();
    };

    private final View.OnClickListener onClickListener_view_all_button_upcoming_movies = v -> {
        loadViewAllActivity(getResources().getString(R.string.upcoming_movies_title));
        Toast.makeText(view.getContext(), "View All Upcoming Button is clicked", Toast.LENGTH_SHORT).show();
    };

    private void loadViewAllActivity(String value) {
        Intent intent = new Intent(getActivity(), ViewAllActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, value);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        fragmentMoviesBinding = null;
        now_playing_item_index = 0;
        isExit = true;
        super.onDestroyView();
    }
}
