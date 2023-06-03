package com.example.mediworld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediworld.databinding.FragmentProductDetailBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetail extends Fragment {
    private FragmentProductDetailBinding binding;
    private DatabaseReference productRef;
    private ValueEventListener valueEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            String productId = args.getString("productId");
            Log.d("productId",productId);

            productRef = FirebaseDatabase.getInstance().getReference().child("MyShop").child("products").child(productId);
            productRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("dataSnapshot",dataSnapshot.toString());

                    if (dataSnapshot.exists()) {
                        String discount = dataSnapshot.child("discount").getValue(String.class);
                        Log.d("discount",discount);


                        int discountedPrice = dataSnapshot.child("discountedPrice").getValue(Integer.class);
                        Log.d("discountedPrice", String.valueOf(discountedPrice));
                        int Price = dataSnapshot.child("price").getValue(Integer.class);
                        Log.d("Price", String.valueOf(Price));

                        String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                        Log.d("imageUrl",imageUrl);
                        String name = dataSnapshot.child("name").getValue(String.class);

                        // Set the product details to the views
                        binding.productName.setText(name);
                        binding.productPrice.setText(String.valueOf(Price));
                        binding.discountedPrice.setText(String.valueOf(discountedPrice));
                        binding.productDiscount.setText(discount);

                        // Load the image using Picasso or any other image loading library
                        Picasso.get().load(imageUrl).into(binding.productImg);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (productRef != null && valueEventListener != null) {
            productRef.removeEventListener(valueEventListener); // Remove the event listener to avoid memory leaks
        }
    }
}
