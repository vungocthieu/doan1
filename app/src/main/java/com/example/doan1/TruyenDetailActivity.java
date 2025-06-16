package com.example.doan1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.doan1.adapters.TruyenPagerAdapter;
import com.example.doan1.fragments.BinhLuanFragment;
import com.example.doan1.fragments.DanhSachChuongFragment;
import com.example.doan1.fragments.GioiThieuFragment;
import com.example.doan1.utils.UserSession;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class TruyenDetailActivity extends AppCompatActivity {

    private ImageView imgCover;
    private TextView tvTitle, tvAuthor, tvStats;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Button btnDocTruyen, btnThemTuTruyen;

    private static int bookId;
    private static String tenTruyen;
    private static String tacGia;
    private static String hinhAnh;

    public static int getBookId() {
        return bookId;
    }

    public static String getTenTruyen() {
        return tenTruyen;
    }

    public static String getTacGia() {
        return tacGia;
    }

    public static String getHinhAnh() {
        return hinhAnh;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truyen_detail);

        imgCover = findViewById(R.id.imgCover);
        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvStats = findViewById(R.id.tvStats);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btnDocTruyen = findViewById(R.id.btnDocTruyen);
        btnThemTuTruyen = findViewById(R.id.btnThemTuTruyen);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("book_id", -1);
        tenTruyen = intent.getStringExtra("tenTruyen");
        tacGia = intent.getStringExtra("tacGia");
        hinhAnh = intent.getStringExtra("cover_image_url");

        if (tenTruyen != null && !tenTruyen.isEmpty()) {
            tvTitle.setText(tenTruyen);
        } else {
            tvTitle.setText("Không có tiêu đề");
        }

        if (tacGia != null && !tacGia.isEmpty()) {
            tvAuthor.setText("Tác giả: " + tacGia);
        } else {
            tvAuthor.setText("Tác giả không rõ");
        }

        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            Glide.with(this).load(hinhAnh).placeholder(R.drawable.image_load_error).into(imgCover);
        } else {
            imgCover.setImageResource(R.drawable.image_load_error);
        }

        tvStats.setText("10 chương - 311K lượt đọc");

        List<Fragment> fragments = Arrays.asList(
                new GioiThieuFragment(),
                new BinhLuanFragment(),
                new DanhSachChuongFragment()
        );

        List<String> titles = Arrays.asList("Giới Thiệu", "Bình Luận", "D.S Chương");
        TruyenPagerAdapter adapter = new TruyenPagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles.get(position))
        ).attach();

        btnDocTruyen.setOnClickListener(v -> {
            if (bookId == -1) {
                Toast.makeText(this, "Thiếu thông tin truyện.", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://192.168.1.153/api/get_first_chapter.php?book_id=" + bookId;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            if (response.getBoolean("success")) {
                                int chapterId = response.getInt("chapter_id");

                                if (UserSession.isLoggedIn(this)) {
                                    int userId = UserSession.getUserId(this);
                                    String saveUrl = "http://192.168.1.153/api/save_userbook.php";

                                    JSONObject saveBody = new JSONObject();
                                    saveBody.put("user_id", userId);
                                    saveBody.put("book_id", bookId);
                                    saveBody.put("status", "Read");

                                    JsonObjectRequest saveRequest = new JsonObjectRequest(
                                            Request.Method.POST, saveUrl, saveBody,
                                            resp -> {}, error -> error.printStackTrace()
                                    );
                                    Volley.newRequestQueue(this).add(saveRequest);
                                }

                                Intent intentDoc = new Intent(this, ReadActivity.class);
                                intentDoc.putExtra("book_id", bookId);
                                intentDoc.putExtra("chapter_id", chapterId);
                                startActivity(intentDoc);
                            } else {
                                Toast.makeText(this, "Truyện chưa có chương nào.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(this, "Lỗi kết nối tới server", Toast.LENGTH_SHORT).show()
            );

            Volley.newRequestQueue(this).add(request);
        });

        btnThemTuTruyen.setOnClickListener(v -> addToUserBooks("Want to Read"));
    }

    private void addToUserBooks(String type) {
        if (!UserSession.isLoggedIn(this)) {
            Toast.makeText(this, "Bạn cần đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = UserSession.getUserId(this);
        String url = "http://192.168.1.153/api/save_userbook.php";

        JSONObject body = new JSONObject();
        try {
            body.put("user_id", userId);
            body.put("book_id", bookId);
            body.put("status", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            if (type.equals("Want to Read")) {
                                Toast.makeText(this, "Đã thêm vào tủ truyện", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String msg = response.optString("message", "Thêm thất bại");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Lỗi kết nối tới server", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}
