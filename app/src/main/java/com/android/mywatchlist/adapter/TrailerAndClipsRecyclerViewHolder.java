package com.android.mywatchlist.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.submodels.TrailerAndClipsModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TrailerAndClipsRecyclerViewHolder extends RecyclerView.Adapter<TrailerAndClipsRecyclerViewHolder.ViewHolder> {

    private List<TrailerAndClipsModel> trailerAndClipsModelList;
    Intent youtube_video_intent = new Intent(Intent.ACTION_VIEW);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_trailer_and_clips, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (Objects.equals(trailerAndClipsModelList.get(position).getSite(), "YouTube") && trailerAndClipsModelList.get(position).getKey() != null) {
            holder.youtube_type_TextView.setText(trailerAndClipsModelList.get(position).getType());
            holder.youtube_name_TextView.setText(trailerAndClipsModelList.get(position).getName());
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://img.youtube.com/vi/" + trailerAndClipsModelList.get(position).getKey() + "/maxresdefault.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            holder.icon_youtube.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .placeholder(R.drawable.placeholder)
                    .into(holder.youtube_poster);

            holder.youtube_poster_CardView.setOnClickListener(v -> {
                youtube_video_intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + trailerAndClipsModelList.get(position).getKey()));
                youtube_video_intent.setPackage("com.google.android.youtube");
                holder.itemView.getContext().startActivity(youtube_video_intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (trailerAndClipsModelList != null) {
            return trailerAndClipsModelList.size();
        }
        return 0;
    }

    public void setTrailerAndClipsModelList(List<TrailerAndClipsModel> trailerAndClipsModelList) {
        Collections.reverse(trailerAndClipsModelList);
        this.trailerAndClipsModelList = trailerAndClipsModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView youtube_poster;
        ImageView icon_youtube;
        CardView youtube_poster_CardView;
        TextView youtube_type_TextView;
        TextView youtube_name_TextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youtube_poster_CardView = itemView.findViewById(R.id.movie_item_youtube_poster_CardView);
            youtube_poster = itemView.findViewById(R.id.movie_item_youtube_poster_ImageView);
            icon_youtube = itemView.findViewById(R.id.icon_youtube);
            youtube_type_TextView = itemView.findViewById(R.id.movie_item_youtube_type_TextView);
            youtube_name_TextView = itemView.findViewById(R.id.movie_item_youtube_name_TextView);
        }
    }
}