package com.gaurav.studentdetail.retrofit;

import com.gaurav.studentdetail.BookInterface;


public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = "http://192.168.1.186:8080/";

    public static BookInterface getBookInterface(){
        return RetrofitClient.getRetrofit(API_URL).create(BookInterface.class);
    }

}
