package com.android.mywatchlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.GlobalVariable;
import com.android.mywatchlist.R;
import com.android.mywatchlist.databinding.RecyElementSearchCardViewBinding;
import com.android.mywatchlist.models.FireStoreDatabaseModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MyWatchListViewHolder extends RecyclerView.Adapter<MyWatchListViewHolder.ViewHolder> {
    private ArrayList<FireStoreDatabaseModel> fireStoreDatabaseModelList;

    public MyWatchListViewHolder() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_element_search_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (fireStoreDatabaseModelList.get(position).getTitle() != null) {
            holder.itemNameTextView.setText(fireStoreDatabaseModelList.get(position).getTitle());
        } else {
            holder.itemNameTextView.setText(fireStoreDatabaseModelList.get(position).getName());
        }

        if (fireStoreDatabaseModelList.get(position).getPoster_path() != null) {
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + fireStoreDatabaseModelList.get(position).getPoster_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.itemImagePoster);
        }

        holder.itemCardView.setOnClickListener(v -> {

            if (fireStoreDatabaseModelList.get(position).getTitle() != null) {
                Toast.makeText(v.getContext(), fireStoreDatabaseModelList.get(position).getTitle() + " is clicked", Toast.LENGTH_SHORT).show();
                GlobalVariable.openItemElementActivity(v.getContext(), v.getContext().getString(R.string.movies), fireStoreDatabaseModelList.get(position).getId(), true, null);
            } else if (fireStoreDatabaseModelList.get(position).getName() != null) {
                Toast.makeText(v.getContext(), fireStoreDatabaseModelList.get(position).getName() + " is clicked", Toast.LENGTH_SHORT).show();
                GlobalVariable.openItemElementActivity(v.getContext(), v.getContext().getString(R.string.tv_shows), fireStoreDatabaseModelList.get(position).getId(), true, fireStoreDatabaseModelList);
            }

        });

    }

    @Override
    public int getItemCount() {
        if (fireStoreDatabaseModelList != null) {
            return fireStoreDatabaseModelList.size();
        }
        return 0;
    }

    public void setFireStoreDatabaseModelList(ArrayList<FireStoreDatabaseModel> fireStoreDatabaseModelList) {
        this.fireStoreDatabaseModelList = fireStoreDatabaseModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyElementSearchCardViewBinding recyElementSearchCardViewBinding;
        private final TextView itemNameTextView;
        private final ImageView itemImagePoster;
        private final CardView itemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyElementSearchCardViewBinding = RecyElementSearchCardViewBinding.bind(itemView);
            itemNameTextView = recyElementSearchCardViewBinding.elementNameTextView;
            itemImagePoster = recyElementSearchCardViewBinding.elementImagePoster;
            itemCardView = recyElementSearchCardViewBinding.elementItemCardView;
        }
    }
}