package com.android.mywatchlist.adapter.TvShowAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.EpisodeModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class EpisodesRecyclerViewHolder extends RecyclerView.Adapter<EpisodesRecyclerViewHolder.ViewHolder> {

    private List<EpisodeModel> episodeModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_episodes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (episodeModelList != null) {
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + episodeModelList.get(position).getStill_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.episode_poster_ImageView);

            holder.episode_number_TextView.setText("Episode: " + episodeModelList.get(position).getEpisode_number());
            holder.episode_name_TextView.setText(episodeModelList.get(position).getName());
            holder.episode_air_date_TextView.setText(episodeModelList.get(position).getAir_date());
            holder.episode_runtime_TextView.setText(episodeModelList.get(position).getRuntime() + " min");

            holder.episode_CardView.setOnClickListener(view -> {
                if (holder.episode_overview_TextView.getVisibility() == View.VISIBLE) {
                    holder.episode_overview_TextView.setVisibility(View.GONE);
                } else {
                    holder.episode_overview_TextView.setVisibility(View.VISIBLE);
                    holder.episode_overview_TextView.setText(episodeModelList.get(position).getOverview());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (episodeModelList != null) {
            return episodeModelList.size();
        }
        return 0;
    }

    public void setEpisodeModelList(List<EpisodeModel> episodeModelList) {
        this.episodeModelList = episodeModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView episode_CardView;
        ImageView episode_poster_ImageView;
        TextView episode_number_TextView;
        TextView episode_name_TextView;
        TextView episode_runtime_TextView;
        TextView episode_air_date_TextView;
        TextView episode_overview_TextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            episode_CardView = itemView.findViewById(R.id.episode_CardView);
            episode_poster_ImageView = itemView.findViewById(R.id.episode_poster_ImageView);
            episode_number_TextView = itemView.findViewById(R.id.episode_number_TextView);
            episode_name_TextView = itemView.findViewById(R.id.episode_name_TextView);
            episode_runtime_TextView = itemView.findViewById(R.id.episode_runtime_TextView);
            episode_air_date_TextView = itemView.findViewById(R.id.episode_air_date_TextView);
            episode_overview_TextView = itemView.findViewById(R.id.episode_overview_TextView);
        }
    }
}