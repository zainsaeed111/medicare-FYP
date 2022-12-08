package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class user_register_activity extends AppCompatActivity {
   Button userrigisterbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getSupportActionBar().hide();
        userrigisterbtn=findViewById(R.id.userregisterBtn);
      /*  userrigisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dashboard_practice.class);
                startActivity(intent);
            }
        });*/

    }
}