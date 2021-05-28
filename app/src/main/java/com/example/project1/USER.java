package com.example.project1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import SessionManagment.SessionManagement;

public class USER extends AppCompatActivity {
    private static final int RC_SIGN_IN = 101;
    EditText txt_email, txt_password;
    String email, password;
    Button login, btnGoogle;
    TextView textview1, textView2;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    RadioButton remember;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent  = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }

        checkSession();
    }

    private void checkSession() {
        //check if user is logged in
        //if user is logged in --> move to mainActivity

        SessionManagement sessionManagement = new SessionManagement(USER.this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            //user id logged in and so move to mainActivity
            moveToMainActivity();
        } else {
            //do nothing
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(USER.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_s_e_r);

        txt_email = findViewById(R.id.email);
        txt_password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        textview1 = findViewById(R.id.text);
        progressBar = findViewById(R.id.progressbar);
        textView2 = findViewById(R.id.text1);
        mAuth = FirebaseAuth.getInstance();
        btnGoogle = findViewById(R.id.btn_google);
        remember =  findViewById(R.id.remember_me);
        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox =  preferences.getString("remember", "");
        if (checkbox.equals("true")){
            String st = preferences.getString("no","");
            txt_email.setText(st);
            String st1 = preferences.getString("no1","");
            txt_password.setText(st1);
        }else if(checkbox.equals("false")){
            Toast.makeText(this,"Please Sign In.",Toast.LENGTH_SHORT).show();
        }
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    String string = txt_email.getText().toString();
                    String string1 = txt_password.getText().toString();
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.putString("no",string);
                    editor.putString("no1",string1);
                    editor.apply();
                    Toast.makeText(USER.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(USER.this, "Checked", Toast.LENGTH_SHORT).show();
                }
            }
        });


    createRequest();






        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txt_email.getText().toString().trim();
                password = txt_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    txt_email.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    txt_password.setError("Password is Required.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //authentication Of the user

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(USER.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(USER.this, "Logged in SuccessFully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        } else {
                            Toast.makeText(USER.this, " Login Failed" , Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
//                SessionManagement sessionManagement = new SessionManagement(USER.this);
//                sessionManagement.saveSession(txt_email);
//                moveToMainActivity();

            }
        });



        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email to Received Recent Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(USER.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(USER.this, "Error ! Reset Link is not Sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });


        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriatel
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent  = new Intent(getApplicationContext(),Home.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(USER.this, "Sorry auth failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


}


