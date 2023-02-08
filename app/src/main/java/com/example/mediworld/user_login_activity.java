package com.example.mediworld;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class user_login_activity extends AppCompatActivity {

    TextView tvRegisterUselog;
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
      //  etuseremailUserLog= findViewById(R.id.etuseremailUserLog);


        mAuth = FirebaseAuth.getInstance();
















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

   /* private void checkUser() {



        String username = etusernameUserLog.getText().toString();
        String Pass = etpassUserLog.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MyUser");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);


        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    etusernameUserLog.setError(null);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);

                    if (passwordFromDB.equals(Pass)){
                        etusernameUserLog.setError(null);

                        //Pass the data using intent

                        String nameFromDB = snapshot.child(username).child("username").getValue(String.class);
                        String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(username).child("phone").getValue(String.class);
                        String PassFromDB = snapshot.child(username).child("password").getValue(String.class);

                        Intent intent = new Intent(user_login_activity.this, custom_bottom_menu.class);

                        intent.putExtra("username", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        etpassUserLog.setError("Invalid Credentials");
                        etpassUserLog.requestFocus();
                    }
                } else {
                    etusernameUserLog.setError("User does not exist");
                    etusernameUserLog.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });*/



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
                     etpassUserLog.setError(null); Intent intent = new Intent(getApplicationContext(), custom_bottom_menu.class);
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




/*
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Pass)) {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Pass.length() < 8) {
            Toast.makeText(this, "Password be greater than 6 characters", Toast.LENGTH_SHORT).show();
            return;

        }

        else{



        }
        */

        /*mAuth.signInWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            startActivity(new Intent(user_login_activity.this,MainActivity.class));
                            Toast.makeText(user_login_activity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(user_login_activity.this, "Login error Check Pass or Email", Toast.LENGTH_SHORT).show();

                        }

                    }
                });*/


    }





   /* private boolean validatePassword() {
        loginpassword = etpassUserLog.getText().toString().trim();
        if (TextUtils.isEmpty(loginpassword)) {
            Toast.makeText(user_login_activity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhone() {
        loginphone = etphoneUserLog.getText().toString().trim();
        if (TextUtils.isEmpty(loginphone)) {
            Toast.makeText(user_login_activity.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginphone).matches()) {
            Toast.makeText(user_login_activity.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }*/


    public void onLoginClick(View view) {
        startActivity(new Intent(this, user_register_activity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);


    }


}



