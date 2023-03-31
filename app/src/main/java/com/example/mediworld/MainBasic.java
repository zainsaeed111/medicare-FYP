package com.example.mediworld;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainBasic extends AppCompatActivity {
    private final int ID_home = 1;
    private final int ID_chat = 2;
    private final int ID_profile = 3;
    private final int ID_about = 4;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_basic);
        getSupportActionBar().hide();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);


        /*
        bottomNavigationView.setItemIconTintList(null); // corrected method name
*/
//        bottomNavigationView.setItemIconTintList(null);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.showBottomHome);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);












    }
}