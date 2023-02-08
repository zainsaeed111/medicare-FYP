package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ShopResgister extends AppCompatActivity {
TextView tvloginShopeg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_resgister);
        getSupportActionBar().hide();
        tvloginShopeg=findViewById(R.id.tvloginShopeg);
        tvloginShopeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),shop_login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);


            }
        });



    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(getApplicationContext(),shop_login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);


    }
}