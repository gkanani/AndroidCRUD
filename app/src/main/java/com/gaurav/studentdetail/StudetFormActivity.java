package com.gaurav.studentdetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class StudetFormActivity extends AppCompatActivity {

    ListView lView;
    SQLiteDatabase db;

    IndiviewListAdapter indiviewListAdapter;
    ArrayList<Student> studentArrayList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studet_form);


        SharedPreferences pref = getSharedPreferences("loginSharedPreference", Context.MODE_PRIVATE);
        String loginEmail = pref.getString("SEmail", "");
        Toast.makeText(StudetFormActivity.this, "Login Details -----  : " + loginEmail, Toast.LENGTH_LONG).show();


        lView = findViewById(R.id.lView);

        db = openOrCreateDatabase("studInfo", Context.MODE_PRIVATE, null);
        Cursor c = db.query("student", null, null, null, null, null, null);

        String[] from = {"_id", "sName", "sCity", "sEmail", "sStream"};
        int[] to = {R.id.sId, R.id.sNameItem, R.id.sCityItem, R.id.sEmailItem, R.id.sStream};
        SimpleCursorAdapter myadapter = new SimpleCursorAdapter(StudetFormActivity.this, R.layout.indiview, c, from, to, 0);
        lView.setAdapter(myadapter);

        setValuesInAdapter();

    }

    private List<Student> setValuesInAdapter() {

        listView = findViewById(R.id.lView);
        getDataFromDatabase();
        indiviewListAdapter = new IndiviewListAdapter(StudetFormActivity.this, R.layout.indiview, studentArrayList);
        //attaching adapter to the listview
        listView.setAdapter(indiviewListAdapter);
        return studentArrayList;
    }

    private void getDataFromDatabase() {
        studentArrayList.clear();
        Cursor cursor = db.rawQuery("select _id, sName, sCity, sEmail , sStream from student", null);
        while (cursor.moveToNext()) {
            studentArrayList.add(new Student(cursor.getString(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("sName")), cursor.getString(cursor.getColumnIndex("sCity")), cursor.getString(cursor.getColumnIndex("sEmail")), cursor.getString(cursor.getColumnIndex("sStream"))));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            getDataFromDatabase();
            indiviewListAdapter.notifyDataSetChanged();
        }
    }
}