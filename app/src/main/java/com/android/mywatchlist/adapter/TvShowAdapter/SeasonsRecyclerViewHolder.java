package com.android.mywatchlist.adapter.TvShowAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.submodels.GenreModel;
import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.SeasonsModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class SeasonsRecyclerViewHolder extends RecyclerView.Adapter<SeasonsRecyclerViewHolder.ViewHolder> {

    private ArrayList<SeasonsModel> seasonsModelList;
    private ArrayList<GenreModel> genreModelArrayList;
    private int tv_show_id;
    private String tv_show_name;
    View view;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_element_card_view_home_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemNameTextView.setText(seasonsModelList.get(position).getName());
        if (seasonsModelList.get(position).getPoster_path() != null) {
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + seasonsModelList.get(position).getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.itemImagePoster);
        } else {
            holder.itemImagePoster.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        Log.d("season_recyclerView", seasonsModelList.toString());

        holder.cardView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("season_number", seasonsModelList.get(position).getSeason_number());
            bundle.putInt("tv_show_id", tv_show_id);
            bundle.putString("tv_show_name", tv_show_name);
            bundle.putParcelableArrayList("genres",genreModelArrayList);
            Navigation.createNavigateOnClickListener(R.id.action_tvShowElementFragment_to_tvShowSeasonElementFragment, bundle)
                    .onClick(holder.cardView);
        });
    }

    @Override
    public int getItemCount() {
        if (seasonsModelList != null) {
            return seasonsModelList.size();
        }
        return 0;
    }

    public void setSeasonsModelList(ArrayList<SeasonsModel> seasonsModelList, int tv_show_id, String tv_show_name, ArrayList<GenreModel> genreModelArrayList) {
        this.seasonsModelList = seasonsModelList;
        this.tv_show_id = tv_show_id;
        this.tv_show_name = tv_show_name;
        this.genreModelArrayList = genreModelArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemImagePoster;
        private final TextView itemNameTextView;
        private final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImagePoster = itemView.findViewById(R.id.element_image_poster_HomePage);
            itemNameTextView = itemView.findViewById(R.id.element_name_TextView_HomePage);
            cardView = itemView.findViewById(R.id.element_item_CardView_HomePage);
        }
    }
}
