package com.example.doan1.network;

import com.example.doan1.models.Book;
import com.example.doan1.models.BookResponse;
import com.example.doan1.models.GenreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("get_top_books.php")
    Call<List<Book>> getTopBooks();

    @GET("get_genres.php")
    Call<GenreResponse> getGenres();

    @GET("get_books_by_genre.php")
    Call<BookResponse> getBooksByGenre(@Query("genre_id") int genreId);



}
