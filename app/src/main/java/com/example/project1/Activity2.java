package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity2 extends AppCompatActivity {
    EditText Username, Email, Password, Age, phone_no,latitude,longitude;
    TextView text1, text2, text3, text4, text5;
    Button edit;
    String gender = null;
    FirebaseAuth fAuth;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Username = findViewById(R.id.edit_1);
        Email = findViewById(R.id.edit_2);
        Password = findViewById(R.id.edit_3);
        Age = findViewById(R.id.edit_4);
        phone_no = findViewById(R.id.edit_5);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        text1 = findViewById(R.id.txt_1);
        text2 = findViewById(R.id.txt_2);
        text3 = findViewById(R.id.txt_3);
        text4 = findViewById(R.id.txt_4);
        text5 = findViewById(R.id.txt_5);
        edit = findViewById(R.id.btn);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();


        reference = FirebaseDatabase.getInstance().getReference("Users");



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass userProfile = snapshot.getValue(UserHelperClass.class);
                if (userProfile != null) {
                    String username = userProfile.username;
                    String useremail = userProfile.email;
                    String userage = userProfile.age;
                    String userpassword = userProfile.password;
                    String userphone = userProfile.phoneNo;
                    String userlatitude = userProfile.latitude;
                    String userlongitude = userProfile.longitude;
                    Username.setText(username);
                    Email.setText(useremail);
                    Age.setText(userage);
                    Password.setText(userpassword);
                    phone_no.setText(userphone);
                    latitude.setText(userlatitude);
                    longitude.setText(userlongitude);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity2.this, "Error! check your network connectivity", Toast.LENGTH_LONG).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase mref = FirebaseDatabase.getInstance();
                final DatabaseReference reference= mref.getReference().child("Users");
                final String username = Username.getText().toString();
                final String password = Password.getText().toString();
                final String email = Email.getText().toString();
                final String age = Age.getText().toString();
                final String phoneNo = phone_no.getText().toString();
                final String lat = latitude.getText().toString();
                final String lon = longitude.getText().toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Activity2.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("no", lat);
                editor.putString("no1", lon);
                editor.apply();

                UserHelperClass helperClass = new UserHelperClass(username,password,email,age,phoneNo,gender,lat,lon);
                reference.child(userID).setValue(helperClass);

                Intent intent = new Intent(Activity2.this,Home.class);
                startActivity(intent);

            }
        });


    }


}







