package com.example.doan1.network;

import com.example.doan1.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("get_top_books.php")
    Call<List<Book>> getTopBooks();

}
