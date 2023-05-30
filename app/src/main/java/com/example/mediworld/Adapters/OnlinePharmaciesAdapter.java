package com.example.mediworld.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Models.OnlinePharmaciesModel;
import com.example.mediworld.R;
import com.example.mediworld.ViewPharmaciesItems;

import java.util.ArrayList;

public class OnlinePharmaciesAdapter extends RecyclerView.Adapter<OnlinePharmaciesAdapter.OnlinePharmaciesViewHolder> {
    private static final String PREF_USER_KEY = "USER_KEY";
    private static final String USER_KEY = "userKey";

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
    public void onBindViewHolder(@NonNull final OnlinePharmaciesViewHolder holder, final int position) {
        OnlinePharmaciesModel pharmacy = pharmaciesList.get(position);
        holder.tvPharmacyName.setText(pharmacy.getShopName());
        holder.tvPharmacyLocation.setText(pharmacy.getLocation());
        holder.btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regNo = pharmacy.getRegNo();
                Log.d("regNo",regNo);
                String value = retrieveValue(context);
                Log.d("UserKeyAdapter", value.toString());
                Bundle bundle = new Bundle();
                bundle.putString("UserId",value);
                bundle.putString("ShopRegId",regNo);
                Navigation.findNavController(view).navigate(R.id.action_userOnlinephar_to_chatFragment,bundle);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open the ViewPharmciesItems fragment and pass the shopName argument
                String regNo = pharmacy.getRegNo();
                Log.d("regNo",regNo);
                Log.d("regNo",pharmacy.getShopName());
                Log.d("regNo",pharmacy.getLocation());
                Bundle bundle = new Bundle();
                bundle.putString("regNo", regNo);
                String value = retrieveValue(context);
                Log.d("UserKeyAdapter", value.toString());

                ViewPharmaciesItems fragment = new ViewPharmaciesItems();
                //fragment.setArguments(bundle);
                // Get the nav controller and navigate to the new fragment
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.viewPharmciesItems,bundle);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pharmaciesList.size();
    }

    public class OnlinePharmaciesViewHolder extends RecyclerView.ViewHolder {

        TextView tvPharmacyName, tvPharmacyLocation;
        Button btnOrderNow;

        public OnlinePharmaciesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPharmacyName = itemView.findViewById(R.id.pharmacyName);
            tvPharmacyLocation = itemView.findViewById(R.id.pharmacyLocation);
            btnOrderNow=itemView.findViewById(R.id.btnChatNow);
        }
    }
    private static String retrieveValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_KEY, null);
    }
}
