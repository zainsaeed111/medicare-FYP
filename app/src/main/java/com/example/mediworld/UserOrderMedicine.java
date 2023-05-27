package com.example.mediworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mediworld.databinding.FragmentUserOrderMedicineBinding;

public class UserOrderMedicine extends Fragment {


    private FragmentUserOrderMedicineBinding binding;
    public UserOrderMedicine() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserOrderMedicineBinding.inflate(inflater, container, false);
        binding.shopUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(view);

                navController.navigate(R.id.action_userOrderMedicine_to_userOrderFromPrescription);

            }
        });
        binding.shopOnlinebuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

                navController.navigate(R.id.action_userOrderMedicine_to_userOnlinephar);

            }
        });
        return binding.getRoot();
    }
}