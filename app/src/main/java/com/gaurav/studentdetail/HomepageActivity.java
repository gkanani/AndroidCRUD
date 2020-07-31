package com.gaurav.studentdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class HomepageActivity extends AppCompatActivity {

    Toolbar toolbar;
    Context context;
    SharedPreferences sharedPreferences = null;
    Button registerButton, loginButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        init();
        clickListener();

        logoutButton = findViewById(R.id.btnlogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "welcome to logout", Toast.LENGTH_LONG).show();
                studentLogout();
            }
        });
    }

    private void studentLogout() {

        sharedPreferences = getSharedPreferences("loginSharedPreference", Context.MODE_PRIVATE);
        String cacheLoginValue = sharedPreferences.getString("loginEmail", "");
        Toast.makeText(context, "After login credential is :::::--------- " + cacheLoginValue, Toast.LENGTH_LONG).show();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("loginEmail");
        editor.clear();
        editor.apply();
        Toast.makeText(context, "Befor login credential is :::::--------- " + cacheLoginValue, Toast.LENGTH_LONG).show();


        if (cacheLoginValue == "") {
            startActivity(new Intent(context, StudentLogin.class));
        } else {
            startActivity(new Intent(context, MainActivity.class));
        }

    }

    private void init() {
        context = HomepageActivity.this;
        registerButton = findViewById(R.id.register);
        loginButton = findViewById(R.id.login);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void clickListener() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, StudentLogin.class));
            }
        });
    }


}