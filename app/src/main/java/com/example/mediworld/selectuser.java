package com.example.mediworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class selectuser extends AppCompatActivity {
    //ImageView iconbtnuser, iconbtnshop;
    private RelativeLayout beAUserRelativeLayout;
    private RelativeLayout beAVendorRelativeLayout;
    private Button contiuneButton;
    private RelativeLayout inActiveButton;
    private Boolean userType = null;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_selectuser);

        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        // TextView introScreenDiaLogTextHeading = view.findViewById(R.id.heading);
        String text = "choose one";
        Spannable spannable = new SpannableString(text);

        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.intro_screen_dialog_text)), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_color)), 6, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //  introScreenDiaLogTextHeading.setText(spannable);

        beAUserRelativeLayout = findViewById(R.id.select_user_type_btn);
        beAVendorRelativeLayout = findViewById(R.id.select_vendor_type_btn);
        inActiveButton = findViewById(R.id.inactive_contiune_btn);
        contiuneButton = findViewById(R.id.main_screen_signin_button);

        beAUserRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectUserType();
            }
        });

        beAVendorRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVendorType();
            }
        });

        contiuneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = sharedPreferences.getString("userType", "");
                if (userType.equals("user")) {
                    // start User activity
                    Intent intent = new Intent(selectuser.this, user_basic_activity.class);
                    startActivity(intent);
                } else if (userType.equals("vendor")) {
                    // start Vendor activity
                    Intent intent = new Intent(selectuser.this, shop_basic.class);
                    startActivity(intent);
                }
            }
        });


    }




    private void selectVendorType() {
        userType = true;
        beAUserRelativeLayout.setBackground(getResources().getDrawable(R.drawable.select_user_type_btn_background));
        beAVendorRelativeLayout.setBackground(getResources().getDrawable(R.drawable.select_type_selected_background));
        contiuneButton.setBackground(getResources().getDrawable(R.drawable.contiune_select_type_btn));
        contiuneButton.setTextColor(getResources().getColor(R.color.white));
        inActiveButton.setVisibility(View.GONE);
        contiuneButton.setVisibility(View.VISIBLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userType", "vendor");
        editor.apply(); // for vendor type
    }

    private void selectUserType() {
        userType = false;
        beAUserRelativeLayout.setBackground(getResources().getDrawable(R.drawable.select_type_selected_background));
        beAVendorRelativeLayout.setBackground(getResources().getDrawable(R.drawable.select_user_type_btn_background));
        contiuneButton.setBackground(getResources().getDrawable(R.drawable.contiune_select_type_btn));
        contiuneButton.setTextColor(getResources().getColor(R.color.white));
        inActiveButton.setVisibility(View.GONE);
        contiuneButton.setVisibility(View.VISIBLE);
        sharedPreferences.edit().putString("userType", "user").apply();
    }
}
