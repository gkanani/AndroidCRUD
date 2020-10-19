package com.gaurav.studentdetail;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import retrofit2.http.PATCH;

public class ApiSites extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_sites);

        listView = findViewById(R.id.listViewHeroes);

    }

}