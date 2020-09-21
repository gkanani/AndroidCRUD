package com.gaurav.studentdetail;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.RestrictionEntry;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.gaurav.studentdetail.model.BookPojo;
import com.gaurav.studentdetail.retrofit.APIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.http.RealResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiSites extends AppCompatActivity {

    ListView listView;
    BookInterface bookInterface;
    List<BookPojo> list = new ArrayList<BookPojo>();
    Context context = ApiSites.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_sites);

        listView = findViewById(R.id.listViewHeroes);

    }

}