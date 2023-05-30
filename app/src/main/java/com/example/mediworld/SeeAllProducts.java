package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mediworld.Adapters.PharmaciesMainItemAdapter;
import com.example.mediworld.Models.PharmaciesMainItemModel;
import com.example.mediworld.databinding.FragmentSeeAllProductsBinding;

import java.util.ArrayList;
import java.util.List;


public class SeeAllProducts extends Fragment {

    private FragmentSeeAllProductsBinding binding;
    private PharmaciesMainItemAdapter seeallitemsAdapter;
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

        // Set up the RecyclerView with grid layout
        binding.allproductsRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        seeallitemsAdapter = new PharmaciesMainItemAdapter(getContext(), seeallItemsList);
        binding.allproductsRecycler.setAdapter(seeallitemsAdapter);

        // Populate the adapter with data or handle the data loading here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding
        binding = null;
    }
}