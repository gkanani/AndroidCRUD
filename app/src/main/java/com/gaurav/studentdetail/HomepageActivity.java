package com.gaurav.studentdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.gaurav.studentdetail.model.BookPojo;
import com.gaurav.studentdetail.retrofit.APIUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomepageActivity extends AppCompatActivity {


    Toolbar toolbar;
    Context context = HomepageActivity.this;
    SharedPreferences sharedPreferences = null;
    Button registerButton, loginButton, logoutButton , apiButton;
    ListView listView;
    BookInterface bookInterface;
    List<BookPojo> list = new ArrayList<BookPojo>();
    private static final String API_BASE_URL="http://192.168.1.186:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        init();
        clickListener();

        bookInterface = APIUtils.getBookInterface();

        logoutButton = findViewById(R.id.btnlogout);
        /*logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "welcome to logout", Toast.LENGTH_LONG).show();
                studentLogout();
            }
        });*/
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
        apiButton = findViewById(R.id.apicall);
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

        apiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Pop Up for api call." , Toast.LENGTH_LONG).show();
//                startActivity(new Intent(context, ApiSites.class));

                getBooks();

            }
        });
    }


    private void getBooks() {
        System.out.println("Into get books function...........");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        BookInterface bi = retrofit.create(BookInterface.class);

        Call<List<BookPojo>> call = bi.getBooksList();

        call.enqueue(new Callback<List<BookPojo>>() {
            @Override
            public void onResponse(Call<List<BookPojo>> call, Response<List<BookPojo>> response) {
                Toast.makeText(context, "Success from ON_RESPONSE" , Toast.LENGTH_LONG).show();
                System.out.println("Success........... :):):):):):):):):):):):):)");
                if(response.isSuccessful()){
                    list = response.body();
                    listView.setAdapter(new ArrayAdapter<>(context,R.layout.activity_api_sites,list));
                }
            }

            @Override
            public void onFailure(Call<List<BookPojo>> call, Throwable t) {
                System.out.println("Error............ :(:(:(:(:(:(:(:(:(:(:(:(:(");
                Toast.makeText(context,"Error msg :--- "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}