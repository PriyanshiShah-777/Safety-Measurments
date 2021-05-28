package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import java.util.ArrayList;

import SessionManagment.SessionManagement;

public class CallActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Button ADD;
    CountryCodePicker ccp,ccp1,ccp2,ccp3,ccp4;
    EditText phone_txt,phone_txt1,phone_txt2,phone_txt3,phone_txt4;
    String string;
    String string1;
    String string2;
    String string3;
    String string4;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
   FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_call);

        ADD = findViewById(R.id.add);
        ccp = findViewById(R.id.ccp);
        ccp1 = findViewById(R.id.ccp1);
        ccp2 = findViewById(R.id.ccp2);
        ccp3 = findViewById(R.id.ccp3);
        ccp4 = findViewById(R.id.ccp4);
        phone_txt = findViewById(R.id.phone_no);
        phone_txt1 = findViewById(R.id.phone_no_1);
        phone_txt2 = findViewById(R.id.phone_no_2);
        phone_txt3 = findViewById(R.id.phone_no_3);
        phone_txt4 = findViewById(R.id.phone_no_4);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String st = preferences.getString("number", "");
        phone_txt.setText(st);
        String st1 = preferences.getString("number1", "");
        phone_txt1.setText(st1);
        String st2 = preferences.getString("number2", "");
        phone_txt2.setText(st2);
        String st3 = preferences.getString("number3", "");
        phone_txt3.setText(st3);
        String st4 = preferences.getString("number4", "");
        phone_txt4.setText(st4);

        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string = phone_txt.getText().toString();
                string1 = phone_txt1.getText().toString();
                string2 = phone_txt2.getText().toString();
                string3 = phone_txt3.getText().toString();
                string4 = phone_txt4.getText().toString();


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CallActivity.this);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("number", string);
                editor.putString("number1", string1);
                editor.putString("number2", string2);
                editor.putString("number3", string3);
                editor.putString("number4", string4);
                editor.apply();
            }

        });



    }
    private void moveToLogin() {
        Intent intent = new Intent(CallActivity.this, USER.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_message) {
            Intent intent = new Intent(CallActivity.this, Notification_activity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_emergencynumber) {
            Intent intent = new Intent(CallActivity.this, CallActivity.class);
            startActivity(intent);
            finish();
            return true;

        }
        else if (id == R.id.edit_profile){
            Intent intent  = new Intent(CallActivity.this,Activity2.class);
            startActivity(intent);
            finish();
            return true;

        }else  if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(CallActivity.this, USER.class);
            startActivity(intent);
            Toast.makeText(CallActivity.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
            SessionManagement sessionManagement = new SessionManagement(CallActivity.this);
            sessionManagement.removeSession();

            moveToLogin();
            finish();
            return true;

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






}





