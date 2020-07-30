package com.gaurav.studentdetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText etStudName, etStudEmail, etStudCity;
    Spinner sStudStream = null;
    Context context = UpdateActivity.this;
    SQLiteDatabase db;
    Cursor cursor = null;
    String streamName = "";
    Button btnSubmit;
    String updateFormId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnSubmit = findViewById(R.id.updatebutton);

        updateFormId = getIntent().getStringExtra("ID");

        db = openOrCreateDatabase("studInfo", Context.MODE_PRIVATE, null);
        cursor = db.rawQuery("select * from student where _id=?", new String[]{updateFormId});
        cursor.moveToFirst();

        Log.i("database value by id", "database value by id for update is ---------- " + cursor.getString(cursor.getColumnIndex("sName")));

        int uId = cursor.getInt(cursor.getColumnIndex("_id"));
        String uName = cursor.getString(cursor.getColumnIndex("sName"));
        String uCity = cursor.getString(cursor.getColumnIndex("sCity"));
        String uEmail = cursor.getString(cursor.getColumnIndex("sEmail"));
        String uStream = cursor.getString(cursor.getColumnIndex("sStream"));


        String[] foo_array = context.getResources().getStringArray(R.array.streams);

        sStudStream = findViewById(R.id.updateStream);

        for (int i = 0; i < foo_array.length; i++) {
            if (foo_array[i].equals(uStream)) {
                sStudStream.setSelection(i);
            }
        }

        Log.i("Name : ", "by value -------------------" + uName);
        Log.i("City : ", "by value -------------------" + uCity);
        Log.i("Email : ", "by value ---------------------" + uEmail);
        Log.i("Stream : ", "by value ---------------------" + uStream);

        etStudName = findViewById(R.id.updateSName);
        etStudCity = findViewById(R.id.updateSCity);
        etStudEmail = findViewById(R.id.updateSEmail);

        etStudName.setText(uName);
        etStudCity.setText(uCity);
        etStudEmail.setText(uEmail);

        updateListners();
    }


    private void updateListners() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdateFormValidation()) {
                    updateDataInSql();
                }
            }
        });


        sStudStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    private void updateDataInSql() {

        ContentValues cv = new ContentValues();
        cv.put("sName", etStudName.getText().toString());
        cv.put("sCity", etStudCity.getText().toString());
        cv.put("sEmail", etStudEmail.getText().toString());
        cv.put("sStream", streamName);

        long status = db.update("student", cv, "_id=?", new String[]{updateFormId});

        if (status != -1L) {
            Toast.makeText(context, "Data is Updated....", Toast.LENGTH_LONG).show();
            etStudEmail.setText("");
            etStudCity.setText("");
            etStudName.setText("");
            sStudStream.setSelection(0);

            Intent data = new Intent();
            setResult(RESULT_OK, data);

            finish();

        } else {
            Toast.makeText(context, "Data is Not Updated....", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isUpdateFormValidation() {

        if (etStudName.getText().toString().isEmpty()) {
            etStudName.setError("Enter Student Name");
            return false;
        }
        if (etStudCity.getText().toString().isEmpty()) {
            etStudCity.setError("Enter Student City");
            return false;
        }
        if (etStudEmail.getText().toString().isEmpty()) {
            etStudEmail.setError("Enter Student Email");
            return false;
        }
        if (streamName.isEmpty()) {
            Toast.makeText(context, "Please Select Stream ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

}