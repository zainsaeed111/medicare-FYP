package com.example.mediworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_register_activity extends AppCompatActivity implements View.OnClickListener {


    private Button btnregUsereg;
    private EditText etusernameUsereg;
    private EditText etemailUsereg;
    private EditText etphoneUsereg;
    private EditText etpassUsereg;
    private EditText etpassconfirmUsereg;
    private TextView tvloginUsereg;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private AwesomeValidation awesomeValidation;

    private FirebaseAuth mAuth;

    //For RealTime Data base InserData function


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getSupportActionBar().hide();
        tvloginUsereg=findViewById(R.id.tvloginUsereg);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        intView();
        mAuth = FirebaseAuth.getInstance();

tvloginUsereg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(user_register_activity.this, user_login_activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
});


   /* private void RegisterData() {


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("MyUser");
        String username = etusernameUsereg.getText().toString();
        String useremail = etemailUsereg.getText().toString();
        String userphone = etphoneUsereg.getText().toString();
        String userpass = etpassUsereg.getText().toString().trim();
        String userconfirmPass = etpassconfirmUsereg.getText().toString().trim();


        UserHelperClass helperClass = new UserHelperClass(username, useremail, userphone,userpass);
        reference.child(username).setValue(helperClass);

        Toast.makeText(user_register_activity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(user_register_activity.this, user_login_activity.class);
        startActivity(intent);






    }


*/


    }

    private void intView() {

        btnregUsereg = findViewById(R.id.btnregUsereg);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnregUsereg = findViewById(R.id.btnregUsereg);
        etusernameUsereg = findViewById(R.id.etusernameUsereg);
        etemailUsereg = findViewById(R.id.etemailUsereg);
        etphoneUsereg = findViewById(R.id.etphoneUsereg);
        etpassUsereg = findViewById(R.id.etpassUsereg);
        etpassconfirmUsereg = findViewById(R.id.etpassconfirmUsereg);
        tvloginUsereg = findViewById(R.id.tvloginUsereg);
        btnregUsereg.setOnClickListener(this);
        addValidationToViews();

    }

    private void addValidationToViews() {


        awesomeValidation.addValidation(this, R.id.etusernameUsereg, RegexTemplate.NOT_EMPTY, R.string.input_error_name);
        awesomeValidation.addValidation(this, R.id.etemailUsereg, Patterns.EMAIL_ADDRESS, R.string.input_error_email);
        awesomeValidation.addValidation(this, R.id.etemailUsereg, R.id.etemailUsereg, R.string.input_error_email);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.etpassUsereg, regexPassword, R.string.input_error_password);
        awesomeValidation.addValidation(this, R.id.etpassconfirmUsereg, R.id.etpassUsereg, R.string.input_comifrm_pass_invalid);
        awesomeValidation.addValidation(this, R.id.etpassconfirmUsereg, regexPassword, R.string.input_comifrm_pass_invalid);
        awesomeValidation.addValidation(this, R.id.etphoneUsereg, "^[+]?[0-9]{10,13}$", R.string.input_error_phone);




    }


    private void submitForm() {
        // Validate the form...
        if (awesomeValidation.validate()) {
            // Here, we are sure that the form is successfully validated. So, proceed with registration.
            String username = etusernameUsereg.getText().toString();
            String useremail = etemailUsereg.getText().toString();
            String userphone = etphoneUsereg.getText().toString();
            String userpass = etpassUsereg.getText().toString().trim();
            String userconfirmPass = etpassconfirmUsereg.getText().toString().trim();

            if (!userpass.equals(userconfirmPass)) {
                // Password and confirm password don't match
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
                return;
            }

            // Create a Firebase instance and reference
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference reference = firebaseDatabase.getReference("MyUser");

            // Generate a unique key for the user
            String uniqueKey = reference.push().getKey();

            // Create a UserHelperClass instance with the user data
            UserHelperClass helperClass = new UserHelperClass(username, useremail, userpass, userphone);

            // Store the user data in Firebase
            reference.child(uniqueKey).setValue(helperClass)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data is successfully inserted into Firebase
                            Toast.makeText(user_register_activity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(user_register_activity.this, user_login_activity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to insert data into Firebase
                            Toast.makeText(user_register_activity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, user_login_activity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnregUsereg:
                submitForm();
                break;

        }





}

}




