package com.gaurav.studentdetail;

import com.gaurav.studentdetail.model.BookPojo;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookInterface {

//    String BASE_URL = "http://192.168.1.186:8080/";

    @GET("book/books")
    Call<List<BookPojo>> getBooksList();

}
