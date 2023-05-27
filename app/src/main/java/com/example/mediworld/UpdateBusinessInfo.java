package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediworld.databinding.FragmentUpdateBusinessInfoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateBusinessInfo extends Fragment {
    private FragmentUpdateBusinessInfoBinding binding;
    private DatabaseReference databaseReference;
    private String shopKey;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentUpdateBusinessInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Access views using the binding object
        // For example, to set a click listener on a button:

        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("MyShop");

        // Set a ValueEventListener to update the UI when data changes
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Find the user's node using the userKey
                for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                    String key = shopSnapshot.getKey();
                    if (key.equals(shopKey)) {
                        // Get the existing data from the user's node
                        String shopName = shopSnapshot.child("shopName").getValue(String.class);
                        String regNo = shopSnapshot.child("regNo").getValue(String.class);
                        String phone = shopSnapshot.child("phone").getValue(String.class);

                        // Set the existing data in the EditText fields
                        binding.etbusinessnameMyshop.setText(shopName);
                        binding.etbusinessregnoMyshop.setText(regNo);
                        binding.etbusinessphoneMyshop.setText(phone);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled event
            }
        };

        // Retrieve the userKey from the bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            shopKey = bundle.getString("shopKey");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }

        binding.btnUpdateMyshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values from the EditText fields
                String updatedshopName = binding.etbusinessnameMyshop.getText().toString();
                String updatedregNo = binding.etbusinessregnoMyshop.getText().toString();
                String updatedphone = binding.etbusinessphoneMyshop.getText().toString();

                // Update the values in the database under the user's node
                DatabaseReference userNodeRef = databaseReference.child(shopKey);
                userNodeRef.child("shopName").setValue(updatedshopName);
                userNodeRef.child("regNo").setValue(updatedregNo);
                userNodeRef.child("phone").setValue(updatedphone);

                // Display a success message or perform any other actions
                // For example, you can use Toast to display a success message
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear the binding object to avoid memory leaks
        binding = null;
    }
}
