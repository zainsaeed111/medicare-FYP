package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mediworld.databinding.FragmentUserForgetPasswordAddOtpBinding;


public class UserForgetPasswordAddOtp extends Fragment {
    private FragmentUserForgetPasswordAddOtpBinding binding;
    private NavController navController;

    public UserForgetPasswordAddOtp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize ViewBinding object
        binding = FragmentUserForgetPasswordAddOtpBinding.inflate(inflater, container, false);

        // Get the NavController
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewforgetpass);

        // Navigate to the next fragment
        binding.btnuserverifyCodeforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_userForgetPasswordAddOtp_to_userForgetPasswordUpdatePassword);
            }
        });

        // Return the root view of the binding object
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Release the binding object to free up memory
        binding = null;
    }
}