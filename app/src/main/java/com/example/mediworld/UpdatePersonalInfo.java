package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mediworld.databinding.FragmentUpdatePersonalInfoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdatePersonalInfo extends Fragment {

    private FragmentUpdatePersonalInfoBinding binding;
    private DatabaseReference databaseReference;
    private String userKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUpdatePersonalInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("MyUser");

        // Set a ValueEventListener to update the UI when data changes
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Find the user's node using the userKey
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String key = userSnapshot.getKey();
                    if (key.equals(userKey)) {
                        // Get the existing data from the user's node
                        String email = userSnapshot.child("email").getValue(String.class);
                        String username = userSnapshot.child("username").getValue(String.class);
                        String phoneNo = userSnapshot.child("phoneno").getValue(String.class);

                        // Set the existing data in the EditText fields
                        binding.etuseremailUpdateprofile.setText(email);
                        binding.etusernameUpdateprofile.setText(username);
                        binding.etuserphoneUpdateprofile.setText(phoneNo);

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
            userKey = bundle.getString("userKey");
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }

        // Add a click listener to the update button
        binding.btnUpdateprofileinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values from the EditText fields
                String updatedEmail = binding.etuseremailUpdateprofile.getText().toString();
                String updatedUsername = binding.etusernameUpdateprofile.getText().toString();
                String updatedPhoneNo = binding.etuserphoneUpdateprofile.getText().toString();

                // Update the values in the database under the user's node
                DatabaseReference userNodeRef = databaseReference.child(userKey);
                userNodeRef.child("email").setValue(updatedEmail);
                userNodeRef.child("username").setValue(updatedUsername);
                userNodeRef.child("phoneno").setValue(updatedPhoneNo);

                // Display a success message or perform any other actions
                // For example, you can use Toast to display a success message
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
