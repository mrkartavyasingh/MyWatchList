package com.android.mywatchlist.fragments.homepage.tv_shows;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.GlobalVariable;
import com.android.mywatchlist.R;
import com.android.mywatchlist.SignInActivity;
import com.android.mywatchlist.adapter.TrailerAndClipsRecyclerViewHolder;
import com.android.mywatchlist.adapter.TvShowAdapter.EpisodesRecyclerViewHolder;
import com.android.mywatchlist.databinding.FragmentTvShowSeasonElementBinding;
import com.android.mywatchlist.models.FireStoreDatabaseModel;
import com.android.mywatchlist.models.VideosModel;
import com.android.mywatchlist.models.tv_shows_model.TvShowSeasonElementModel;
import com.android.mywatchlist.request.FirebaseAuthService;
import com.android.mywatchlist.request.FirebaseService;
import com.android.mywatchlist.request.Service;
import com.android.mywatchlist.utils.Credentials;
import com.android.mywatchlist.utils.ItemApi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.Timestamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class TvShowSeasonElementFragment extends Fragment {

    private FragmentTvShowSeasonElementBinding fragmentTvShowSeasonElementBinding;
    private final TrailerAndClipsRecyclerViewHolder TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER = new TrailerAndClipsRecyclerViewHolder();
    private final EpisodesRecyclerViewHolder EPISODES_AND_CLIPS_RECYCLE_VIEW_HOLDER = new EpisodesRecyclerViewHolder();
    private final ItemApi itemApi = Service.getItemApi();
    private TvShowSeasonElementModel tvShowSeasonElementObject;
    private View view;

    public TvShowSeasonElementFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tv_show_season_element, container, false);
        fragmentTvShowSeasonElementBinding = FragmentTvShowSeasonElementBinding.bind(view);

        try {
            Thread thread = new Thread(() -> {
                try {
                    GetRetrofitTvShowSeasonResponse();
                    GetRetrofitVideosTvShowSeasonItemResponse();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (tvShowSeasonElementObject != null) {
            Log.d("tv_show_season", String.valueOf(tvShowSeasonElementObject.getId()));
            Glide
                    .with(this)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + tvShowSeasonElementObject.getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(fragmentTvShowSeasonElementBinding.posterImage);
            fragmentTvShowSeasonElementBinding.tvShowSeasonTitleTextView.setText(tvShowSeasonElementObject.getName());

            if (!tvShowSeasonElementObject.getOverview().isEmpty()) {
                fragmentTvShowSeasonElementBinding.tvShowSeasonOverviewTextView.setText(tvShowSeasonElementObject.getOverview());
                fragmentTvShowSeasonElementBinding.tvShowSeasonOverviewTextView.setOnClickListener(v -> fragmentTvShowSeasonElementBinding.tvShowSeasonOverviewTextView.setMaxLines(Integer.MAX_VALUE));
            } else {
                fragmentTvShowSeasonElementBinding.overviewText.setVisibility(View.GONE);
            }

            fragmentTvShowSeasonElementBinding.episodesNoTextView.setText("Episodes: " + tvShowSeasonElementObject.getEpisodes().size());
            fragmentTvShowSeasonElementBinding.releasedDateTextView.setText(tvShowSeasonElementObject.getAir_date());

            if (!GlobalVariable.getVideosItemObject().getResults().isEmpty()) {
                setVideosRecyclerView(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            } else {
                fragmentTvShowSeasonElementBinding.videosLinearLayout.setVisibility(View.GONE);
            }
            setEpisodesRecyclerView(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

            ArrayList<FireStoreDatabaseModel> fireStoreTvShowMWLArray = requireActivity().getIntent().getParcelableArrayListExtra("firestore_tv_show_mwl");

            if (requireActivity().getIntent().getBooleanExtra("mwl", false)) {
                assert fireStoreTvShowMWLArray != null;
                for (FireStoreDatabaseModel fireStoreTvShowMWLModel : fireStoreTvShowMWLArray) {
                    Log.d("tv_show_season", fireStoreTvShowMWLModel.getSeason_id() + " : " + tvShowSeasonElementObject.getId());
                    if (fireStoreTvShowMWLModel.getSeason_id() == tvShowSeasonElementObject.getId()) {
                        fragmentTvShowSeasonElementBinding.addMyWatchListButton.setVisibility(View.INVISIBLE);
                        fragmentTvShowSeasonElementBinding.deleteButton.setVisibility(View.VISIBLE);
                        break;
                    }
                    fragmentTvShowSeasonElementBinding.addMyWatchListButton.setVisibility(View.VISIBLE);
                    fragmentTvShowSeasonElementBinding.deleteButton.setVisibility(View.INVISIBLE);
                }
            }
            fragmentTvShowSeasonElementBinding.addMyWatchListButton.setOnClickListener(v -> addToMyWatchList());
            fragmentTvShowSeasonElementBinding.addMyWatchListAnimeButton.setOnClickListener(v -> addToMyWatchListAnime());
            fragmentTvShowSeasonElementBinding.deleteButton.setOnClickListener(view1 -> {
                FirebaseService
                        .reference_mwl_tv_shows(Objects.requireNonNull(FirebaseAuthService.getFirebaseAuth().getCurrentUser()).getUid())
                        .document(String.valueOf(tvShowSeasonElementObject.getId()))
                        .delete()
                        .addOnCompleteListener(task -> {
                            Toast.makeText(requireContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                            requireActivity().finish();
                        });
            });
        }
        return view;
    }

    private void addToMyWatchList() {
        if (FirebaseAuthService.getFirebaseAuth().getCurrentUser() != null) {
            FireStoreDatabaseModel fireStoreDatabaseModel = new FireStoreDatabaseModel();

            assert getArguments() != null;
            fireStoreDatabaseModel.setId(getArguments().getInt("tv_show_id"));
            fireStoreDatabaseModel.setSeason_id(tvShowSeasonElementObject.getId());
            fireStoreDatabaseModel.setName(getArguments().getString("tv_show_name") + ": " + tvShowSeasonElementObject.getName());
            fireStoreDatabaseModel.setTimeStamp(Timestamp.now());
            fireStoreDatabaseModel.setPoster_path(tvShowSeasonElementObject.getPoster_path());
            fireStoreDatabaseModel.setSeason_number(tvShowSeasonElementObject.getSeason_number());
            fireStoreDatabaseModel.setGenres(getArguments().getParcelableArrayList("genres"));

            Log.d("tv_show_season", fireStoreDatabaseModel.getName());
            GlobalVariable.setFireStoreDatabaseObject(fireStoreDatabaseModel);
            FirebaseService
                    .reference_mwl_tv_shows(FirebaseAuthService.getFirebaseAuth().getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseService
                                    .reference_mwl_tv_shows(FirebaseAuthService.getFirebaseAuth().getCurrentUser().getUid())
                                    .document(String.valueOf(tvShowSeasonElementObject.getId()))
                                    .set(GlobalVariable.getFireStoreDatabaseObject())
                                    .addOnCompleteListener(_task -> Toast.makeText(view.getContext(), "Added Successfully!!!", Toast.LENGTH_SHORT).show());

                        }
                    });
        } else {
            Intent intent = new Intent(view.getContext(), SignInActivity.class);
            activityResultLauncher.launch(intent);
        }
    }

    private void addToMyWatchListAnime() {
        if (FirebaseAuthService.getFirebaseAuth().getCurrentUser() != null) {
            FireStoreDatabaseModel fireStoreDatabaseModel = new FireStoreDatabaseModel();

            assert getArguments() != null;
            fireStoreDatabaseModel.setId(getArguments().getInt("tv_show_id"));
            fireStoreDatabaseModel.setSeason_id(tvShowSeasonElementObject.getId());
            fireStoreDatabaseModel.setName(getArguments().getString("tv_show_name") + ": " + tvShowSeasonElementObject.getName());
            fireStoreDatabaseModel.setTimeStamp(Timestamp.now());
            fireStoreDatabaseModel.setPoster_path(tvShowSeasonElementObject.getPoster_path());
            fireStoreDatabaseModel.setSeason_number(tvShowSeasonElementObject.getSeason_number());
            fireStoreDatabaseModel.setGenres(getArguments().getParcelableArrayList("genres"));

            Log.d("tv_show_season", fireStoreDatabaseModel.getName());
            GlobalVariable.setFireStoreDatabaseObject(fireStoreDatabaseModel);
            FirebaseService
                    .reference_mwl_anime(FirebaseAuthService.getFirebaseAuth().getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseService
                                    .reference_mwl_anime(FirebaseAuthService.getFirebaseAuth().getCurrentUser().getUid())
                                    .document(String.valueOf(tvShowSeasonElementObject.getId()))
                                    .set(GlobalVariable.getFireStoreDatabaseObject())
                                    .addOnCompleteListener(_task -> Toast.makeText(view.getContext(), "Added Successfully to anime!!!", Toast.LENGTH_SHORT).show());
                        }
                    });
        } else {
            Intent intent = new Intent(view.getContext(), SignInActivity.class);
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

    private void setVideosRecyclerView(RecyclerView.LayoutManager layoutManager) {
        TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER.setTrailerAndClipsModelList(GlobalVariable.getVideosItemObject().getResults());
        fragmentTvShowSeasonElementBinding.videosRecyclerView.setAdapter(TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER);
        fragmentTvShowSeasonElementBinding.videosRecyclerView.setLayoutManager(layoutManager);
    }

    private void setEpisodesRecyclerView(RecyclerView.LayoutManager layoutManager) {
        EPISODES_AND_CLIPS_RECYCLE_VIEW_HOLDER.setEpisodeModelList(tvShowSeasonElementObject.getEpisodes());
        fragmentTvShowSeasonElementBinding.episodesRecyclerView.setAdapter(EPISODES_AND_CLIPS_RECYCLE_VIEW_HOLDER);
        fragmentTvShowSeasonElementBinding.episodesRecyclerView.setLayoutManager(layoutManager);
    }

    private void GetRetrofitTvShowSeasonResponse() throws IOException {
        assert getArguments() != null;
        Log.d("tv_show_season", String.valueOf(getArguments().getInt("tv_show_id")));
        Call<TvShowSeasonElementModel> responseCall = itemApi.getTvShowSeasonById(getArguments().getInt("tv_show_id"), getArguments().getInt("season_number"), Credentials.API_KEY);
        Response<TvShowSeasonElementModel> response = responseCall.execute();
        tvShowSeasonElementObject = response.body();
    }

    private void GetRetrofitVideosTvShowSeasonItemResponse() throws IOException {
        assert getArguments() != null;
        Call<VideosModel> responseCall = itemApi.getVideosSeasonByTvShowId(getArguments().getInt("tv_show_id"), getArguments().getInt("season_number"), Credentials.API_KEY);
        Response<VideosModel> response = responseCall.execute();
        GlobalVariable.setVideoItemObject(response.body());
    }
}