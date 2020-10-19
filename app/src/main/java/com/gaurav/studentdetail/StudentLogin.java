package com.gaurav.studentdetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentLogin extends AppCompatActivity {

    Context context = StudentLogin.this;
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView attemptsText, tvAttempt;
    int counter = 3;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences = null;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etEmail = findViewById(R.id.etemailaddress);
        etPassword = findViewById(R.id.etpassword);
        attemptsText = findViewById(R.id.attempsCount);
        attemptsText.setVisibility(View.GONE);
        tvAttempt = findViewById(R.id.attemps);
        tvAttempt.setVisibility(View.GONE);

        btnLogin = findViewById(R.id.btnlogin);

        /*SharedPreferences pref = getSharedPreferences("FirstPreference", Context.MODE_PRIVATE);
        String loginName = pref.getString("SName", "");
        String loginEmail = pref.getString("SEmail", "");
        Toast.makeText(context, "login ------ s1 is  :  " + loginName + " s3 is : " + loginEmail, Toast.LENGTH_LONG).show();*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFormValidation();
                studentLogin(etEmail.getText().toString());
            }
        });
    }


    private void studentLogin(String loginEmail) {

        db = openOrCreateDatabase("studInfo", Context.MODE_PRIVATE, null);
        Cursor mCursor = db.rawQuery("select * from student where sEmail=?", new String[]{loginEmail});

        sharedPreferences = getSharedPreferences("loginSharedPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loginEmail", loginEmail);
        editor.apply();
        Toast.makeText(context, "login ------" + loginEmail, Toast.LENGTH_LONG).show();

        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                startActivity(new Intent(context, MainActivity.class));

            } else {
                Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                attemptsText.setVisibility(View.VISIBLE);
                attemptsText.setBackgroundColor(Color.RED);
                tvAttempt.setVisibility(View.VISIBLE);
                counter--;
                attemptsText.setText(Integer.toString(counter));
                attemptsText.setTextColor(Color.BLACK);

                if (counter == 0) {
                    btnLogin.setBackgroundColor(Color.GRAY);
                    btnLogin.setEnabled(false);
                    attemptsText.setText("Login Locked For 3 Sec.");
                    new CountDownTimer(3000, 10) { //Set Timer for 3 seconds
                        public void onTick(long millisUntilFinished) {

                            Toast.makeText(context, "Clicked ON onTick.... ", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFinish() {
                            btnLogin.setBackgroundColor(Color.BLACK);
                            attemptsText.setText("");
                            btnLogin.setEnabled(true);
                            counter = 3;
                        }
                    }.start();
                }
            }
        }
    }

    private boolean loginFormValidation() {

        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Enter Your Email");
            return false;
        } else {
            if (etEmail.getText().toString().trim().matches(EMAIL_PATTERN)) {
                Toast.makeText(context, "valid email address", Toast.LENGTH_SHORT).show();
            } else {
                etEmail.setError("Enter Valid Email Address");
                return false;
            }
        }
        return true;
    }

}