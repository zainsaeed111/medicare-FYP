package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class shop_login extends AppCompatActivity {
 TextView newShoptv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_login);
        newShoptv=findViewById(R.id.newShoptv);
        getSupportActionBar().hide();

        newShoptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ShopResgister.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


    }


    public void onLoginClick(View view) {

        Intent intent = new Intent(getApplicationContext(),ShopResgister.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);


    }
}