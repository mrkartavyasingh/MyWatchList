package com.android.mywatchlist.fragments.homepage.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.mywatchlist.GlobalVariable;
import com.android.mywatchlist.R;
import com.android.mywatchlist.SignInActivity;
import com.android.mywatchlist.adapter.CastRecyclerViewHolder;
import com.android.mywatchlist.adapter.GenreRecyclerViewHolder;
import com.android.mywatchlist.adapter.ProductionCompaniesRecyclerViewHolder;
import com.android.mywatchlist.adapter.TrailerAndClipsRecyclerViewHolder;
import com.android.mywatchlist.databinding.ActivityMovieElementBinding;
import com.android.mywatchlist.fragments.homepage.GetRetrofitItemResponse;
import com.android.mywatchlist.models.CreditsModel;
import com.android.mywatchlist.models.FireStoreDatabaseModel;
import com.android.mywatchlist.models.VideosModel;
import com.android.mywatchlist.models.movies_model.MovieElementModel;
import com.android.mywatchlist.models.submodels.ProductionCompaniesModel;
import com.android.mywatchlist.request.FirebaseAuthService;
import com.android.mywatchlist.request.FirebaseService;
import com.android.mywatchlist.request.Service;
import com.android.mywatchlist.utils.Credentials;
import com.android.mywatchlist.utils.ItemApi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.Timestamp;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class MovieElementActivity extends AppCompatActivity {
    private ActivityMovieElementBinding activityMovieElementBinding;
    private final GenreRecyclerViewHolder GENRE_RECYCLE_VIEW_HOLDER = new GenreRecyclerViewHolder(false);
    private final ProductionCompaniesRecyclerViewHolder PRODUCTION_COMPANIES_RECYCLE_VIEW_HOLDER = new ProductionCompaniesRecyclerViewHolder();
    private final CastRecyclerViewHolder CAST_RECYCLE_VIEW_HOLDER = new CastRecyclerViewHolder();
    private final TrailerAndClipsRecyclerViewHolder TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER = new TrailerAndClipsRecyclerViewHolder();
    private final GetRetrofitItemResponse recommendations_movie = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse similar_movie = new GetRetrofitItemResponse();
    private final ItemApi itemApi = Service.getItemApi();

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieElementBinding = ActivityMovieElementBinding.inflate(getLayoutInflater());
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(activityMovieElementBinding.getRoot());

        Toolbar appBar = activityMovieElementBinding.movieItemActivityAppBar;
        setSupportActionBar(appBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        appBar.setNavigationIcon(R.drawable.icon_back_button);
        appBar.setNavigationOnClickListener(v -> onBackPressed());

        try {
            Thread thread = new Thread(() -> {
                try {
                    GetRetrofitCreditsMovieItemResponse();
                    GetRetrofitMovieItemResponse();
                    GetRetrofitVideosMovieItemResponse();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (GlobalVariable.getMovieItemResultObject() != null) {
            Glide
                    .with(this)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + GlobalVariable.getMovieItemResultObject().getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(activityMovieElementBinding.posterImage);
            activityMovieElementBinding.movieTitleTextView.setText(GlobalVariable.getMovieItemResultObject().getTitle());

            if (!GlobalVariable.getMovieItemResultObject().getOverview().isEmpty()) {
                activityMovieElementBinding.movieOverviewTextView.setText(GlobalVariable.getMovieItemResultObject().getOverview());
                activityMovieElementBinding.movieOverviewTextView.setOnClickListener(v -> activityMovieElementBinding.movieOverviewTextView.setMaxLines(Integer.MAX_VALUE));
            } else {
                activityMovieElementBinding.overviewText.setVisibility(View.GONE);
            }

            StaggeredGridLayoutManager staggeredGridLayoutManager = (GlobalVariable.getMovieItemResultObject().getGenres().size() == 1)
                    ? new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            setGenreRecyclerView(staggeredGridLayoutManager);

            setProductionCompaniesRecyclerView(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            if (!GlobalVariable.getCreditsObject().getCast().isEmpty()) {
                setCastRecyclerView(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            } else {
                activityMovieElementBinding.castLinearLayout.setVisibility(View.GONE);
            }

            if (!GlobalVariable.getVideosItemObject().getResults().isEmpty()) {
                setVideosRecyclerView(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            } else {
                activityMovieElementBinding.videosLinearLayout.setVisibility(View.GONE);
            }

            activityMovieElementBinding.releasedDateTextView.setText(GlobalVariable.getMovieItemResultObject().getRelease_date());
            float runtime = GlobalVariable.getMovieItemResultObject().getRuntime();
            String _runtime = (int) (runtime / 60) + "hrs  " + (int) (runtime % 60) + "min";
            activityMovieElementBinding.movieRuntimeTextView.setText(_runtime);
            activityMovieElementBinding.movieStatusTextView.setText(GlobalVariable.getMovieItemResultObject().getStatus());

            if (getIntent().getBooleanExtra("mwl", false)) {
                activityMovieElementBinding.addMyWatchListButton.setVisibility(View.INVISIBLE);
            }

            activityMovieElementBinding.addMyWatchListButton.setOnClickListener(v -> addToMyWatchList());

            activityMovieElementBinding.posterImage.setOnClickListener(v -> {
                if (!Objects.equals(GlobalVariable.getMovieItemResultObject().getHomepage(), "")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GlobalVariable.getMovieItemResultObject().getHomepage()));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid Url!!!", Toast.LENGTH_SHORT).show();
                }
            });
            if (getIntent().getBooleanExtra("mwl", false)) {
            activityMovieElementBinding.recommendationsMoviesLinearLayout.setVisibility(View.GONE);
                activityMovieElementBinding.similarMoviesLinearLayout.setVisibility(View.GONE);
            } else {
                recommendations_movie.GetRetrofitItemResponse_(activityMovieElementBinding.getRoot(), getString(R.string.recommended_movies), GlobalVariable.getMovieItemResultObject().getId());
                similar_movie.GetRetrofitItemResponse_(activityMovieElementBinding.getRoot(), getString(R.string.similar_movies), GlobalVariable.getMovieItemResultObject().getId());
            }
        }
    }


    private void addToMyWatchList() {
        if (FirebaseAuthService.getFirebaseAuth().getCurrentUser() != null) {
            FireStoreDatabaseModel fireStoreDatabaseModel = new FireStoreDatabaseModel();
            fireStoreDatabaseModel.setId(GlobalVariable.getMovieItemResultObject().getId());
            fireStoreDatabaseModel.setTitle(GlobalVariable.getMovieItemResultObject().getTitle());
            fireStoreDatabaseModel.setTimeStamp(Timestamp.now());
            fireStoreDatabaseModel.setPoster_path(GlobalVariable.getMovieItemResultObject().getPoster_path());
            fireStoreDatabaseModel.setGenres(GlobalVariable.getMovieItemResultObject().getGenres());

            GlobalVariable.setFireStoreDatabaseObject(fireStoreDatabaseModel);
            FirebaseService
                    .reference_mwl_movies(FirebaseAuthService.getFirebaseAuth().getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseService
                                    .reference_mwl_movies(FirebaseAuthService.getFirebaseAuth().getCurrentUser().getUid())
                                    .document(String.valueOf(GlobalVariable.getFireStoreDatabaseObject().getId()))
                                    .set(GlobalVariable.getFireStoreDatabaseObject())
                                    .addOnCompleteListener(_task -> Toast.makeText(MovieElementActivity.this, "Added Successfully!!!", Toast.LENGTH_SHORT).show());
                        }
                    });
        } else {
            Intent intent = new Intent(this, SignInActivity.class);
            activityResultLauncher.launch(intent);
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    addToMyWatchList();
                }
            });

    private void setGenreRecyclerView(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        GENRE_RECYCLE_VIEW_HOLDER.setGenreModelList(GlobalVariable.getMovieItemResultObject().getGenres());
        activityMovieElementBinding.genreRecyclerView.setAdapter(GENRE_RECYCLE_VIEW_HOLDER);
        activityMovieElementBinding.genreRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    private void setProductionCompaniesRecyclerView(RecyclerView.LayoutManager layoutManager) {
        PRODUCTION_COMPANIES_RECYCLE_VIEW_HOLDER.setProductionCompaniesModelList(GlobalVariable.getMovieItemResultObject().getProduction_companies());
        activityMovieElementBinding.productionCompaniesRecyclerView.setAdapter(PRODUCTION_COMPANIES_RECYCLE_VIEW_HOLDER);
        activityMovieElementBinding.productionCompaniesRecyclerView.setLayoutManager(layoutManager);
    }

    private void setCastRecyclerView(RecyclerView.LayoutManager layoutManager) {
        CAST_RECYCLE_VIEW_HOLDER.setCastMemberModelList(GlobalVariable.getCreditsObject().getCast());
        activityMovieElementBinding.castRecyclerView.setAdapter(CAST_RECYCLE_VIEW_HOLDER);
        activityMovieElementBinding.castRecyclerView.setLayoutManager(layoutManager);
    }

    private void setVideosRecyclerView(RecyclerView.LayoutManager layoutManager) {
        TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER.setTrailerAndClipsModelList(GlobalVariable.getVideosItemObject().getResults());
        activityMovieElementBinding.videosRecyclerView.setAdapter(TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER);
        activityMovieElementBinding.videosRecyclerView.setLayoutManager(layoutManager);
    }

    private void GetRetrofitMovieItemResponse() throws IOException {
        Call<MovieElementModel> responseCall = itemApi.getMovieById(getIntent().getIntExtra("movie_id", 0), Credentials.API_KEY);
        Response<MovieElementModel> response = responseCall.execute();
        GlobalVariable.setMovieItemResultObject(response.body());
        Log.d("movie_item", GlobalVariable.getCreditsObject().getCast().toString());

//        for (int i = 0; i < GlobalVariable.getMovieItemResultObject().getProduction_companies().size(); i++) {
//            String production_company_homepage_url = GetRetrofitProductionCompanyItemResponse(i);
//            if (!Objects.equals(production_company_homepage_url, "")) {
//                GlobalVariable.getMovieItemResultObject().getProduction_companies().get(i).setHomepage(production_company_homepage_url);
//            }
//        }
    }

    private String GetRetrofitProductionCompanyItemResponse(int position) throws IOException {
        Call<ProductionCompaniesModel> responseCall = itemApi.getProductionCompany(GlobalVariable.getMovieItemResultObject().getProduction_companies().get(position).getId(), Credentials.API_KEY);
        Response<ProductionCompaniesModel> response = responseCall.execute();

        assert response.body() != null;
        return response.body().getHomepage();
    }

    private void GetRetrofitCreditsMovieItemResponse() throws IOException {
        Call<CreditsModel> responseCall = itemApi.getCreditsByMovieId(getIntent().getIntExtra("movie_id", 0), Credentials.API_KEY);
        Response<CreditsModel> response = responseCall.execute();
        GlobalVariable.setCreditsObject(response.body());
    }

    private void GetRetrofitVideosMovieItemResponse() throws IOException {
        Call<VideosModel> responseCall = itemApi.getVideosByMovieId(getIntent().getIntExtra("movie_id", 0), Credentials.API_KEY);
        Response<VideosModel> response = responseCall.execute();
        GlobalVariable.setVideoItemObject(response.body());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getBooleanExtra("mwl", false)) {
            getMenuInflater().inflate(R.menu.movie_item_activity_toolbar_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int ITEM_ID = item.getItemId();
        if (ITEM_ID == R.id.movie_item_delete_button_menu) {
            FirebaseService
                    .reference_mwl_movies(Objects.requireNonNull(FirebaseAuthService.getFirebaseAuth().getCurrentUser()).getUid())
                    .document(String.valueOf(GlobalVariable.getMovieItemResultObject().getId()))
                    .delete()
                    .addOnCompleteListener(task -> {
                        Toast.makeText(MovieElementActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        MovieElementActivity.this.finish();
                    });
        }
        return super.onOptionsItemSelected(item);
    }
}