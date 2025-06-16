package com.example.doan1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan1.R;
import com.example.doan1.TruyenDetailActivity;
import com.example.doan1.adapters.BookVerticalAdapter;
import com.example.doan1.models.Book;
import com.example.doan1.models.BookResponse;
import com.example.doan1.network.ApiService;
import com.example.doan1.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankFragment extends Fragment {

    private RecyclerView recyclerRank;
    private BookVerticalAdapter rankAdapter;
    private List<Book> topBooks = new ArrayList<>();
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        // Khởi tạo view
        recyclerRank = view.findViewById(R.id.recyclerRank);
        recyclerRank.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapter
        rankAdapter = new BookVerticalAdapter(getContext(), topBooks, book -> {
            Intent intent = new Intent(getContext(), TruyenDetailActivity.class);
            intent.putExtra("book_id", book.book_id);
            intent.putExtra("title", book.title);
            intent.putExtra("tacGia", String.valueOf(book.author_id)); // hoặc tên tác giả nếu có
            intent.putExtra("cover_image_url", book.cover_image_url);
            startActivity(intent);
        });
        recyclerRank.setAdapter(rankAdapter);

        // Gọi API
        apiService = RetrofitClient.getClient().create(ApiService.class);
        loadTopBooks();

        return view;
    }

    private void loadTopBooks() {
        Call<BookResponse> call = apiService.getTopBooks();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookResponse> call, @NonNull Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    topBooks.clear();
                    topBooks.addAll(response.body().getBooks());
                    rankAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không tải được danh sách xếp hạng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối tới API xếp hạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
