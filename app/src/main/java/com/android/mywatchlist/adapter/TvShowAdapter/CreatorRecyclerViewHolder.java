package com.android.mywatchlist.adapter.TvShowAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.CreatedByModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class CreatorRecyclerViewHolder extends RecyclerView.Adapter<CreatorRecyclerViewHolder.ViewHolder> {

    private List<CreatedByModel> createdByModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_creator, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cast_name_TextView.setText(createdByModelList.get(position).getName());
        if (createdByModelList.get(position).getProfile_path() != null){
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + createdByModelList.get(position).getProfile_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.cast_profile_ImageView);
        } else {
            holder.cast_profile_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }

    @Override
    public int getItemCount() {
        if (createdByModelList != null) {
            return createdByModelList.size();
        }
        return 0;
    }

    public void setCreatedByModelList(List<CreatedByModel> createdByModelList) {
        this.createdByModelList = createdByModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cast_profile_ImageView;
        private final TextView cast_name_TextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cast_profile_ImageView = itemView.findViewById(R.id.creator_profile_ImageView);
            cast_name_TextView = itemView.findViewById(R.id.creator_name_TextView);
        }
    }
}
