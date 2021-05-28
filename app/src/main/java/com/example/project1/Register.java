package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText Username,Email,Password,Age,PhoneNo,latitude,longitude;
    Button Register;
    TextView textView;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    RadioButton radioGenderMale,radioGenderFemale;
    String gender = "";
    FirebaseUser user;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Age = findViewById(R.id.age);
        Register = findViewById(R.id.register);
        textView = findViewById(R.id.textview);
        PhoneNo = findViewById(R.id.phoneNo);
        radioGenderMale = findViewById(R.id.radio_male);
        radioGenderFemale = findViewById(R.id.radio_female);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        FirebaseDatabase mref = FirebaseDatabase.getInstance();





        Register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                FirebaseDatabase mref = FirebaseDatabase.getInstance();
                final DatabaseReference reference= mref.getReference().child("Users");
                final String username = Username.getText().toString();
                final String password = Password.getText().toString();
                final String email = Email.getText().toString();
                final String age = Age.getText().toString();
                final String phoneNo = PhoneNo.getText().toString();
                final String lat = latitude.getText().toString();
                final String lon = longitude.getText().toString();




                if(radioGenderMale.isChecked()){
                    gender="Male";
                }
                if(radioGenderFemale.isChecked()){
                    gender="Female";
                }

                if(TextUtils.isEmpty(email)) {
                     Email.setError("Email is Required.");
                     return;
                }
                if(TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //Register User in FireBase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserHelperClass helperClass = new UserHelperClass(username,password,email,age,phoneNo,gender,lat,lon);
                            reference.child(fAuth.getCurrentUser().getUid()).setValue(helperClass);

                            Toast.makeText(Register.this, "Registered SuccessFully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this,USER.class);
                            startActivity(intent);
                                    }
                        else {
                            Toast.makeText(Register.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(Register.this,USER.class);
              startActivity(intent);
            }
        });


    }
}