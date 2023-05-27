package com.example.mediworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediworld.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {

    private FragmentProfileBinding binding;
    private DatabaseReference databaseReference;
    private static final String PREF_USER_KEY = "USER_KEY"; // Add this constant
    private static final String USER_KEY = "userKey"; // Add this constant
    private String userKey; // Add this variable to store the userKey


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.bind(view);
        String loginUserKey = retrieveUserKey(requireContext());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userKey = userSnapshot.getKey();
                    if (userKey.equals(loginUserKey)) {
                        String email = userSnapshot.child("email").getValue(String.class);
                        String username = userSnapshot.child("username").getValue(String.class);
                        String phoneNo = userSnapshot.child("phoneno").getValue(String.class);

                        // Set the retrieved data to the TextViews
                        binding.tvprofileEmailtitle.setText(email);
                        binding.tvprofileuserEmail.setText(email);
                        binding.tvprofileUsernametitle.setText(username);
                        binding.tvprofileuserUsername.setText(username);
                        binding.tvprofileuserPhone.setText(phoneNo);


                        // Break the loop as we found the matching user
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
            }
        };

        databaseReference.child("MyUser").addListenerForSingleValueEvent(valueEventListener);


        // Inside onViewCreated or any appropriate method
        binding.iconeditPersonalinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current values from the TextViews
                String email = binding.tvprofileuserEmail.getText().toString();
                String username = binding.tvprofileuserUsername.getText().toString();
                String phoneNo = binding.tvprofileuserPhone.getText().toString();



                // Pass the profile data to the next fragment using NavController

                Bundle bundle = new Bundle();
                bundle.putString("userKey", loginUserKey);
                bundle.putString("email", email);
                bundle.putString("username", username);
                bundle.putString("phoneNo", phoneNo);

                Navigation.findNavController(v).navigate(R.id.action_userprofile_to_updatePersonalInfo, bundle);
            }
        });


        binding.iconchnagePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userKey", loginUserKey);
                Navigation.findNavController(view).navigate(R.id.action_userprofile_to_updateUserPassword, bundle);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    private static String retrieveUserKey(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_KEY, "");
    }
}