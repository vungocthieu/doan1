package com.example.doan1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan1.LoginActivity;
import com.example.doan1.R;
import com.example.doan1.TruyenDetailActivity;
import com.example.doan1.adapters.BinhLuanAdapter;
import com.example.doan1.models.BinhLuan;
import com.example.doan1.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BinhLuanFragment extends Fragment {

    private RecyclerView recyclerBinhLuan;
    private EditText edtNoiDungBinhLuan;
    private Button btnGuiBinhLuan;
    private BinhLuanAdapter adapter;
    private ArrayList<BinhLuan> binhLuanList;
    private RequestQueue requestQueue;
    private int bookId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_binh_luan, container, false);

        recyclerBinhLuan = view.findViewById(R.id.recyclerBinhLuan);
        edtNoiDungBinhLuan = view.findViewById(R.id.edtNoiDungBinhLuan);
        btnGuiBinhLuan = view.findViewById(R.id.btnGuiBinhLuan);

        bookId = TruyenDetailActivity.getBookId();

        requestQueue = Volley.newRequestQueue(requireContext());
        recyclerBinhLuan.setLayoutManager(new LinearLayoutManager(getContext()));
        binhLuanList = new ArrayList<>();
        adapter = new BinhLuanAdapter(getContext(), binhLuanList);
        recyclerBinhLuan.setAdapter(adapter);

        loadBinhLuan();

        btnGuiBinhLuan.setOnClickListener(v -> {
            if (!UserSession.isLoggedIn(getContext())) {
                Toast.makeText(getContext(), "Bạn cần đăng nhập để bình luận", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                return;
            }

            String comment = edtNoiDungBinhLuan.getText().toString().trim();
            if (TextUtils.isEmpty(comment)) {
                Toast.makeText(getContext(), "Nội dung bình luận không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = UserSession.getUserId(getContext());
            guiBinhLuan(userId, bookId, comment);
        });

        return view;
    }

    private void loadBinhLuan() {
        String url = "http://192.168.1.153/api/reviews.php?book_id=" + bookId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    binhLuanList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            String username = obj.getString("username");
                            String comment = obj.getString("comment");
                            String date = obj.getString("review_date");

                            binhLuanList.add(new BinhLuan(username, comment, date));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(getContext(), "Lỗi tải bình luận", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

    private void guiBinhLuan(int userId, int bookId, String comment) {
        String url = "http://192.168.1.153/api/reviews.php";

        JSONObject body = new JSONObject();
        try {
            body.put("user_id", userId);
            body.put("book_id", bookId);
            body.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            edtNoiDungBinhLuan.setText("");
                            loadBinhLuan();
                            Toast.makeText(getContext(), "Bình luận đã gửi", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "Lỗi khi gửi bình luận", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }
}
