package com.example.mediworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediworld.databinding.ActivityShopResgisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class ShopResgister extends AppCompatActivity {
TextView tvloginShopeg;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private String selectedAddress;
    private static final String ADDRESS_EXTRA_KEY = "address_extra_key";
    private ActivityShopResgisterBinding binding;
    private FirebaseAuth mAuth;
    public static final int MAP_REQUEST_CODE = 1234; // you can use any integer value here
    private static final int PLACE_PICKER_REQUEST = 1;
    public static final String LONGITUDE_EXTRA_KEY = "longitude_key";
    public static final String LATITUDE_EXTRA_KEY = "latitude_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout using View Binding
        binding = ActivityShopResgisterBinding.inflate(getLayoutInflater());
        Places.initialize(getApplicationContext(), "AIzaSyAqSbmx434bvOdz8pRmYlQ3G41NmmdTpaw");

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);
        // Set the content view using the root view of the binding
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        tvloginShopeg = findViewById(R.id.tvloginShopeg);
        tvloginShopeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), shop_login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);


            }
        });
        mAuth = FirebaseAuth.getInstance();
        binding.btnregShopreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        binding.etlocationShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(ShopResgister.this, MapsActivity.class);
               // startActivityForResult(intent, MAP_REQUEST_CODE);
            }
        });





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_REQUEST_CODE && resultCode == RESULT_OK) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String location = String.format(Locale.getDefault(), "%.6f,%.6f", latitude, longitude);
            binding.etlocationShop.setText(location);
        }
    }












    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

        }
    }


    public void onLoginClick(View view) {

        Intent intent = new Intent(getApplicationContext(),shop_login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);


    }
    private void submitForm() {
        // Validate the form...
// Validate the form...
        if (validateForm()) {
            // Here, we are sure that form is successfully validated. So, do your stuffs now...
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference("MyShop");

            String shopname = binding.etshopnameShop.getText().toString();
            String regno = binding.etregnoShop.getText().toString();
            String phone = binding.etphoneShop.getText().toString();
            String location = binding.etlocationShop.getText().toString();
            String password = binding.etpassUsereg.getText().toString().trim();
            String confirmPassword = binding.etpassconfirmUsereg.getText().toString().trim();
            // Check if a shop with the same registration number already exists
            reference.orderByChild("regno").equalTo(regno).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // A shop with the same registration number already exists, show an error message
                        Toast.makeText(ShopResgister.this, "Registration failed: Shop with the same registration number already exists", Toast.LENGTH_LONG).show();
                    } else {
                        // No shop with the same registration number exists, proceed with registration
                        ShopHelperClass helperClass = new ShopHelperClass(shopname, regno, phone, location, password, "default value for confirmPassword");

                        // Generate a unique ID for the shop
                        String uniqueId = reference.push().getKey();

                        // Add the shop details to the database using the unique ID
                        reference.child(uniqueId).setValue(helperClass)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ShopResgister.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ShopResgister.this, shop_login.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ShopResgister.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ShopResgister.this, "Registration failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }    }
    private boolean validateForm() {
        boolean isValid = true;

        if (binding.etshopnameShop.getText().toString().isEmpty()) {
            binding.etshopnameShop.setError("Shop name is required");
            isValid = false;
        }

        if (binding.etregnoShop.getText().toString().isEmpty()) {
            binding.etregnoShop.setError("Registration number is required");
            isValid = false;
        }

        String phoneRegex = "^[+]?[0-9]{10,13}$";
        if (!binding.etphoneShop.getText().toString().matches(phoneRegex)) {
            binding.etphoneShop.setError("Invalid phone number");
            isValid = false;
        }

        if (binding.etlocationShop.getText().toString().isEmpty()) {
            binding.etlocationShop.setError("Location is required");
            isValid = false;
        }

        String password = binding.etpassUsereg.getText().toString();
        String confirmPassword = binding.etpassconfirmUsereg.getText().toString();
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            binding.etpassUsereg.setError("Password is required");
            binding.etpassconfirmUsereg.setError("Confirm password is required");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            binding.etpassUsereg.setError("Passwords do not match");
            binding.etpassconfirmUsereg.setError("Passwords do not match");
            isValid = false;
        } else if (password.length() < 8) {
            binding.etpassUsereg.setError("Password must be at least 8 characters long");
            isValid = false;
        }
        return isValid;

    }








}
