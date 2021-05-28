package com.example.project1;

import android.Manifest;
import android.app.PendingIntent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.StringTokenizer;

import SessionManagment.SessionManagement;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    EditText sos;
    Button SEND;
    TextView text1, text2, text3, text4, text5, text6, text7, text8;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;
    String string1, string2, string3, string4, string5, string6, string7, string8;
    ImageView img;
//    private DatabaseReference reference;
//    private String userID;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SEND = findViewById(R.id.send);
        sos = findViewById(R.id.name);
        text1 = findViewById(R.id.text_1);
        text2 = findViewById(R.id.text_2);
        text3 = findViewById(R.id.text_3);
        text4 = findViewById(R.id.text_4);
        text5 = findViewById(R.id.text_5);
        text6 = findViewById(R.id.text_6);
        text7 = findViewById(R.id.text_7);
        text8 = findViewById(R.id.text_8);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String latit = preferences.getString("no","");
        String longit = preferences.getString("no1","");
        Log.d("bhim", latit);


//        userID = currentUser.getUid();

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1 = text1.getText().toString();
                sos.setText(string1);
            }
        });


        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string2 = text2.getText().toString();
                sos.setText(string2);

            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string3 = text3.getText().toString();
                sos.setText(string3);
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string4 = text4.getText().toString();
                sos.setText(string4);
            }
        });

        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string5 = text5.getText().toString();
                sos.setText(string5);
            }
        });

        text6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string6 = text6.getText().toString();
                sos.setText(string6);
            }
        });

        text7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string7 = text7.getText().toString();
                sos.setText(string7);
            }
        });

        text8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string8 = text8.getText().toString();
                sos.setText(string8);
            }
        });



        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissioncheck = ContextCompat.checkSelfPermission(Home.this, Manifest.permission.SEND_SMS);
                if (permissioncheck == PackageManager.PERMISSION_GRANTED) {
                    MyMessage();
                } else {
                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.SEND_SMS}, 0);
                }
                String string = sos.getText().toString();
                if (string.equals(string1)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("1");

                } else if (string.equals(string2)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("2");
                } else if (string.equals(string3)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("3");
                } else if (string.equals(string4)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("4");
                } else if (string.equals(string5)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("5");
                } else if (string.equals(string6)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("6");
                } else if (string.equals(string7)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("7");
                } else if (string.equals(string8)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("Users");
                    myRef.child(mAuth.getCurrentUser().getUid()).child("message").setValue("8");
                }

                //Location
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getCurrentLocation();
                }

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        updateNavHeader();

    }

    private void MyMessage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String st = preferences.getString("number","");
        String st1 = preferences.getString("number1","");
        String st2 = preferences.getString("number2","");
        String st3 = preferences.getString("number3","");
        String st4 = preferences.getString("number4","");
        String num[] = {st,st1,st2,st3,st4}; 
        String number = "";
        for (String s : num){
            number = number + s + ";";
        }
        number = number.substring(0,number.length()-1);
        String ourmessage = sos.getText().toString().trim();
        Uri sendSmsTo = Uri.parse("smsto:" + number);
        Intent i = new Intent(android.content.Intent.ACTION_SENDTO,sendSmsTo);
        i.putExtra("sms_body", ourmessage);
        startActivity(i);
     }
     private void moveToLogin() {
        Intent intent = new Intent(Home.this, USER.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure Want To Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Home.super.onBackPressed();
                        Intent intent = new Intent(Home.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_message) {
            Intent intent = new Intent(Home.this, Notification_activity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_emergencynumber) {
            Intent intent = new Intent(Home.this, CallActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.edit_profile){
            Intent intent  = new Intent(Home.this,Activity2.class);
            startActivity(intent);

        }else  if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Home.this, USER.class);
            startActivity(intent);
            Toast.makeText(Home.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
            SessionManagement sessionManagement = new SessionManagement(Home.this);
            sessionManagement.removeSession();

            moveToLogin();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = headerView.findViewById(R.id.nav_username);
        final TextView navUseremail = headerView.findViewById(R.id.nav_user_email);
        ImageView navUserphoto = headerView.findViewById(R.id.nav_user_photo);
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserHelperClass userProfile = snapshot.getValue(UserHelperClass.class);
//                if (userProfile != null) {
//                    String username = userProfile.username;
//                    String useremail = userProfile.email;
//                    navUsername.setText(username);
//                    navUseremail.setText(useremail);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Home.this, "Error! check your network connectivity", Toast.LENGTH_LONG).show();
//            }
//        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MyMessage();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(Home.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult){
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Home.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();

                            FirebaseDatabase mref = FirebaseDatabase.getInstance();
                            final DatabaseReference reference = mref.getReference().child("Users");
                            reference.child(mAuth.getCurrentUser().getUid()).child("latitude").setValue(
                                    String.format("%s", latitude)
                            );
                            double longitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            reference.child(mAuth.getCurrentUser().getUid()).child("Longitude").setValue(
                                    String.format("%s", longitude)
                            );
                        }
                    }

                }, Looper.getMainLooper());



    }
}

