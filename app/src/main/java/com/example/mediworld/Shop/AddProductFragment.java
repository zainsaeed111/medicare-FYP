package com.example.mediworld.Shop;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mediworld.databinding.FragmentAddProductBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.powerspinner.PowerSpinnerView;

public class AddProductFragment extends Fragment {

    // Constants for selecting an image from the gallery
    private static final int PICK_IMAGE_REQUEST = 1;

    // View binding instance
    private FragmentAddProductBinding binding;

    // Uri of the selected image
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up the "Add Product" button
        binding.saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputFields()) {
                    // Get the values entered by the shopkeeper
                    PowerSpinnerView categorySpinner = binding.droplistaddproductCatogery;
                    String productName = binding.etaddproductName.getText().toString().trim();
                    String productDescription = binding.etaddproductDescription.getText().toString().trim();
                    String company = binding.etaddproductCompany.getText().toString().trim();
                    int price = Integer.parseInt(binding.etaddproductPrice.getText().toString().trim());
                    String discount = binding.etaddproductDiscount.getText().toString().trim();
                    int discountedPrice = TextUtils.isEmpty(discount) ? 0 : Integer.parseInt(binding.etaddproductDiscountedprice.getText().toString().trim());
                    String imageUriString = null;
                    if (imageUri != null) {
                        imageUriString = imageUri.toString();
                    }
                    // Add the product to the database
                    addProductToDatabase(String.valueOf(categorySpinner),
                            productName,
                            productDescription,
                            company,
                            price,
                            discount,
                            discountedPrice,
                            imageUriString);
                }
            }
        });

        // Set up the "imgProduct" ImageView to open the gallery when clicked
        binding.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the gallery to select an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        return view;
    }


    // Handle the selected image from the gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Hide the "imgProduct" ImageView and show the "selectedImg" ImageView
            binding.addProductlinear.setVisibility(View.GONE);
            binding.alreadyaddProductlayout.setVisibility(View.VISIBLE);

            // Load the selected image into the "selectedImg" ImageView
            Glide.with(requireActivity())
                    .load(imageUri)
                    .into(binding.selectedImg);
        }

        binding.removeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addProductlinear.setVisibility(View.VISIBLE);
                binding.alreadyaddProductlayout.setVisibility(View.GONE);

            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding when the view is destroyed to avoid memory leaks
        binding = null;
    }

    private boolean validateInputFields() {
        // Get references to the input fields using view binding
        PowerSpinnerView categorySpinner = binding.droplistaddproductCatogery;
        EditText productNameEditText = binding.etaddproductName;
        EditText productDescriptionEditText = binding.etaddproductDescription;
        EditText companyEditText = binding.etaddproductCompany;
        EditText priceEditText = binding.etaddproductPrice;
        EditText discountEditText = binding.etaddproductDiscount;
        EditText discountedPriceEditText = binding.etaddproductDiscountedprice;

        // Get the values entered by the shopkeeper
        String category = categorySpinner.getText().toString();
        String productName = productNameEditText.getText().toString().trim();
        String productDescription = productDescriptionEditText.getText().toString().trim();
        String company = companyEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();
        String discountString = discountEditText.getText().toString().trim();
        String discountedPriceString = discountedPriceEditText.getText().toString().trim();

        // Perform the validations
        if (TextUtils.isEmpty(category)) {
            categorySpinner.setError("Please select a category");
            return false;
        }
        if (TextUtils.isEmpty(productName)) {
            productNameEditText.setError("Please enter a product name");
            return false;
        }
        if (productName.length() > 20) {
            productNameEditText.setError("Product name should not be more than 20 characters");
            return false;
        }
        if (TextUtils.isEmpty(company)) {
            companyEditText.setError("Please enter a company name");
            return false;
        }
        if (TextUtils.isEmpty(priceString)) {
            priceEditText.setError("Please enter a price");
            return false;
        }
        try {
            int price = Integer.parseInt(priceString);
            if (price <= 0) {
                priceEditText.setError("Price should be greater than zero");
                return false;
            }
        } catch (NumberFormatException e) {
            priceEditText.setError("Invalid price format");
            return false;
        }
        if (!TextUtils.isEmpty(discountString)) {
            if (!discountString.endsWith("%")) {
                discountEditText.setError("Discount should be in percentage format (e.g. 5%)");
                return false;
            }
        }
        if (!TextUtils.isEmpty(discountedPriceString)) {
            try {
                int discountedPrice = Integer.parseInt(discountedPriceString);
                if (discountedPrice <= 0) {
                    discountedPriceEditText.setError("Discounted price should be greater than zero");
                    return false;
                }
                int price = Integer.parseInt(priceString);
                if (discountedPrice >= price) {
                    discountedPriceEditText.setError("Discounted price should be less than original price");
                    return false;
                }
            } catch (NumberFormatException e) {
                discountedPriceEditText.setError("Invalid discounted price format");
                return false;
            }
        }

        return true;
    }

/*
    private void addProductToDatabase(PowerSpinnerView category, String productName, String productDescription, String company, int price, String discount, int discountedPrice, String imageUrl) {
        // Get a reference to the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myShopRef = database.getReference("MyShop");

        // Check if the user is logged in and retrieve the loggedInShopKey from shared preferences
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String loggedInShopKey = sharedPreferences.getString("shopKey", "");
        if (TextUtils.isEmpty(loggedInShopKey)) {
            // loggedInShopKey not found in shared preferences, show error message and return
            Toast.makeText(getActivity(), "Shop key not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check for empty or null values for mandatory fields
        if (TextUtils.isEmpty(category.getText())) {
            category.setError("Category cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(productName)) {
            Toast.makeText(getActivity(), "Product name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(productDescription)) {
            Toast.makeText(getActivity(), "Product description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(company)) {
            Toast.makeText(getActivity(), "Company name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            Toast.makeText(getActivity(), "Image URL cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Product object with the given details
        Product product = new Product(category.getText().toString(), productName, productDescription, company, price, discount, discountedPrice, imageUrl);

        // Generate a unique key for the product and add it to the shop's list of products
        String productId = myShopRef.child(loggedInShopKey).child("products").push().getKey();
        myShopRef.child(loggedInShopKey).child("products").child(productId).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Product added successfully
                        Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add product
                        Toast.makeText(getActivity(), "Failed to add product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
*/
private void addProductToDatabase(String category, String productName, String productDescription, String company, int price, String discount, int discountedPrice, String imageUrl) {
    // Get a reference to the Firebase Realtime Database
    DatabaseReference myShopRef = FirebaseDatabase.getInstance().getReference().child("MyShop");

    // Create a Product object with the given details
    Product product = new Product(category, productName, productDescription, company, price, discount, discountedPrice, imageUrl);

    // Generate a unique key for the product and add it to the "products" node under each shop's key
    myShopRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                String shopKey = shopSnapshot.getKey();
                String productId = myShopRef.child(shopKey).child("products").push().getKey();
                myShopRef.child(shopKey).child("products").child(productId).setValue(product)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Product added successfully
                                Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to add product
                                Toast.makeText(getActivity(), "Failed to add product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // Handle database error
            Toast.makeText(getActivity(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}

}