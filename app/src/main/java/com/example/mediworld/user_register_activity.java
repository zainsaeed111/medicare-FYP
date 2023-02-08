package com.example.mediworld;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
            // Here, we are sure that form is successfully validated. So, do your stuffs now...
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference =firebaseDatabase.getReference("MyUser");
            etusernameUsereg = findViewById(R.id.etusernameUsereg);
            String username = etusernameUsereg.getText().toString();
            String useremail = etemailUsereg.getText().toString();
            String userphone = etphoneUsereg.getText().toString();
            String userpass = etpassUsereg.getText().toString().trim();
            String userconfirmPass = etpassconfirmUsereg.getText().toString().trim();
            UserHelperClass helperClass = new UserHelperClass(username, useremail, userphone,userpass);
            reference.child(username).setValue(helperClass);
            Toast.makeText(this, "Registered Successfulyy", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(user_register_activity.this, user_login_activity.class);
            startActivity(intent);
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

    /*private void registerUser() {
        String username = etusernameUsereg.getText().toString().trim();
        String email = etemailUsereg.getText().toString().trim();
        String password = etpassUsereg.getText().toString().trim();
        String confirmpass= etpassconfirmUsereg.getText().toString().trim();
        String phone = etphoneUsereg.getText().toString().trim();


        if (!username.isEmpty()) {
            etusernameUsereg.setError(null);
            etusernameUsereg.setErrorEnabled(false);

        }
        else
            {etusernameUsereg.setError("Enter Username Please*"); }
        if(!email.isEmpty()){ } else{etemailUsereg.setError("Enter Email Please*"); }
        if(!password.isEmpty()){ } else{ etpassUsereg.setError("Enter Password Please*"); }
        if(!confirmpass.isEmpty()){ } else{etpassconfirmUsereg.setError("Confirm Password Please*"); }
        if(!phone.isEmpty()){ } else{etphoneUsereg.setError("Enter Email Please*"); }

    }*/


}

}
















