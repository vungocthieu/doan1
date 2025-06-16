// File: GioiThieuFragment.java
package com.example.doan1.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan1.R;
import com.example.doan1.TruyenDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class GioiThieuFragment extends Fragment {

    private TextView tvGioiThieu;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);

        tvGioiThieu = view.findViewById(R.id.tvGioiThieu);
        requestQueue = Volley.newRequestQueue(requireContext());

        int bookId = TruyenDetailActivity.getBookId();
        Log.d("GioiThieuFragment", "bookId: " + bookId);

        if (bookId != -1) {
            loadGioiThieu(bookId);
        } else {
            tvGioiThieu.setText("Không tìm thấy mã truyện.");
        }

        return view;
    }

    private void loadGioiThieu(int bookId) {

        String url = "http://192.168.1.153/api/gioi_thieu.php?book_id=" + bookId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.has("description")) {
                            String moTa = response.getString("description");
                            tvGioiThieu.setText(moTa);
                        } else {
                            tvGioiThieu.setText("Không có mô tả.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvGioiThieu.setText("Lỗi xử lý dữ liệu.");
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });

        requestQueue.add(request);
    }
}
