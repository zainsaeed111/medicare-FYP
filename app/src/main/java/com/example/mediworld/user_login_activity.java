package com.example.mediworld;

import android.content.Intent;
import android.os.Bundle;
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

    Button userLoginbtn,btnuserLoginGoogle;
    EditText etpassUserLog, etemailUserLog,etusernameUserLog,   etuseremailUserLog;
    FirebaseAuth mAuth;
   /* String loginphone, loginpassword;*/
    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().hide();

     //   btnuserLoginGoogle=findViewById(R.id.btnuserLoginGoogle);
        userLoginbtn = findViewById(R.id.userLoginBtn);
        tvRegisterUselog = findViewById(R.id.tvRegisterUselog);
        etpassUserLog = findViewById(R.id.etpassUserLog);
       etusernameUserLog= findViewById(R.id.etusernameUserLog);
        tvforgetPassword=findViewById(R.id.tvforgetPassword);
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

                if (!validateUsername() | !validatePassword()){

                } else {
                    LoginUser();
                }
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
        if (val.isEmpty()){
            etpassUserLog.setError("Password cannot be empty");
            return false;
        } else {
            etpassUserLog.setError(null);
            return true;
        }




    }

    private boolean validateUsername() {

        String val = etusernameUserLog.getText().toString();
        if (val.isEmpty()){
            etusernameUserLog.setError("Username cannot be empty");
            return false;
        } else {
            etusernameUserLog.setError(null);
            return true;
        }


    }
    private void LoginUser() {


     final    String username = etusernameUserLog.getText().toString();
     final    String password = etpassUserLog.getText().toString();


     FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
     DatabaseReference databaseReference = firebaseDatabase.getReference("MyUser");
     Query check_username= databaseReference.orderByChild("username").equalTo(username);

        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(snapshot.exists()){
                 etusernameUserLog.setError(null);

                //Check password
                 String check_pass = snapshot.child(username).child("confirmpassword").getValue(String.class);
                 if(check_pass.equals(password)){
                     etpassUserLog.setError(null); Intent intent = new Intent(getApplicationContext(), MainBasic.class);
                     startActivity(intent);
                     finish();
                     overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);}

                 else { etpassUserLog.setError("Inavlid Password");}
             }else{

                 etusernameUserLog.setError("Inavlid Username");
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });

    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, user_register_activity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);


    }


}



