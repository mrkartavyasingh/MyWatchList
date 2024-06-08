package com.android.mywatchlist.fragments.homepage.tv_shows;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.mywatchlist.GlobalVariable;
import com.android.mywatchlist.R;
import com.android.mywatchlist.adapter.CastRecyclerViewHolder;
import com.android.mywatchlist.adapter.GenreRecyclerViewHolder;
import com.android.mywatchlist.adapter.ProductionCompaniesRecyclerViewHolder;
import com.android.mywatchlist.adapter.TrailerAndClipsRecyclerViewHolder;
import com.android.mywatchlist.adapter.TvShowAdapter.CreatorRecyclerViewHolder;
import com.android.mywatchlist.adapter.TvShowAdapter.NetworkRecyclerViewHolder;
import com.android.mywatchlist.adapter.TvShowAdapter.SeasonsRecyclerViewHolder;
import com.android.mywatchlist.databinding.FragmentTvShowElementBinding;
import com.android.mywatchlist.fragments.homepage.GetRetrofitItemResponse;
import com.android.mywatchlist.models.CreditsModel;
import com.android.mywatchlist.models.VideosModel;
import com.android.mywatchlist.models.submodels.ProductionCompaniesModel;
import com.android.mywatchlist.models.tv_shows_model.TvShowElementModel;
import com.android.mywatchlist.request.Service;
import com.android.mywatchlist.utils.Credentials;
import com.android.mywatchlist.utils.ItemApi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class TvShowElementFragment extends Fragment {
    private View view;
    private FragmentTvShowElementBinding fragmentTvShowElementBinding;
    private final GenreRecyclerViewHolder GENRE_RECYCLE_VIEW_HOLDER = new GenreRecyclerViewHolder(false);
    private final ProductionCompaniesRecyclerViewHolder PRODUCTION_COMPANIES_RECYCLE_VIEW_HOLDER = new ProductionCompaniesRecyclerViewHolder();
    private final TrailerAndClipsRecyclerViewHolder TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER = new TrailerAndClipsRecyclerViewHolder();
    private final CastRecyclerViewHolder CAST_RECYCLE_VIEW_HOLDER = new CastRecyclerViewHolder();
    private final SeasonsRecyclerViewHolder SEASONS_RECYCLE_VIEW_HOLDER = new SeasonsRecyclerViewHolder();
    private final CreatorRecyclerViewHolder CREATOR_RECYCLE_VIEW_HOLDER = new CreatorRecyclerViewHolder();
    private final NetworkRecyclerViewHolder NETWORK_RECYCLE_VIEW_HOLDER = new NetworkRecyclerViewHolder();
    private final GetRetrofitItemResponse recommendations_tv_show = new GetRetrofitItemResponse();
    private final GetRetrofitItemResponse similar_tv_show = new GetRetrofitItemResponse();
    private final ItemApi itemApi = Service.getItemApi();
    private TvShowElementModel tvShowItemResultObject;


    public TvShowElementFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tv_show_element, container, false);
        fragmentTvShowElementBinding = FragmentTvShowElementBinding.bind(view);

        try {
            Thread thread = new Thread(() -> {
                try {
                    GetRetrofitTvShowItemResponse();
                    GetRetrofitCreditsTvShowItemResponse();
                    GetRetrofitVideosTvShowItemResponse();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (tvShowItemResultObject != null) {
            Glide
                    .with(this)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + tvShowItemResultObject.getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(fragmentTvShowElementBinding.posterImage);
            fragmentTvShowElementBinding.tvShowTitleTextView.setText(tvShowItemResultObject.getName());

            if (!tvShowItemResultObject.getOverview().isEmpty()) {
                fragmentTvShowElementBinding.tvShowOverviewTextView.setText(tvShowItemResultObject.getOverview());
                fragmentTvShowElementBinding.tvShowOverviewTextView.setOnClickListener(v -> fragmentTvShowElementBinding.tvShowOverviewTextView.setMaxLines(Integer.MAX_VALUE));

            } else {
                fragmentTvShowElementBinding.overviewText.setVisibility(View.GONE);
            }

            setProductionCompaniesRecyclerView(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

            StaggeredGridLayoutManager staggeredGridLayoutManager_Genre = (tvShowItemResultObject.getGenres().size() == 1)
                    ? new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            setGenreRecyclerView(staggeredGridLayoutManager_Genre);

            StaggeredGridLayoutManager staggeredGridLayoutManager_Network = (tvShowItemResultObject.getNetworks().size() == 1)
                    ? new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            setNetworkRecyclerView(staggeredGridLayoutManager_Network);

            fragmentTvShowElementBinding.posterImage.setOnClickListener(v -> {
                if (!Objects.equals(tvShowItemResultObject.getHomepage(), "")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tvShowItemResultObject.getHomepage()));
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "Invalid Url!!!", Toast.LENGTH_SHORT).show();
                }
            });

            if (!GlobalVariable.getCreditsObject().getCast().isEmpty()) {
                setCastRecyclerView(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            } else {
                fragmentTvShowElementBinding.castLinearLayout.setVisibility(View.GONE);
            }

            if (!tvShowItemResultObject.getSeasons().isEmpty()) {
                setSeasonsRecyclerView(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            } else {
                fragmentTvShowElementBinding.seasonsLinearLayout.setVisibility(View.GONE);
            }

            if (!GlobalVariable.getVideosItemObject().getResults().isEmpty()) {
                setVideosRecyclerView(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            } else {
                fragmentTvShowElementBinding.videosLinearLayout.setVisibility(View.GONE);
            }

            if (!tvShowItemResultObject.getCreated_by().isEmpty()) {
                setCreatorRecyclerView(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            } else {
                fragmentTvShowElementBinding.creatorRecyclerView.setVisibility(View.GONE);
            }

            fragmentTvShowElementBinding.releasedDateTextView.setText(tvShowItemResultObject.getFirst_air_date());
            fragmentTvShowElementBinding.tvShowStatusTextView.setText(tvShowItemResultObject.getStatus());

            if (requireActivity().getIntent().getBooleanExtra("mwl", false)) {
                fragmentTvShowElementBinding.recommendationsTvShowsLinearLayout.setVisibility(View.GONE);
                fragmentTvShowElementBinding.similarTvShowsLinearLayout.setVisibility(View.GONE);
            } else {
                recommendations_tv_show.GetRetrofitItemResponse_(fragmentTvShowElementBinding.getRoot(), getString(R.string.recommended_tv_shows), tvShowItemResultObject.getId());
                similar_tv_show.GetRetrofitItemResponse_(fragmentTvShowElementBinding.getRoot(), getString(R.string.similar_tv_shows), tvShowItemResultObject.getId());
            }
        }
        return view;
    }

    private void GetRetrofitCreditsTvShowItemResponse() throws IOException {
        Call<CreditsModel> responseCall = itemApi.getCreditsByTvShowId(tvShowItemResultObject.getId(), Credentials.API_KEY);
        Response<CreditsModel> response = responseCall.execute();
        GlobalVariable.setCreditsObject(response.body());
    }

    private String GetRetrofitProductionCompanyItemResponse(int position) throws IOException {
        Call<ProductionCompaniesModel> responseCall = itemApi.getProductionCompany(tvShowItemResultObject.getProduction_companies().get(position).getId(), Credentials.API_KEY);
        Response<ProductionCompaniesModel> response = responseCall.execute();

        assert response.body() != null;
        return response.body().getHomepage();
    }

    private void GetRetrofitVideosTvShowItemResponse() throws IOException {
        Call<VideosModel> responseCall = itemApi.getVideosByTvShowId(tvShowItemResultObject.getId(), Credentials.API_KEY);
        Response<VideosModel> response = responseCall.execute();
        GlobalVariable.setVideoItemObject(response.body());
    }

    private void GetRetrofitTvShowItemResponse() throws IOException {
        Call<TvShowElementModel> responseCall = itemApi.getTvShowById(requireActivity().getIntent().getIntExtra("tv_show_id", 0), Credentials.API_KEY);
        Response<TvShowElementModel> response = responseCall.execute();
        tvShowItemResultObject = response.body();

        assert tvShowItemResultObject != null;
        Log.d("tv_show_element", tvShowItemResultObject.toString());


//        for (int i = 0; i < Objects.requireNonNull(tvShowItemResultObject).getProduction_companies().size(); i++) {
//            String production_company_homepage_url = GetRetrofitProductionCompanyItemResponse(i);
//            if (!Objects.equals(production_company_homepage_url, "")) {
//                tvShowItemResultObject.getProduction_companies().get(i).setHomepage(production_company_homepage_url);
//            }
//        }
    }

    private void setProductionCompaniesRecyclerView(RecyclerView.LayoutManager layoutManager) {
        PRODUCTION_COMPANIES_RECYCLE_VIEW_HOLDER.setProductionCompaniesModelList(tvShowItemResultObject.getProduction_companies());
        fragmentTvShowElementBinding.productionCompaniesRecyclerView.setAdapter(PRODUCTION_COMPANIES_RECYCLE_VIEW_HOLDER);
        fragmentTvShowElementBinding.productionCompaniesRecyclerView.setLayoutManager(layoutManager);
    }

    private void setVideosRecyclerView(RecyclerView.LayoutManager layoutManager) {
        TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER.setTrailerAndClipsModelList(GlobalVariable.getVideosItemObject().getResults());
        fragmentTvShowElementBinding.videosRecyclerView.setAdapter(TRAILER_AND_CLIPS_RECYCLE_VIEW_HOLDER);
        fragmentTvShowElementBinding.videosRecyclerView.setLayoutManager(layoutManager);
    }

    private void setCreatorRecyclerView(RecyclerView.LayoutManager layoutManager) {
        CREATOR_RECYCLE_VIEW_HOLDER.setCreatedByModelList(tvShowItemResultObject.getCreated_by());
        fragmentTvShowElementBinding.creatorRecyclerView.setAdapter(CREATOR_RECYCLE_VIEW_HOLDER);
        fragmentTvShowElementBinding.creatorRecyclerView.setLayoutManager(layoutManager);
    }

    private void setNetworkRecyclerView(RecyclerView.LayoutManager layoutManager) {
        NETWORK_RECYCLE_VIEW_HOLDER.setNetworksModelList(tvShowItemResultObject.getNetworks());
        fragmentTvShowElementBinding.networkRecyclerView.setAdapter(NETWORK_RECYCLE_VIEW_HOLDER);
        fragmentTvShowElementBinding.networkRecyclerView.setLayoutManager(layoutManager);
    }

    private void setGenreRecyclerView(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        GENRE_RECYCLE_VIEW_HOLDER.setGenreModelList(tvShowItemResultObject.getGenres());
        fragmentTvShowElementBinding.genreRecyclerView.setAdapter(GENRE_RECYCLE_VIEW_HOLDER);
        fragmentTvShowElementBinding.genreRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    private void setCastRecyclerView(RecyclerView.LayoutManager layoutManager) {
        CAST_RECYCLE_VIEW_HOLDER.setCastMemberModelList(GlobalVariable.getCreditsObject().getCast());
        fragmentTvShowElementBinding.castRecyclerView.setAdapter(CAST_RECYCLE_VIEW_HOLDER);
        fragmentTvShowElementBinding.castRecyclerView.setLayoutManager(layoutManager);
    }

    private void setSeasonsRecyclerView(RecyclerView.LayoutManager layoutManager) {
        SEASONS_RECYCLE_VIEW_HOLDER.setSeasonsModelList(tvShowItemResultObject.getSeasons(), tvShowItemResultObject.getId(), tvShowItemResultObject.getName(), tvShowItemResultObject.getGenres());
        fragmentTvShowElementBinding.seasonsRecyclerView.setAdapter(SEASONS_RECYCLE_VIEW_HOLDER);
        fragmentTvShowElementBinding.seasonsRecyclerView.setLayoutManager(layoutManager);
    }
}