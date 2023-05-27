package com.example.mediworld;

import static com.example.mediworld.MainBasic.removeValue;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class ShopHost extends AppCompatActivity {
    private NavController navController;
    private DrawerLayout drawerLayout;
    private com.example.mediworld.databinding.ActivityShopHostBinding binding;
    private ImageView backImgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.mediworld.databinding.ActivityShopHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.showShop);
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavShop);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        drawerLayout = findViewById(R.id.drawerLayoutshop);
        NavigationView navigationView = findViewById(R.id.navViewshop);
        NavigationUI.setupWithNavController(navigationView, navController);

        ImageView drawerImgbtn = findViewById(R.id.drawerImgbtn);
        backImgbtn = findViewById(R.id.backImgbtn);

        drawerImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.userLogout) {
                    // Handle logout action
                    logoutUser();
                    return true;
                } else {
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.basic_nav, true)
                            .build();
                    navController.navigate(itemId, null, navOptions);
                    drawerLayout.closeDrawer(navigationView);
                    return true;
                }
            }
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            TextView toolbarTitle = findViewById(R.id.tvTitle);

            switch (destination.getId()) {
                case R.id.bnhomeShop:
                    toolbarTitle.setText("Home");
                    break;
                case R.id.Inbox:
                    toolbarTitle.setText("Inbox");
                    break;
                case R.id.bnmyShop:
                    toolbarTitle.setText("My Shop");
                    break;
                // Add more cases for other destinations

                default:
                    toolbarTitle.setText("Shop");
                    break;
            }

            if (destination.getId() == R.id.bnhomeShop ||
                    destination.getId() == R.id.bnmyShop ||
                    destination.getId() == R.id.Inbox) {
                backImgbtn.setVisibility(View.GONE);
                drawerImgbtn.setVisibility(View.VISIBLE);
            } else {
                backImgbtn.setVisibility(View.VISIBLE);
                drawerImgbtn.setVisibility(View.GONE);
            }
        });

        ImageView backImgbtn = findViewById(R.id.backImgbtn);
        backImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
    }

    private void logoutUser() {
        // Remove the shared preference value
        removeValue(getApplicationContext());
        // Add code here to perform any other necessary logout actions
        // Example: Start a new activity or clear the current activity stack
        Intent intent = new Intent(ShopHost.this, selectuser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}