package com.example.mediworld.Shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mediworld.databinding.FragmentUpdateShopPasswordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateShopPassword extends Fragment {
    private FragmentUpdateShopPasswordBinding binding;
    private DatabaseReference databaseReference;
    private String shopKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentUpdateShopPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Access views using the binding object
        // For example, to set a click listener on a button:
        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("MyShop");

        // Retrieve the userKey from the bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            shopKey = bundle.getString("shopKey");
        }

        // Retrieve the existing password from the database
        databaseReference.child(shopKey).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentPassword = dataSnapshot.getValue(String.class);
                if (currentPassword != null) {
                    // Set the existing password to the TextInputEditText
                    binding.etoldPass.setText(currentPassword);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });

        // Add a click listener to the update button
        binding.btnUpdatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values from the EditText fields
                String oldPassword = binding.etoldPass.getText().toString();
                String newPassword = binding.etnewPass.getText().toString();
                String confirmPassword = binding.etconfirmPass.getText().toString();

                // Validate the entered values
                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(requireContext(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the old password matches the password in the database
                databaseReference.child(shopKey).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String currentPassword = dataSnapshot.getValue(String.class);
                        if (currentPassword != null && currentPassword.equals(oldPassword)) {
                            // Update the password in the database
                            databaseReference.child(shopKey).child("password").setValue(newPassword);
                            Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle onCancelled event
                    }
                });
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
