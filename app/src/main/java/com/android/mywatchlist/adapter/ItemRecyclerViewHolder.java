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
import com.android.mywatchlist.models.ItemSearchResultModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ItemRecyclerViewHolder extends RecyclerView.Adapter<ItemRecyclerViewHolder.ViewHolder> {
    private ArrayList<ItemSearchResultModel> itemModelList;
    private final boolean home_page;
    private boolean expand_playing;
    View view;

    public ItemRecyclerViewHolder(boolean home_page) {
        this.home_page = home_page;
    }

    public ItemRecyclerViewHolder(boolean home_page, boolean expand_playing) {
        this.home_page = home_page;
        this.expand_playing = expand_playing;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (home_page) {
            if (expand_playing) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_element_expand_card_view_home_page, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_element_card_view_home_page, parent, false);
            }
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_element_search_card_view, parent, false);
        }

        return new ViewHolder(view, home_page, expand_playing);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (expand_playing) {
            if (itemModelList.get(position).getPoster_path() != null) {
                Glide
                        .with(holder.itemView.getContext())
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w200" + itemModelList.get(position).getPoster_path())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(holder.itemImagePoster);
            } else {
                holder.itemImagePoster.setImageResource(R.drawable.placeholder);
            }
            if (itemModelList.get(position).getBackdrop_path() != null) {
                assert holder.itemBackDropImageView != null;
                Glide
                        .with(holder.itemView.getContext())
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w500" + itemModelList.get(position).getBackdrop_path())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(holder.itemBackDropImageView);
            } else {
                holder.itemImagePoster.setImageResource(R.drawable.placeholder);
            }
            assert holder.itemOverviewTextView != null;
            holder.itemOverviewTextView.setText(itemModelList.get(position).getOverview());
        } else {

            if (itemModelList.get(position).getTitle() != null) {
                holder.itemNameTextView.setText(itemModelList.get(position).getTitle());
            } else {
                holder.itemNameTextView.setText(itemModelList.get(position).getName());
            }
            if (itemModelList.get(position).getPoster_path() != null) {
                Glide
                        .with(holder.itemView.getContext())
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w500" + itemModelList.get(position).getPoster_path())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(holder.itemImagePoster);
            } else {
                holder.itemImagePoster.setImageResource(R.drawable.placeholder);
            }
        }
        holder.itemCardView.setOnClickListener(v -> {
            if (itemModelList.get(position).getTitle() != null) {
                Toast.makeText(v.getContext(), itemModelList.get(position).getTitle() + " is clicked", Toast.LENGTH_SHORT).show();
                GlobalVariable.openItemElementActivity(v.getContext(), view.getContext().getString(R.string.movies), itemModelList.get(position).getId(), false, null);
            } else {
                Toast.makeText(v.getContext(), itemModelList.get(position).getName() + " is clicked", Toast.LENGTH_SHORT).show();
                GlobalVariable.openItemElementActivity(v.getContext(), view.getContext().getString(R.string.tv_shows), itemModelList.get(position).getId(), false, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemModelList != null) {
            return itemModelList.size();
        }
        return 0;
    }

    public void setItemModelList(ArrayList<ItemSearchResultModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemNameTextView;
        private final TextView itemOverviewTextView;
        private final ImageView itemImagePoster;
        private final ImageView itemBackDropImageView;
        private final CardView itemCardView;

        public ViewHolder(@NonNull View itemView, boolean home_page, boolean now_playing) {
            super(itemView);
            if (home_page) {
                if (now_playing) {
                    itemCardView = itemView.findViewById(R.id.expand_element_HomePage_CardView);
                    itemOverviewTextView = itemView.findViewById(R.id.expand_element_overview_TextView_HomePage);
                    itemNameTextView = itemView.findViewById(R.id.expand_element_name_TextView_HomePage);
                    itemImagePoster = itemView.findViewById(R.id.expand_element_image_poster_HomePage);
                    itemBackDropImageView = itemView.findViewById(R.id.expand_element_backdrop_poster_HomePage_ImageView);
                } else {
                    itemBackDropImageView = null;
                    itemOverviewTextView = null;
                    itemNameTextView = itemView.findViewById(R.id.element_name_TextView_HomePage);
                    itemImagePoster = itemView.findViewById(R.id.element_image_poster_HomePage);
                    itemCardView = itemView.findViewById(R.id.element_item_CardView_HomePage);
                }
            } else {
                itemBackDropImageView = null;
                itemOverviewTextView = null;
                itemNameTextView = itemView.findViewById(R.id.element_name_TextView);
                itemImagePoster = itemView.findViewById(R.id.element_image_poster);
                itemCardView = itemView.findViewById(R.id.element_item_CardView);
            }
        }
    }
}