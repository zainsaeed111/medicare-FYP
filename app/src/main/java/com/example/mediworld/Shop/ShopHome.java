package com.example.mediworld.Shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mediworld.R;
import com.example.mediworld.databinding.FragmentShopHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopHome extends Fragment {
    private FragmentShopHomeBinding binding;

    public static ShopHome newInstance(String param1, String param2) {
        ShopHome fragment = new ShopHome();
        Bundle args = new Bundle();
        // put arguments into the bundle if needed
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.addProductlinearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.addProductFragment2);
            }
        });
        // Use binding to access views
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.viewproductsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.viewMyProducts);
            }
        });
        binding.vieworderReqbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.orderRequests);
            }
        });

        binding.vieworderHistorybox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

// Navigate to the destination fragment
                navController.navigate(R.id.orderHistory);
            }
        });
    }
}
