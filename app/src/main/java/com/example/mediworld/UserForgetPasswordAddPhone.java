package com.example.mediworld;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mediworld.databinding.FragmentUserForgetPasswordAddPhoneBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class UserForgetPasswordAddPhone extends Fragment {

    private FragmentUserForgetPasswordAddPhoneBinding binding;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UserForgetPasswordAddPhone() {
        // Required empty public constructor
    }

    public static UserForgetPasswordAddPhone newInstance(String param1, String param2) {
        UserForgetPasswordAddPhone fragment = new UserForgetPasswordAddPhone();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize ViewBinding object
        binding = FragmentUserForgetPasswordAddPhoneBinding.inflate(inflater, container, false);
        binding.btnuseraddPhoneforgetpass.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        } else {
                            sendOTP(binding.etuseraddPhoneforgetpass.getText().toString());
                        }
                    }
                }
        );
        // Return the root view of the binding object
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Release the binding object to free up memory
        binding = null;
    }

    // This method sends an OTP to the given phone number and navigates to the next fragment to verify it
    private void sendOTP(String phoneNumber) {
        // Verify that the phone number matches the pattern for a Pakistani phone number
        if (!phoneNumber.matches("^03\\d{9}$")) {
            // Phone number is not valid for Pakistan
            return;
        }

        // Configure the SMS verification settings
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phoneNumber)  // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity()) // Activity (for callback binding)
                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                        .build();

        // Start the phone number verification process
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}
