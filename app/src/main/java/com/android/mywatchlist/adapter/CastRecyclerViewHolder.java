package com.android.mywatchlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.submodels.CastMemberModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class CastRecyclerViewHolder extends RecyclerView.Adapter<CastRecyclerViewHolder.ViewHolder> {

    private List<CastMemberModel> castMemberModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cast_name_TextView.setText(castMemberModelList.get(position).getName());
        holder.cast_character_name_TextView.setText(castMemberModelList.get(position).getCharacter());
        if (castMemberModelList.get(position).getProfile_path() != null){
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + castMemberModelList.get(position).getProfile_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.cast_profile_ImageView);
        } else {
            holder.cast_profile_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }

    @Override
    public int getItemCount() {
        if (castMemberModelList != null) {
            return castMemberModelList.size();
        }
        return 0;
    }

    public void setCastMemberModelList(List<CastMemberModel> castMemberModelList) {
        this.castMemberModelList = castMemberModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView cast_profile_ImageView;
        private final TextView cast_name_TextView;
        private final TextView cast_character_name_TextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cast_profile_ImageView = itemView.findViewById(R.id.cast_profile_ImageView);
            cast_name_TextView = itemView.findViewById(R.id.cast_name_TextView);
            cast_character_name_TextView = itemView.findViewById(R.id.cast_character_name_TextView);
        }
    }
}
