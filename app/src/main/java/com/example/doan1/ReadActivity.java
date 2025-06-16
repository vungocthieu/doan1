package com.example.doan1;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadActivity extends AppCompatActivity {

    private TextView tvTieuDeChuong, tvNoiDung;
    private Button btnToggleMode;
    private boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        tvTieuDeChuong = findViewById(R.id.tvTieuDeChuong);
        tvNoiDung = findViewById(R.id.tvNoiDung);
        btnToggleMode = findViewById(R.id.btnToggleMode);

        // Load chế độ đọc từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("read_mode", MODE_PRIVATE);
        isDarkMode = prefs.getBoolean("dark", true);
        applyMode();  // Áp dụng giao diện

        btnToggleMode.setOnClickListener(v -> {
            isDarkMode = !isDarkMode;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("dark", isDarkMode);
            editor.apply();
            applyMode();
        });

        // Lấy chapter_id từ intent
        int chapterId = getIntent().getIntExtra("chapter_id", -1);
        if (chapterId != -1) {
            loadChapter(chapterId);
        } else {
            tvNoiDung.setText("Không tìm thấy chương.");
        }
    }

    private void loadChapter(int chapterId) {
        String url = "http://192.168.1.8/api/noi_dung_chuong.php?chuong_id=" + chapterId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.has("title") && response.has("content")) {
                            tvTieuDeChuong.setText(response.getString("title"));
                            tvNoiDung.setText(response.getString("content"));
                        } else {
                            tvNoiDung.setText("Không có nội dung chương.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvNoiDung.setText("Lỗi xử lý dữ liệu.");
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Lỗi kết nối đến API", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }

    // Giao diện đọc sáng/tối
    private void applyMode() {
        View root = findViewById(android.R.id.content);
        if (isDarkMode) {
            root.setBackgroundColor(Color.parseColor("#121212"));
            tvTieuDeChuong.setTextColor(Color.WHITE);
            tvNoiDung.setTextColor(Color.LTGRAY);
            btnToggleMode.setText("☀️ Chế độ Sáng");
        } else {
            root.setBackgroundColor(Color.WHITE);
            tvTieuDeChuong.setTextColor(Color.BLACK);
            tvNoiDung.setTextColor(Color.DKGRAY);
            btnToggleMode.setText("🌙 Chế độ Tối");
        }
    }
}
