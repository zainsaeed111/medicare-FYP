package com.example.mediworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class user_login_activity extends AppCompatActivity {

    TextView tvRegisterUselog;
    TextView tvforgetPassword;

    Button userLoginbtn, btnuserLoginGoogle;
    EditText etpassUserLog, etemailUserLog, etusernameUserLog, etuseremailUserLog;
    FirebaseAuth mAuth;
    /* String loginphone, loginpassword;*/
    GoogleSignInClient googleSignInClient;
    private static final String PREF_NAME = "MyPreferences";
    private static final String PREF_USER_KEY = "USER_KEY";
    private static final String USER_KEY = "userKey";
    private static final String KEY_VALUE = "myValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().hide();

        //   btnuserLoginGoogle=findViewById(R.id.btnuserLoginGoogle);
        userLoginbtn = findViewById(R.id.userLoginBtn);
        tvRegisterUselog = findViewById(R.id.tvRegisterUselog);
        etpassUserLog = findViewById(R.id.etpassUserLog);
        etusernameUserLog = findViewById(R.id.etusernameUserLog);
        tvforgetPassword = findViewById(R.id.tvforgetPassword);
        //  etuseremailUserLog= findViewById(R.id.etuseremailUserLog);

        mAuth = FirebaseAuth.getInstance();
        tvforgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserForgetPasswordHost.class);
                startActivity(intent);

            }
        });

        userLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!validateUsername() | !validatePassword()) {
//
//                } else {
                    LoginUser();
//                }
            }


            /*    startActivity(new Intent(getApplicationContext(),custom_bottom_menu.class));*/

        });


        tvRegisterUselog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), user_login_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }

    private boolean validatePassword() {

        String val = etpassUserLog.getText().toString();
        if (val.isEmpty()) {
            etpassUserLog.setError("Password cannot be empty");
            return false;
        } else {
            etpassUserLog.setError(null);
            return true;
        }


    }

    private boolean validateUsername() {

        String val = etusernameUserLog.getText().toString();
        if (val.isEmpty()) {
            etusernameUserLog.setError("Username cannot be empty");
            return false;
        } else {
            etusernameUserLog.setError(null);
            return true;
        }


    }

    private void LoginUser() {
        final String username = etusernameUserLog.getText().toString().trim();
        final String password = etpassUserLog.getText().toString().trim();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("MyUser");

        Query checkUsername = databaseReference.orderByChild("username").equalTo(username);

        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    etusernameUserLog.setError(null);
                    Log.d("check",snapshot.toString());

                    // Check if the password is correct for the user
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String dbPassword = childSnapshot.child("password").getValue(String.class);
                        Log.d("check",dbPassword.toString());
                        if (dbPassword != null && dbPassword.equals(password)) {
                            etpassUserLog.setError(null);
                            String UserKey= childSnapshot.getKey();
                            storeValueUser(getApplicationContext(), "User");
                            Log.d("userkey",UserKey);
                            storeUserKey(getApplicationContext(),UserKey);
                            Log.d("uservalue",UserKey);
                            Intent intent = new Intent(getApplicationContext(), MainBasic.class);
                            startActivity(intent);
                            finish();

                            // Generate a key for the user
                            String key = childSnapshot.getKey();

//                            // Store the key in SharedPreferences
//                            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//                            preferences.edit().putString("userKey", key).apply();

                            // If the password is correct, start the MainBasic activity
                            overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
                            return;
                        }
                    }
                    // Display an error message if the password is incorrect
                } else {
                    // Display an error message if the username does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, user_register_activity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);


    }
    private static void storeValueUser(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_VALUE, value);
        editor.apply();
    }

    private static void storeUserKey(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, value);
        editor.apply();
    }
}



