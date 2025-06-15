package com.example.doan1.fragments;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan1.R;
import com.example.doan1.TruyenDetailActivity;
import com.example.doan1.adapters.ChuongAdapter;
import com.example.doan1.models.Chuong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DanhSachChuongFragment extends Fragment {

    private RecyclerView recyclerChuong;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_chuong, container, false);

        recyclerChuong = view.findViewById(R.id.recyclerChuong);
        recyclerChuong.setLayoutManager(new LinearLayoutManager(getContext()));
        requestQueue = Volley.newRequestQueue(requireContext());

        int bookId = TruyenDetailActivity.getBookId();
        if (bookId != -1) {
            loadChapters(bookId);
        }

        return view;
    }

    private void loadChapters(int bookId) {
        String url = "http://192.168.1.8/api/chuong.php?book_id=" + bookId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Chuong> chuongList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            chuongList.add(new Chuong(obj.getInt("chapter_id"), obj.getString("title")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ChuongAdapter adapter = new ChuongAdapter(getContext(), chuongList);
                    recyclerChuong.setAdapter(adapter);
                },
                error -> Toast.makeText(getContext(), "Lỗi tải danh sách chương", Toast.LENGTH_SHORT).show());

        requestQueue.add(request);
    }
}
