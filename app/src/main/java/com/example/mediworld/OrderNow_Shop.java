package com.example.mediworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderNow_Shop extends AppCompatActivity {
    ImageView shopBackBtn;
    Button shopUploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_now_shop);
        getSupportActionBar().hide();
        shopBackBtn=findViewById(R.id.shopBackBtn);
        shopUploadImage=findViewById(R.id.shopUploadImg);
        shopUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserOrderFromPrescription.class);
                startActivity(intent);
            }
        });

        shopBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderNow_Shop.this,custom_bottom_menu.class);
                startActivity(intent);

            }
        });


    }
}