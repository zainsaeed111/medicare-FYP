package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mediworld.databinding.FragmentUserForgetPasswordAddPhoneBinding;
import com.google.firebase.auth.PhoneAuthProvider;

public class UserForgetPasswordAddPhone extends Fragment {
    private NavController navController;
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
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewforgetpass);

        binding.btnuseraddPhoneforgetpass.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController.navigate(R.id.action_userForgetPasswordAddPhone_to_userForgetPasswordAddOtp);


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

    }

