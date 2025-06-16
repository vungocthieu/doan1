package com.example.doan1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.R;
import com.example.doan1.TruyenDetailActivity;
import com.example.doan1.adapters.BookVerticalAdapter;
import com.example.doan1.adapters.GenreAdapter;
import com.example.doan1.models.Book;
import com.example.doan1.models.BookResponse;
import com.example.doan1.models.Genre;
import com.example.doan1.models.GenreResponse;
import com.example.doan1.network.ApiService;
import com.example.doan1.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerGenres, recyclerBooks;
    private TextView tvTitle;
    private GenreAdapter genreAdapter;
    private BookVerticalAdapter bookAdapter;
    private List<Genre> genreList = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();
    private ApiService apiService;

    public CategoryFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Ánh xạ view
        recyclerGenres = view.findViewById(R.id.recyclerGenres);
        recyclerBooks = view.findViewById(R.id.recyclerBooks);
        tvTitle = view.findViewById(R.id.tvTitle);

        // Adapter & Layout
        recyclerGenres.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        genreAdapter = new GenreAdapter(genreList, this::loadBooksByGenre);
        recyclerGenres.setAdapter(genreAdapter);

        recyclerBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        bookAdapter = new BookVerticalAdapter(getContext(), bookList, book -> {
            Intent intent = new Intent(getContext(), TruyenDetailActivity.class);
            intent.putExtra("book_id", book.book_id);
            intent.putExtra("title", book.title);
            intent.putExtra("tacGia", String.valueOf(book.author_id)); // hoặc book.author_name nếu có
            intent.putExtra("cover_image_url", book.cover_image_url);// hoặc truyền cả object nếu Serializable
            startActivity(intent);
        });

        recyclerBooks.setAdapter(bookAdapter);

        // Retrofit
        apiService = RetrofitClient.getClient().create(ApiService.class);


        // Load dữ liệu thể loại
        loadGenres();

        return view;
    }

    private void loadGenres() {
        apiService.getGenres().enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    genreList.clear();
                    genreList.addAll(response.body().getGenres());
                    genreAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không lấy được thể loại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối API thể loại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBooksByGenre(Genre genre) {
        tvTitle.setVisibility(View.VISIBLE);
        recyclerBooks.setVisibility(View.VISIBLE);
        tvTitle.setText("Truyện thuộc thể loại: " + genre.getGenre_name());

        apiService.getBooksByGenre(genre.getGenre_id()).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    bookList.clear();
                    bookList.addAll(response.body().getBooks());
                    bookAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không lấy được danh sách truyện", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi gọi API truyện theo thể loại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
