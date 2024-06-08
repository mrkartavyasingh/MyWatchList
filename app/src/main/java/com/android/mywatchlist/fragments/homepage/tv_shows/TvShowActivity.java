package com.android.mywatchlist.fragments.homepage.tv_shows;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.mywatchlist.R;
import com.android.mywatchlist.databinding.ActivityTvShowBinding;

public class TvShowActivity extends AppCompatActivity {
    ActivityTvShowBinding activityTvShowBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowBinding = ActivityTvShowBinding.inflate(getLayoutInflater());
        setContentView(activityTvShowBinding.getRoot());

        setSupportActionBar(activityTvShowBinding.tvShowItemActivityAppBar);

        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.tvShowElementFragment, R.id.tvShowSeasonElementFragment)
                .build();

        NavController tvShowNavController = Navigation.findNavController(this, R.id.nav_host_tv_show_fragment);
        NavigationUI.setupActionBarWithNavController(this, tvShowNavController, mAppBarConfiguration);
    }
}