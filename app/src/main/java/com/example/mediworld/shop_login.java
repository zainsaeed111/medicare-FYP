package com.example.mediworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediworld.databinding.ActivityShopLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class shop_login extends AppCompatActivity {
    private ActivityShopLoginBinding binding;
    private static final String PREF_NAME = "MyPreferences";
    private static final String KEY_VALUE = "myValue";
    private static final String PREF_Shop_KEY = "Shop_KEY";
    private static final String Shop_KEY = "shopKey";
    private static final String PREF_Shop_KEY_New = "Shop_KEY_New";
    private static final String Shop_KEY_New = "newshopKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.tvforgetpassShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserForgetPasswordHost.class);
                startActivity(intent);

            }
        });

        binding.newShoptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopResgister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        binding.shopLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    loginShop();
                }
            }
        });
    }


    public void onLoginClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ShopResgister.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    private boolean validateForm() {
        boolean valid = true;

        // Validate license number
        String regno = binding.etregnoshopLogin.getText().toString().trim();
        if (TextUtils.isEmpty(regno)) {
            binding.etregnoshopLogin.setError("Required.");
            valid = false;
        } else {
            binding.etregnoshopLogin.setError(null);
        }

        // Validate password
        String password = binding.etpassShopLog.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            binding.etpassShopLog.setError("Required.");
            valid = false;
        } else {
            binding.etpassShopLog.setError(null);
        }

        return valid;
    }

    private void loginShop() {
        // Get the license number and password from the input fields
        final String license = binding.etregnoshopLogin.getText().toString().trim();
        final String password = binding.etpassShopLog.getText().toString().trim();

        // Check if the input fields are empty and display an error message if they are
        if (TextUtils.isEmpty(license)) {
            binding.etregnoshopLogin.setError("Please enter your license number");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.etpassShopLog.setError("Please enter your password");
            return;
        }

        // Get a reference to the Firebase database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("MyShop");

        // Query the database to find the shop with the given license number
        Query checkLicense = databaseReference.orderByChild("regNo").equalTo(license);

        // Add a listener to the query to handle the result
        checkLicense.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if a shop with the given license number exists in the database
                if (snapshot.exists()) {
                    binding.etregnoshopLogin.setError(null);

                    // Check if the password is correct for the shop
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String checkPass = childSnapshot.child("password").getValue(String.class);
                        if (checkPass != null && checkPass.equals(password)) {
                            binding.etpassShopLog.setError(null);
                            String ShopKey = childSnapshot.child("regNo").getValue(String.class);
                            // Generate a key for the user
                            String key = databaseReference.push().getKey();
                            // Shop found and password matched, store the shop key in SharedPreferences
                            //storeShopKeyNew(childSnapshot.getKey());
                            String shopKeyunique= childSnapshot.getKey();
                            Log.d("shopKeyunique",shopKeyunique);
                            storeShopKeyNew(getApplicationContext(), "Shop");
                            Log.d("userkey",shopKeyunique);
                            String ShopKeyNew= childSnapshot.getKey();
                            storeShopKeyNew(getApplicationContext(), ShopKeyNew);
                            Log.d("ShopKeyNew", ShopKeyNew);

//                            // Store the key in SharedPreferences
//                            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//                            preferences.edit().putString("shopKey", childSnapshot.getKey()).apply();
//                            // If the password is correct, start the ShopHost activity
//                            Log.d("shopKey", ShopKey);
                            //implement shared preference
                            storeValue(getApplicationContext(), "Shop");
                            storeShopKey(getApplicationContext(), ShopKey);
//                            storeShopKeyNew(childSnapshot.getKey());
//                            Log.d("storeShopKeyNew", childSnapshot.toString());

                            Intent intent = new Intent(getApplicationContext(), ShopHost.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                            return;
                        }
                    }
                    // Display an error message if the password is incorrect
                    binding.etpassShopLog.setError("Invalid password");
                } else {
                    // Display an error message if no shop with the given license number exists in the database
                    binding.etregnoshopLogin.setError("Invalid license number");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the case where the query is cancelled
                Log.e("LoginShop", "Error querying database", error.toException());
            }
        });
    }

    private static void storeValue(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_VALUE, value);
        editor.apply();
    }

    private static void storeShopKey(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_Shop_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Shop_KEY, value);
        editor.apply();
    }
    private void storeShopKeyNew(Context context, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_Shop_KEY_New, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Shop_KEY_New, value);
        editor.apply();
    }

}
