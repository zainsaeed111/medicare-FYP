package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class selectuser extends AppCompatActivity {
    ImageView iconbtnuser, iconbtnshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_selectuser);
        iconbtnuser = findViewById(R.id.iconbtn_user);
        iconbtnshop = findViewById(R.id.icobtn_shop);
        iconbtnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), user_basic_activity.class);
                startActivity(intent);
            }
        });

        iconbtnshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),shop_basic.class);
                startActivity(intent);
            }
        });




    }








}