package com.example.mediworld.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.OnlinePharmaciesModel;
import com.example.mediworld.R;

import java.util.ArrayList;

public class OnlinePharmaciesAdapter extends RecyclerView.Adapter<OnlinePharmaciesAdapter.OnlinePharmaciesViewHolder> {

    private Context context;
    private ArrayList<OnlinePharmaciesModel> pharmaciesList;

    public OnlinePharmaciesAdapter(Context context, ArrayList<OnlinePharmaciesModel> pharmaciesList) {
        this.context = context;
        this.pharmaciesList = pharmaciesList;
    }

    @NonNull
    @Override
    public OnlinePharmaciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.online_pharmacies_single_row, parent, false);
        return new OnlinePharmaciesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlinePharmaciesViewHolder holder, int position) {
        OnlinePharmaciesModel pharmacy = pharmaciesList.get(position);
        holder.tvPharmacyName.setText(pharmacy.getShopName());
        holder.tvPharmacyLocation.setText(pharmacy.getLocation());
    }

    @Override
    public int getItemCount() {
        return pharmaciesList.size();
    }

    public class OnlinePharmaciesViewHolder extends RecyclerView.ViewHolder {

        TextView tvPharmacyName, tvPharmacyLocation;

        public OnlinePharmaciesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPharmacyName = itemView.findViewById(R.id.pharmacyName);
            tvPharmacyLocation = itemView.findViewById(R.id.pharmacyLocation);
        }
    }
}
