package com.example.mediworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediworld.Adapters.MyProductsAdapter;
import com.example.mediworld.Shop.Product;
import com.example.mediworld.databinding.FragmentViewMyProductsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMyProducts extends Fragment {

    private FragmentViewMyProductsBinding binding;
    private static final String PREF_Shop_KEY_New = "Shop_KEY_New";
    private static final String Shop_KEY_New = "newshopKey";
    private MyProductsAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_my_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentViewMyProductsBinding.bind(view);
        productAdapter = new MyProductsAdapter();
        binding.myprodutcsRecylerview.setAdapter(productAdapter);
        binding.myprodutcsRecylerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadProducts();
    }

    private void loadProducts() {
        String loggedInShopKey = retrieveShopKey(requireContext());
        if (TextUtils.isEmpty(loggedInShopKey)) {
            Toast.makeText(requireContext(), "Shop key not found", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("MyShop")
                .child(loggedInShopKey).child("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    productList.add(product);
                }

                // Set the loaded products in the adapter
                productAdapter.setProducts(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private static String retrieveShopKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_Shop_KEY_New, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Shop_KEY_New, "");
    }
}
