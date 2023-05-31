package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediworld.Adapters.PharmaciesMainItemAdapter;
import com.example.mediworld.Models.PharmaciesMainItemModel;
import com.example.mediworld.databinding.FragmentSeeAllProductsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class SeeAllProducts extends Fragment {

    private FragmentSeeAllProductsBinding binding;
    private PharmaciesMainItemAdapter allItemsAdapter;
    private List<PharmaciesMainItemModel> seeallItemsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout using view binding
        binding = FragmentSeeAllProductsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView allItemsRecyclerView = binding.allproductsRecycler;

        // Get the arguments passed from the previous fragment
        Bundle args = getArguments();
        if (args != null) {
            String regNo = args.getString("regNo");
            String popularItemsJson = args.getString("popularItems");
            String latestItemsJson = args.getString("latestItems");

            // Convert the JSON strings back to lists
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PharmaciesMainItemModel>>() {}.getType();
            List<PharmaciesMainItemModel> popularItemsList = gson.fromJson(popularItemsJson, listType);
            List<PharmaciesMainItemModel> latestItemsList = gson.fromJson(latestItemsJson, listType);

            // Combine the popular and latest items lists
            seeallItemsList.addAll(popularItemsList);
            seeallItemsList.addAll(latestItemsList);

            // Set the GridLayoutManager with desired number of columns
            int numColumns = 2; // You can change this value according to your needs
            allItemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
            // Set up the RecyclerView with the combined list
            allItemsAdapter = new PharmaciesMainItemAdapter(getContext(), seeallItemsList);
            allItemsRecyclerView.setAdapter(allItemsAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding
        binding = null;
    }
}