package com.example.mediworld;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.example.mediworld.databinding.ActivityUserForgetPasswordHostBinding;

public class UserForgetPasswordHost extends AppCompatActivity {
    private ActivityUserForgetPasswordHostBinding binding;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserForgetPasswordHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.include.tvTitle.setVisibility(View.GONE);

        // Replace drawerImgbtn with backImgbtn
        binding.include.drawerImgbtn.setVisibility(View.GONE);
        binding.include.backImgbtn.setVisibility(View.VISIBLE);
        // Handle back stack on backImgbtn click
        binding.include.backImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
