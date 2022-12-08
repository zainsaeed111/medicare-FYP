package com.example.mediworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
   Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        timer = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(5000);
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(MainActivity.this, selectuser.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
    }
