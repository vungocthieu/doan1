package com.example.doan1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan1.R;
import com.example.doan1.adapters.NewTruyenAdapter;
import com.example.doan1.models.Truyen;
import com.example.doan1.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LichSuFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private final ArrayList<Truyen> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_su, container, false);

        recyclerView = view.findViewById(R.id.recyclerLichSu);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadHistory();

        return view;
    }

    private void loadHistory() {
        int userId = UserSession.getUserId(getContext());
        String url = "http://192.168.1.153/api/get_userbooks.php?user_id=" + userId + "&type=history";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    list.clear();
                    try {
                        if (response.getBoolean("success")) {
                            JSONArray books = response.getJSONArray("books");
                            if (books.length() == 0) {
                                tvEmpty.setVisibility(View.VISIBLE);
                                return;
                            }

                            for (int i = 0; i < books.length(); i++) {
                                JSONObject obj = books.getJSONObject(i);
                                list.add(new Truyen(
                                        obj.getInt("id"),
                                        obj.getString("tenTruyen"),
                                        obj.getString("tacGia"),
                                        obj.getString("hinhAnh"),
                                        obj.optString("theLoai", "Chưa rõ")
                                ));
                            }

                            tvEmpty.setVisibility(View.GONE);
                            recyclerView.setAdapter(new NewTruyenAdapter(getContext(), list));
                        } else {
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        tvEmpty.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Lỗi khi tải lịch sử đọc", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(getContext()).add(request);
    }
}
