package com.example.doan1.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan1.R;
import com.example.doan1.adapters.NewTruyenAdapter;
import com.example.doan1.adapters.SearchBookAdapter;
import com.example.doan1.models.SearchBook;
import com.example.doan1.models.Truyen;
import com.example.doan1.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerNew, recyclerRecommended, recyclerCompleted, recyclerSearchResults;
    private EditText editSearch;
    private List<SearchBook> searchList;
    private SearchBookAdapter searchAdapter;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        if (UserSession.isLoggedIn(getContext())) {
            String username = UserSession.getUsername(getContext());
            tvUsername.setText("Xin chào, " + username);
        } else {
            tvUsername.setText("Bạn chưa đăng nhập");
        }

        // Ánh xạ danh sách
        recyclerNew = view.findViewById(R.id.recyclerNew);
        recyclerRecommended = view.findViewById(R.id.recyclerRecommended);
        recyclerCompleted = view.findViewById(R.id.recyclerCompleted);
        recyclerSearchResults = view.findViewById(R.id.recyclerSearchResults);
        editSearch = view.findViewById(R.id.editSearch);

        recyclerNew.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerRecommended.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerCompleted.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo adapter cho tìm kiếm
        searchList = new ArrayList<>();
        searchAdapter = new SearchBookAdapter(searchList, getContext());
        recyclerSearchResults.setAdapter(searchAdapter);

        // Gọi dữ liệu ban đầu
        loadNewStories();
        loadRecommendedStories();
        loadCompletedStories();

        // Xử lý tìm kiếm
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2) {
                    fetchSearchResults(s.toString());
                    recyclerNew.setVisibility(View.GONE);
                    recyclerRecommended.setVisibility(View.GONE);
                    recyclerCompleted.setVisibility(View.GONE);
                } else {
                    recyclerSearchResults.setVisibility(View.GONE);
                    recyclerNew.setVisibility(View.VISIBLE);
                    recyclerRecommended.setVisibility(View.VISIBLE);
                    recyclerCompleted.setVisibility(View.VISIBLE);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void loadNewStories() {
        String url = "http://192.168.1.153/api/truyen_moi.php";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Truyen> list = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            list.add(new Truyen(
                                    obj.getInt("id"),
                                    obj.getString("tenTruyen"),
                                    obj.getString("tacGia"),
                                    obj.getString("hinhAnh"),
                                    obj.optString("theLoai", "Huyền Huyễn")
                            ));
                        }
                        NewTruyenAdapter adapter = new NewTruyenAdapter(getContext(), list);
                        recyclerNew.setAdapter(adapter);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void loadRecommendedStories() {
        String url = "http://192.168.1.153/api/truyen_de_cu.php";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Truyen> list = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            list.add(new Truyen(
                                    obj.getInt("id"),
                                    obj.getString("tenTruyen"),
                                    obj.getString("tacGia"),
                                    obj.getString("hinhAnh"),
                                    obj.getString("theLoai")
                            ));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    NewTruyenAdapter adapter = new NewTruyenAdapter(getContext(), list);
                    recyclerRecommended.setAdapter(adapter);
                },
                error -> Toast.makeText(getContext(), "Lỗi lấy đề cử", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void loadCompletedStories() {
        String url = "http://192.168.1.153/api/truyen_hoan_thanh.php";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Truyen> list = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            list.add(new Truyen(
                                    obj.getInt("id"),
                                    obj.getString("tenTruyen"),
                                    obj.getString("tacGia"),
                                    obj.getString("hinhAnh"),
                                    obj.getString("theLoai")
                            ));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    NewTruyenAdapter adapter = new NewTruyenAdapter(getContext(), list);
                    recyclerCompleted.setAdapter(adapter);
                },
                error -> Toast.makeText(getContext(), "Lỗi tải truyện hoàn thành", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void fetchSearchResults(String keyword) {
        String url = "http://192.168.1.153/api/search_books.php?query=" + keyword;
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    searchList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            SearchBook book = new SearchBook(
                                    obj.getInt("book_id"),
                                    obj.getString("title"),
                                    obj.getString("cover_image_url"),
                                    obj.getString("author_name")
                            );
                            searchList.add(book);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    searchAdapter.notifyDataSetChanged();
                    recyclerSearchResults.setVisibility(View.VISIBLE);
                },
                error -> Toast.makeText(getContext(), "Lỗi khi tìm kiếm", Toast.LENGTH_SHORT).show()
        );
        queue.add(request);
    }
}