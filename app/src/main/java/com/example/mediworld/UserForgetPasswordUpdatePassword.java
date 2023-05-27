package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mediworld.databinding.FragmentUserForgetPasswordUpdatePasswordBinding;

public class UserForgetPasswordUpdatePassword extends Fragment {

    private FragmentUserForgetPasswordUpdatePasswordBinding binding;
    private NavController navController;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserForgetPasswordUpdatePasswordBinding.inflate(inflater, container, false);

        // Get the NavController
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerViewforgetpass);

        // Navigate to the next fragment
        binding.btnuserconfirmPassforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_userForgetPasswordUpdatePassword_to_userForgetPasswordDone);
            }
        });
        return binding.getRoot();
    }
    }