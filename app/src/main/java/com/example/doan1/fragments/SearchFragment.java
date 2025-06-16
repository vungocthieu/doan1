package com.example.doan1.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan1.adapters.SearchBookAdapter;
import com.example.doan1.models.SearchBook;

import com.example.doan1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText edtSearch;
    private RecyclerView rvResults;
    private SearchBookAdapter adapter;
    private List<SearchBook> bookList = new ArrayList<>();
    private static final String SEARCH_URL = "http://192.168.1.8/api/search_books.php?query=";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        rvResults = view.findViewById(R.id.rvSearchResults);

        rvResults.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchBookAdapter(bookList, getContext());
        rvResults.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2) {
                    fetchResults(s.toString());
                } else {
                    bookList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void fetchResults(String keyword) {
        String url = SEARCH_URL + Uri.encode(keyword);
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    bookList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            SearchBook book = new SearchBook(
                                    obj.getInt("book_id"),
                                    obj.getString("title"),
                                    obj.getString("cover_image_url"),
                                    obj.getString("author_name")
                            );
                            bookList.add(book);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(getContext(), "Lỗi tìm kiếm!", Toast.LENGTH_SHORT).show()
        );
        queue.add(request);
    }
}
