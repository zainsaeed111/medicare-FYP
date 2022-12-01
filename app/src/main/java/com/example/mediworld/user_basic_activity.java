package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class user_basic_activity extends AppCompatActivity {
    Button userRegisterbutton,userLoginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_basic);
        getSupportActionBar().hide();
        userRegisterbutton=findViewById(R.id.btnuserRegister);
        userLoginbutton=findViewById(R.id.btnuserlogin);


        userRegisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), user_register_activity.class);
                startActivity(intent);
            }
        });

        userLoginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), user_login_activity.class);
                startActivity(intent);
            }
        });


    }
}