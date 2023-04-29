package com.example.mediworld;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainBasic extends AppCompatActivity {
    private final int ID_home = 1;
    private final int ID_chat = 2;
    private final int ID_profile = 3;
    private final int ID_about = 4;
    private NavController navController;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_basic);
        getSupportActionBar().hide();
        toolbar = findViewById(R.id.shopToolbar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Find Nav Host Fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.showBottomHome);
        NavController navController = navHostFragment.getNavController();

        // Set Bottom Navigation and Pass nav controller to handle item clicks and change fragments
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Set drawerLayout
        NavigationView navigationView = findViewById(R.id.navView);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
}
