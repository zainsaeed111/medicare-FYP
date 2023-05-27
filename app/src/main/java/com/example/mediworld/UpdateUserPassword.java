package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediworld.databinding.FragmentUpdateUserPasswordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateUserPassword extends Fragment {

    private FragmentUpdateUserPasswordBinding binding;
    private DatabaseReference databaseReference;
    private String userKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUpdateUserPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("MyUser");

        // Retrieve the userKey from the bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            userKey = bundle.getString("userKey");
        }

        // Retrieve the existing password from the database
        databaseReference.child(userKey).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
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
                databaseReference.child(userKey).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String currentPassword = dataSnapshot.getValue(String.class);
                        if (currentPassword != null && currentPassword.equals(oldPassword)) {
                            // Update the password in the database
                            databaseReference.child(userKey).child("password").setValue(newPassword);
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
        binding = null;
    }
}