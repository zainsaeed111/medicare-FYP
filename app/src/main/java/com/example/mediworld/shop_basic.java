package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class shop_basic extends AppCompatActivity {
    Button shopRegisterbutton;
    Button btnshoplogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_basic);
        getSupportActionBar().hide();
        shopRegisterbutton=findViewById(R.id.btnshopRegister);
        btnshoplogin=findViewById(R.id.btnshoplogin);
        btnshoplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), shop_login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });
        shopRegisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopResgister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
}