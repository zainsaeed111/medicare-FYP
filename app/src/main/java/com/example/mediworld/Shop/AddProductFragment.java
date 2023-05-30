package com.example.mediworld.Shop;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediworld.DropdownAdapter;
import com.example.mediworld.R;
import com.example.mediworld.databinding.FragmentAddProductBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddProductFragment extends Fragment {

    // Constants for selecting an image from the gallery
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PREF_Shop_KEY_New = "Shop_KEY_New";
    private static final String Shop_KEY_New = "newshopKey";

    // View binding instance
    private FragmentAddProductBinding binding;

    // Uri of the selected image
    private Uri imageUri;


    private DropdownAdapter adapter;
    private List<String> dropdownItems;
    private DropdownAdapter subadapter;
    private List<String> subdropdownItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up the "Add Product" button

        TextView tvCategory = view.findViewById(R.id.tvCatogery);
        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.catogeryRecycler.getVisibility() == View.GONE) {
                    showDropdownList(binding.catogeryRecycler, binding.tvCatogery);
                } else {
                    hideDropdownList(binding.catogeryRecycler, binding.tvCatogery);
                }
            }
        });
        // Declare and initialize the dropdownItems list
        List<String> dropdownItems = new ArrayList<>(Arrays.asList("Medicine", "Gennral", "Suplliments", "Surgical","Other"));

// Initialize the recyclerView and set its layout manager
        RecyclerView recyclerView = view.findViewById(R.id.catogeryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

// Create the adapter and set it to the recyclerView
        DropdownAdapter adapter = new DropdownAdapter(dropdownItems, new DropdownAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String selectedItem) {
                binding.tvCatogery.setText(selectedItem);
                hideDropdownList(recyclerView, binding.tvCatogery);
            }
        });
        recyclerView.setAdapter(adapter);




        TextView tvsubCategory = view.findViewById(R.id.tvsubCatogery);
        tvsubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.subcatogeryRecycler.getVisibility() == View.GONE) {
                    showDropdownList(binding.subcatogeryRecycler, binding.tvsubCatogery);
                } else {
                    hideDropdownList(binding.subcatogeryRecycler, binding.tvsubCatogery);
                }
            }
        });



        // Declare and initialize the dropdownItems list
        List<String> subdropdownItems = new ArrayList<>(Arrays.asList("Popular", "Latest", "Men Care", "Women Care","Child"));

// Initialize the recyclerView and set its layout manager
        RecyclerView subrecyclerView = view.findViewById(R.id.subcatogeryRecycler);
        subrecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

// Create the adapter and set it to the recyclerView
        DropdownAdapter subadapter = new DropdownAdapter(subdropdownItems, new DropdownAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String selectedItem) {
                binding.tvsubCatogery.setText(selectedItem);
                hideDropdownList(subrecyclerView, binding.tvsubCatogery);
            }
        });
        subrecyclerView.setAdapter(subadapter);



        binding.saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputFields()) {
                    // Get the values entered by the shopkeeper
                    //    PowerSpinnerView categorySpinner = binding.droplistaddproductCatogery;
                    String productName = binding.etaddproductName.getText().toString().trim();
                    String Catogery = binding.tvCatogery.getText().toString().trim();
                    String subCatogery = binding.tvsubCatogery.getText().toString().trim();
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
                    addProductToDatabase(

                            Catogery,
                            subCatogery,
                            productName,
                            productDescription,
                            company,
                            price,
                            discount,
                            discountedPrice,
                            Uri.parse(imageUriString));
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
        //  PowerSpinnerView categorySpinner = binding.droplistaddproductCatogery;
        EditText productNameEditText = binding.etaddproductName;
        EditText productDescriptionEditText = binding.etaddproductDescription;
        EditText companyEditText = binding.etaddproductCompany;
        EditText priceEditText = binding.etaddproductPrice;
        EditText discountEditText = binding.etaddproductDiscount;
        EditText discountedPriceEditText = binding.etaddproductDiscountedprice;

        // Get the values entered by the shopkeeper
        String category = binding.tvCatogery.getText().toString();
        String productName = productNameEditText.getText().toString().trim();
        String productDescription = productDescriptionEditText.getText().toString().trim();
        String company = companyEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();
        String discountString = discountEditText.getText().toString().trim();
        String discountedPriceString = discountedPriceEditText.getText().toString().trim();

        // Perform the validations
        if (TextUtils.isEmpty(category)) {
            binding.tvCatogery.setError("Please select a category");
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
    private void addProductToDatabase(String category, String subcategory, String productName,
                                      String productDescription, String company, int price, String discount,
                                      int discountedPrice, Uri imageUri) {
        String loggedInShopKey = retrieveShopKey(requireContext());
        if (TextUtils.isEmpty(loggedInShopKey)) {
            Toast.makeText(requireContext(), "Shop key not found", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("MyShop").child(loggedInShopKey);

        // Generate a new key for the product
        String productId = databaseRef.child("products").push().getKey();

        // Create a Product object with the provided details
        Product product = new Product();
        product.setProductId(productId);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        product.setName(productName);
        product.setDescription(productDescription);
        product.setCompany(company);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setDiscountedPrice(discountedPrice);

        // Upload the image to Firebase Storage and retrieve the direct URL
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("images");
        String imageFileName = System.currentTimeMillis() + "." + getFileExtension(imageUri);
        StorageReference fileRef = storageRef.child(imageFileName);
        UploadTask uploadTask = fileRef.putFile(imageUri);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Image uploaded successfully, retrieve the direct URL
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    product.setImageUrl(imageUrl);

                    // Add the product to the database
                    databaseRef.child("products").child(productId).setValue(product)
                            .addOnCompleteListener(completionListener -> {
                                if (completionListener.isSuccessful()) {
                                    // Product added successfully
                                    Toast.makeText(requireContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
                                    clearInputFields();
                                } else {
                                    // Failed to add product
                                    Toast.makeText(requireContext(), "Failed to add product: " + completionListener.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                });
            } else {
                // Failed to upload image
                Toast.makeText(requireContext(), "Failed to upload image: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void showDropdownList(RecyclerView recyclerView, TextView textview) {
        recyclerView.setVisibility(View.VISIBLE);
        Drawable[] drawables = binding.tvCatogery.getCompoundDrawablesRelative();

        Drawable newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.up_ic);

        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(drawables[0], drawables[1], newDrawable, drawables[3]);
    }

    private void hideDropdownList(RecyclerView recyclerView, TextView textview) {
        recyclerView.setVisibility(View.GONE);
        Drawable[] drawables = binding.tvCatogery.getCompoundDrawablesRelative();
        Drawable newDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.down_ic);

        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(drawables[0], drawables[1], newDrawable, drawables[3]);
    }
    private static String retrieveShopKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_Shop_KEY_New, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Shop_KEY_New, "");
    }
    private void clearInputFields() {
        // Clear the input fields
        binding.etaddproductName.getText().clear();
        binding.tvCatogery.setText("");
        binding.tvsubCatogery.setText("");
        binding.etaddproductDescription.getText().clear();
        binding.etaddproductCompany.getText().clear();
        binding.etaddproductPrice.getText().clear();
        binding.etaddproductDiscount.getText().clear();
        binding.etaddproductDiscountedprice.getText().clear();
        binding.selectedImg.setImageDrawable(null);
        imageUri = null;
    }

}