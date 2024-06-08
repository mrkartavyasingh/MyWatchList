package com.android.mywatchlist.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.submodels.GenreModel;

import java.util.ArrayList;

public class GenreRecyclerViewHolder extends RecyclerView.Adapter<GenreRecyclerViewHolder.ViewHolder> {
    private ArrayList<GenreModel> genreModelList;
    private View view;
    private static boolean adv_search;

    public GenreRecyclerViewHolder(boolean adv_search) {
        GenreRecyclerViewHolder.adv_search = adv_search;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (adv_search) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_adv_search_genre, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_genre, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.genre_TextView.setText(genreModelList.get(position).getName());
        if (adv_search) {
            assert holder.genre_CardView != null;
            if (genreModelList.get(position).isSelected()) {
                holder.genre_CardView.setCardBackgroundColor(Color.parseColor(view.getResources().getString(R.color.teal_200)));
            }
            holder.genre_CardView.setOnClickListener(view1 -> {
                if ((holder.genre_CardView.getCardBackgroundColor().getDefaultColor()) == Color.parseColor(view.getResources().getString(R.color.white_200))) {
                    holder.genre_CardView.setCardBackgroundColor(Color.parseColor(view.getResources().getString(R.color.teal_200)));
                    genreModelList.get(position).setSelected(true);
                } else {
                    holder.genre_CardView.setCardBackgroundColor(Color.parseColor(view.getResources().getString(R.color.white_200)));
                    genreModelList.get(position).setSelected(false);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (genreModelList != null) {
            return genreModelList.size();
        }
        return 0;
    }

    public void setGenreModelList(ArrayList<GenreModel> genreModelList) {
        this.genreModelList = genreModelList;
    }

    public ArrayList<GenreModel> getGenreModelList() {
        return genreModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView genre_CardView;
        private final TextView genre_TextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (adv_search) {
                genre_TextView = itemView.findViewById(R.id.adv_search_genre_TextView);
                genre_CardView = itemView.findViewById(R.id.adv_search_genre_CardView);
            } else {
                genre_TextView = itemView.findViewById(R.id.genre_TextView);
                genre_CardView = null;
            }
        }
    }
}
