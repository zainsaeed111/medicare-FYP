package com.example.mediworld.Shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediworld.R;
import com.example.mediworld.databinding.FragmentMyShopBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyShop extends Fragment {

    private FragmentMyShopBinding binding;
    private DatabaseReference databaseReference;
    private static final String PREF_Shop_KEY = "Shop_KEY";
    private static final String Shop_KEY = "shopKey";
    private static final String PREF_Shop_KEY_New = "Shop_KEY_New";
    private static final String Shop_KEY_New = "newshopKey";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyShopBinding.bind(view);

        String loginUShopKey = retrieveShopKey(requireContext());
        Log.d("LoginUShopKey", loginUShopKey); // Check the value of loginUShopKey

        ValueEventListener valueEventListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();
                    Log.d("UserKey", userKey); // Check the value of userKey
                    if (userKey.equals(loginUShopKey)) {
                        String shopName = userSnapshot.child("shopName").getValue(String.class);
                        String regNo = userSnapshot.child("regNo").getValue(String.class);
                        String phone = userSnapshot.child("phone").getValue(String.class);
                        String location = userSnapshot.child("location").getValue(String.class);

                        binding.tvshopnameMyshop.setText(shopName);
                        binding.tvregnoMyshop.setText(regNo);
                        binding.tvphoneMyshop.setText(phone);
                        binding.tvshopnametitleMyshop.setText(shopName);
                        binding.tvshoplocationMyshop.setText(location);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors here
            }
        };

        databaseReference.child("MyShop").addListenerForSingleValueEvent(valueEventListener);



        binding.iconeditBusinessinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current values from the TextViews
                String shopName = binding.tvshopnameMyshop.getText().toString();
                String regNo = binding.tvregnoMyshop.getText().toString();
                String phone = binding.tvphoneMyshop.getText().toString();
                String location = binding.tvshoplocationMyshop.getText().toString();


                Bundle bundle = new Bundle();
                bundle.putString("shopKey", loginUShopKey);
                bundle.putString("shopName", shopName);
                bundle.putString("regNo", regNo);
                bundle.putString("phone", phone);
                bundle.putString("location", location);

                Navigation.findNavController(view).navigate(R.id.action_bnmyShop_to_updateBusinessInfo, bundle);
            }
        });

        binding.iconchnagepassMyShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("shopKey", loginUShopKey);
                Navigation.findNavController(view).navigate(R.id.action_bnmyShop_to_updateShopPassword, bundle);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyShopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private static String retrieveShopKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_Shop_KEY_New, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Shop_KEY_New, "");
    }

}