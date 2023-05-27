package com.example.mediworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mediworld.databinding.ActivityMainBasicBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainBasic extends AppCompatActivity {
    private final int ID_home = 1;
    private final int ID_chat = 2;
    private final int ID_profile = 3;
    private final int ID_about = 4;
    private NavController navController;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ImageView backImgbtn;

    private ActivityMainBasicBinding binding;
    private static final String PREF_NAME = "MyPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        toolbar = findViewById(R.id.shopToolbar);
//       // setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Find Nav Host Fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.showBottomHome);
        NavController navController = navHostFragment.getNavController();

        // Set Bottom Navigation and Pass nav controller to handle item clicks and change fragments
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        drawerLayout=findViewById(R.id.drawerLayout);
        // Set drawerLayout
        NavigationView navigationView = findViewById(R.id.navView);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationView navView = findViewById(R.id.navView);

// Set custom toolbar icon and title dynamically
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            TextView toolbarTitle = findViewById(R.id.tvTitle);
            ImageView drawerImgbtn = findViewById(R.id.drawerImgbtn);
            backImgbtn = findViewById(R.id.backImgbtn);


            switch (destination.getId()) {
                case R.id.userHome:
                    toolbarTitle.setText("Home");
                    break;

                case R.id.userInbox:
                    toolbarTitle.setText("Inbox");

                    break;

                case R.id.userprofile:
                    toolbarTitle.setText("Profile");
                    break;

                //DrawerFragments

                case R.id.userAbout:
                    toolbarTitle.setText("About");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.userPrivacyPolicy:
                    toolbarTitle.setText("Privacy");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.usercontactUs:
                    toolbarTitle.setText("Contact Us");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;

                //After Home
                case R.id.userOrderMedicine:
                    toolbarTitle.setText("Order");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.userOrderFromPrescription:
                    toolbarTitle.setText("Upload Prescription");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.userOnlinephar:
                    toolbarTitle.setText("Pharmacies");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.viewPharmciesItems:
                    toolbarTitle.setText("Pharmacy Products");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.userEmgno:
                    toolbarTitle.setText("Emergency Numbers");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.userAIMedicalAssitant:
                    toolbarTitle.setText("Medical Assistant");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                case R.id.userNearbyHospitals:
                    toolbarTitle.setText("NearbyHospitals");
                    backImgbtn.setVisibility(View.VISIBLE);
                    drawerImgbtn.setVisibility(View.GONE);
                    break;
                default:
                    toolbarTitle.setText("MediWorld");
                    backImgbtn.setVisibility(View.GONE);
                    drawerImgbtn.setVisibility(View.VISIBLE);


                    break;
            }


            //After home fragments in host activity coming back it should be show default home toolabra drawerImgbtn gamberg icon
            if (controller.getGraph().getStartDestination() == destination.getId() ||
                    destination.getId() == R.id.userprofile ||
                    destination.getId() == R.id.userInbox) {
                backImgbtn.setVisibility(View.GONE);
                drawerImgbtn.setVisibility(View.VISIBLE);
            } else {
                backImgbtn.setVisibility(View.VISIBLE);
                drawerImgbtn.setVisibility(View.GONE);
            }


        });



       binding.include.drawerImgbtn.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
                // Navigate up in the NavHostFragment
                navController.navigateUp();
            }
        });
        //Toolbar Backarrow Code Handling
        backImgbtn.setOnClickListener(v -> {
            navController.navigateUp();
        });






        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.userLogout) {
                    // Remove the shared preference value
                    removeValue(getApplicationContext());
                    // Add code here to perform any other necessary logout actions
                    // Example: Start a new activity or clear the current activity stack
                    Intent intent = new Intent(MainBasic.this, selectuser.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                } else {
                    // Navigate to the selected fragment using the NavController
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.basic_nav, true)
                            .build();
                    navController.navigate(itemId, null, navOptions);

                    // Close the drawer after item selection
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true;
                }
            }
        });
    }
    public static void removeValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
