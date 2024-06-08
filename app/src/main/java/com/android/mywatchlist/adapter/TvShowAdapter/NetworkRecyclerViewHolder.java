package com.android.mywatchlist.adapter.TvShowAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.tv_shows_model.tv_shows_submodels.NetworksModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class NetworkRecyclerViewHolder extends RecyclerView.Adapter<NetworkRecyclerViewHolder.ViewHolder> {

    private List<NetworksModel> networksModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_network, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (networksModelList.get(position).getLogo_path() != null){
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + networksModelList.get(position).getLogo_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.network_logo_ImageView);
        } else {
            holder.network_logo_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    public int getItemCount() {
        if (networksModelList != null) {
            return networksModelList.size();
        }
        return 0;
    }

    public void setNetworksModelList(List<NetworksModel> networksModelList) {
        this.networksModelList = networksModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView network_logo_ImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            network_logo_ImageView = itemView.findViewById(R.id.network_logo_ImageView);
        }
    }
}
