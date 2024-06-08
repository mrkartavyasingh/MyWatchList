package com.android.mywatchlist.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mywatchlist.R;
import com.android.mywatchlist.models.submodels.ProductionCompaniesModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ProductionCompaniesRecyclerViewHolder extends RecyclerView.Adapter<ProductionCompaniesRecyclerViewHolder.ViewHolder> {

    private List<ProductionCompaniesModel> productionCompaniesModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_production_companies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.production_company_name_TextView.setText(productionCompaniesModelList.get(position).getName());

        if (productionCompaniesModelList.get(position).getLogo_path() != null) {
            Glide
                    .with(holder.itemView.getContext())
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + productionCompaniesModelList.get(position).getLogo_path())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.production_company_logo_ImageView);
        } else {
            holder.production_company_logo_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

//        holder.production_company_logo_ImageView.setOnClickListener(v -> {
//            if (productionCompaniesModelList.get(position).getHomepage() != null) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productionCompaniesModelList.get(position).getHomepage()));
//                holder.itemView.getContext().startActivity(intent);
//            } else {
//                Toast.makeText(holder.itemView.getContext(), "Invalid Url!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (productionCompaniesModelList != null) {
            return productionCompaniesModelList.size();
        }
        return 0;
    }

    public void setProductionCompaniesModelList(List<ProductionCompaniesModel> productionCompaniesModelList) {
        this.productionCompaniesModelList = productionCompaniesModelList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView production_company_name_TextView;
        private final ImageView production_company_logo_ImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            production_company_name_TextView = itemView.findViewById(R.id.production_company_name_TextView);
            production_company_logo_ImageView = itemView.findViewById(R.id.production_company_logo_ImageView);
        }
    }
}
