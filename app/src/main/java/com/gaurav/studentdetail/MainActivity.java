package com.gaurav.studentdetail;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;
    Button btnSubmit, btnView, logoutButton;
    EditText etStudName, etStudEmail, etStudCity;
    Spinner streams;
    SQLiteDatabase db;
    String streamName = "";
    SharedPreferences sharedpreferences;
    Toolbar toolbar;
    SharedPreferences sharedPreferences = null;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listners();
//      checkIsLoginOrNot();

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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        sharedPreferences = getSharedPreferences("loginSharedPreference", Context.MODE_PRIVATE);
                        String cacheLoginValue = sharedPreferences.getString("loginEmail", "");
                        Toast.makeText(context, "After login credential is :::::--------- " + cacheLoginValue, Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.remove("loginEmail");
                        editor.clear();
                        editor.apply();
                        Toast.makeText(context, "Before login credential is :::::--------- " + cacheLoginValue, Toast.LENGTH_LONG).show();

                        if (cacheLoginValue != null) {
                            startActivity(new Intent(context, HomepageActivity.class));
                        } else {
                            startActivity(new Intent(context, StudentLogin.class));
                        }
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                            // Do extra stuff with Negative button
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String msg = "";

        switch (item.getItemId()) {
            case R.id.subitem1:
                msg = "subitem 111";
                break;
            case R.id.subitem2:
                msg = "subitem 222";
                break;
            case R.id.subitem3:
                msg = "subitem 333";
                break;
            case R.id.subitem4:
                msg = "subitem 444";
                break;
            case R.id.subitem5:
                msg = "subitem 555";
                break;
            case R.id.subitem6:
                msg = "subitem 666";
                break;
        }

        Toast.makeText(context, msg + " Checked", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }


    /*private void checkIsLoginOrNot() {

        String email = sharedpreferences.getString("SEmail","");
        if(!email.isEmpty()){
        }

    }*/


    private void listners() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFormValid()) {
                    storeDataIntoSQL();
//                  storeDataInSharedReference();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewData();
            }
        });


        streams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position == 0) {
                    streamName = "";
                } else {
                    streamName = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), streamName, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void storeDataInSharedReference() {
        String sharedName = etStudName.getText().toString();
        String sharedEmail = etStudEmail.getText().toString();

        SharedPreferences.Editor pref = getSharedPreferences("FirstPreference", Context.MODE_PRIVATE).edit();
        pref.putString("SName", sharedName);
        pref.putString("SEmail", sharedEmail);
        pref.apply();

        String na_forShared = sharedpreferences.getString("SName", "");
        String em_forShared = sharedpreferences.getString("SEmail", "");

        Toast.makeText(context, "name is  :  " + na_forShared + " email is : " + em_forShared, Toast.LENGTH_LONG).show();

        startActivity(new Intent(context, StudetFormActivity.class));

    }

    private void viewData() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        System.out.println("date is :------ " + dateFormat.format(date));
        Toast.makeText(context, "Date is coming from MainActivity.java :--------- " + dateFormat.format(date), Toast.LENGTH_LONG).show();

        startActivity(new Intent(MainActivity.this, StudetFormActivity.class));
    }


    private void storeDataIntoSQL() {

        db = openOrCreateDatabase("studInfo", Context.MODE_PRIVATE, null);

        boolean checkmail = CheckIsEmailAlreadyInDBorNot(etStudEmail.getText().toString());

        if (checkmail) {
            Toast.makeText(context, "Email Duplicate.........", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put("sName", etStudName.getText().toString());
        cv.put("sCity", etStudCity.getText().toString());
        cv.put("sEmail", etStudEmail.getText().toString());
        cv.put("sStream", streamName);

        long status = db.insert("student", null, cv);

        if (status != -1L) {
            Toast.makeText(context, "Data is Inserted..", Toast.LENGTH_LONG).show();
            etStudEmail.setText("");
            etStudCity.setText("");
            etStudName.setText("");
            streams.setSelection(0);

        } else {
            Toast.makeText(context, "Data is Insertion Failed..", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        btnSubmit = findViewById(R.id.btnSubmit);
//        btnDisplay = findViewById(R.id.btnDisplay);
        btnView = findViewById(R.id.btnView);
        etStudCity = findViewById(R.id.etStudCity);
        etStudEmail = findViewById(R.id.etStudEmail);
        etStudName = findViewById(R.id.etStudName);
        streams = findViewById(R.id.streams);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.YELLOW);
        /*toolbar.setSubtitle("Testing App");
        toolbar.setLogo(R.drawable.search);*/

        sharedpreferences = getSharedPreferences("FirstPreference", Context.MODE_PRIVATE);

        db = openOrCreateDatabase("studInfo", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists student(_id integer primary key autoincrement,sName varchar(50),sCity varchar(50),sEmail varchar(50),sStream varchar(50))");
    }


    public boolean CheckIsEmailAlreadyInDBorNot(String emaail) {

        db = openOrCreateDatabase("studInfo", Context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("select * from student where sEmail=?", new String[]{emaail});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    private boolean isFormValid() {

        if (etStudName.getText().toString().isEmpty()) {
            etStudName.setError("Enter Student Name");
            return false;
        }
        if (etStudCity.getText().toString().isEmpty()) {
            etStudCity.setError("Enter Student City");
            return false;
        }

        if (etStudEmail.getText().toString().isEmpty()) {
            etStudEmail.setError("Enter Email Address");
            return false;
        } else {
            if (etStudEmail.getText().toString().trim().matches(emailPattern)) {
                Toast.makeText(context, "valid email address", Toast.LENGTH_SHORT).show();
            } else {
                etStudEmail.setError("Enter Valid Email Address");
                return false;
            }
        }
        if (streamName.isEmpty()) {
            Toast.makeText(context, "Please Select Stream ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

}