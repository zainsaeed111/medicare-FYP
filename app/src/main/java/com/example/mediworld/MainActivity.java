package com.example.mediworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Thread timer;
    private static final String PREF_NAME = "MyPreferences";
    private static final String KEY_VALUE = "myValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        String value = retrieveValue(getApplicationContext());

        timer = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if ("Shop".equals(value)) {
                        Intent intent = new Intent(getApplicationContext(), ShopHost.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                    } else if ("User".equals(value)) {
                        Intent intent = new Intent(getApplicationContext(), MainBasic.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(MainActivity.this, selectuser.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        timer.start();

    }


    private static String retrieveValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_VALUE, null);
    }
}
