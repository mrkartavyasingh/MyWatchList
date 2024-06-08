package com.android.mywatchlist;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.mywatchlist.databinding.ActivityMainBinding;
import com.android.mywatchlist.fragments.SearchItemActivity;
import com.android.mywatchlist.models.submodels.GenreModel;
import com.android.mywatchlist.request.FirebaseAuthService;
import com.android.mywatchlist.request.Service;
import com.android.mywatchlist.utils.Credentials;
import com.android.mywatchlist.utils.ItemApi;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private AppBarConfiguration mAppBarConfiguration;
    private final ItemApi itemApi = Service.getItemApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        assert networkInfo != null;
        Log.d("mobile_data", networkInfo.getTypeName());
        // My ToolBar -------------
        assert activityMainBinding != null;
        setSupportActionBar(activityMainBinding.toolbarMain.mainActivityToolBar);

        // My Drawer -------------
        DrawerLayout mdrawerLayout = activityMainBinding.mDrawerLayout;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movies, R.id.nav_tv_shows, R.id.nav_mwl)
                .setOpenableLayout(mdrawerLayout)
                .build();

        NavController homepageNavController = Navigation.findNavController(this, R.id.nav_host_homepage_fragment);
        NavigationUI.setupActionBarWithNavController(this, homepageNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(activityMainBinding.navView, homepageNavController, false);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mdrawerLayout, activityMainBinding.toolbarMain.mainActivityToolBar, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.foregroundColor));
        mdrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        try {
            Thread thread = new Thread(() -> {
                try {
                    GetRetrofitMovieTvShowGenreItemResponse();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Log.d("main_activity", GlobalVariable.getMovieGenreModel().toString());
        Log.d("main_activity", GlobalVariable.getTvshowGenreModel().toString());

    }

    private void GetRetrofitMovieTvShowGenreItemResponse() throws IOException {
        if (GlobalVariable.getMovieGenreModel() == null) {
            Call<GenreModel.GenreModelList> responseCall = itemApi.getMoviesGenre(Credentials.API_KEY);
            Response<GenreModel.GenreModelList> response = responseCall.execute();
            assert response.body() != null;
            GlobalVariable.setMovieGenreModel(response.body().getGenres());
        }

        if (GlobalVariable.getTvshowGenreModel() == null) {
            Call<GenreModel.GenreModelList> responseCall = itemApi.getTvShowsGenre(Credentials.API_KEY);
            Response<GenreModel.GenreModelList> response = responseCall.execute();
            assert response.body() != null;
            GlobalVariable.setTvshowGenreModel(response.body().getGenres());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_homepage_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int ITEM_ID = item.getItemId();
        if (ITEM_ID == R.id.my_app_bar_search_button) {
            Intent intent = new Intent(this, SearchItemActivity.class);
            intent.putExtra("item_type", Objects.requireNonNull(activityMainBinding.navView.getCheckedItem()).getTitle());
            startActivity(intent);
        } else if (ITEM_ID == R.id.my_app_bar_account_button) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Successfully Logout!!!", Toast.LENGTH_SHORT).show();
                FirebaseAuthService.getFirebaseAuth().signOut();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (activityMainBinding.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityMainBinding.mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }
}