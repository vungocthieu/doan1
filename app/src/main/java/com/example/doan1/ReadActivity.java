package com.example.doan1;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadActivity extends AppCompatActivity {

    private TextView tvTieuDeChuong, tvNoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        tvTieuDeChuong = findViewById(R.id.tvTieuDeChuong);
        tvNoiDung = findViewById(R.id.tvNoiDung);

        int chuongId = getIntent().getIntExtra("chuong_id", -1);

        if (chuongId != -1) {
            loadNoiDungChuong(chuongId);
        } else {
            tvNoiDung.setText("Không tìm thấy chương.");
        }
    }

    private void loadNoiDungChuong(int chuongId) {
        String url = "http://192.168.1.8/api/noi_dung_chuong.php?chuong_id=" + chuongId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.has("title") && response.has("content")) {
                            tvTieuDeChuong.setText(response.getString("title"));
                            tvNoiDung.setText(response.getString("content"));
                        } else {
                            tvNoiDung.setText("Không có nội dung.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvNoiDung.setText("Lỗi xử lý dữ liệu.");
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }
}
