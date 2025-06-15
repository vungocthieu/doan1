package com.example.doan1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.doan1.models.Truyen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerNew;
    private RecyclerView recyclerRecommended;

    private RecyclerView recyclerCompleted;


    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Ánh xạ RecyclerView
        recyclerNew = view.findViewById(R.id.recyclerNew);

        // 2. Cài đặt hiển thị dạng lưới 3 cột
        recyclerNew.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // 3. Gọi API để lấy truyện "mới đăng"
        loadNewStories();

        recyclerRecommended = view.findViewById(R.id.recyclerRecommended);
        loadRecommendedStories();
        recyclerRecommended.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerCompleted = view.findViewById(R.id.recyclerCompleted);
        loadCompletedStories();
        recyclerCompleted.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return view;
    }

    private void loadNewStories() {
        String url = "http://192.168.1.8/api/truyen_moi.php";
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
                                    obj.optString("theLoai", "Huyền Huyễn") // thêm thể loại nếu có
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
        String url = "http://192.168.1.8/api/truyen_de_cu.php"; // Đổi IP nếu khác
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
                    recyclerRecommended.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerRecommended.setAdapter(adapter);
                },
                error -> Toast.makeText(getContext(), "Lỗi lấy đề cử", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void loadCompletedStories() {
        String url = "http://192.168.1.8/api/truyen_hoan_thanh.php";
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
                    recyclerCompleted.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    recyclerCompleted.setAdapter(adapter);
                },
                error -> Toast.makeText(getContext(), "Lỗi tải truyện hoàn thành", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }


}
