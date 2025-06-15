// File: TruyenDetailActivity.java
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

import com.bumptech.glide.Glide;
import com.example.doan1.adapters.TruyenPagerAdapter;
import com.example.doan1.fragments.BinhLuanFragment;
import com.example.doan1.fragments.DanhSachChuongFragment;
import com.example.doan1.fragments.GioiThieuFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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

    public static int getBookId() {
        return bookId;
    }


    public static String getTenTruyen() {
        return tenTruyen;
    }

    public static String getTacGia() {
        return tacGia;
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
        String hinhAnh = intent.getStringExtra("hinhAnh");

        tvTitle.setText(tenTruyen);
        tvAuthor.setText(tacGia);
        tvStats.setText("842 chương - 311K lượt đọc");

        Glide.with(this).load(hinhAnh).into(imgCover);

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
            Toast.makeText(this, "Đọc truyện sẽ triển khai sau", Toast.LENGTH_SHORT).show();
        });

        btnThemTuTruyen.setOnClickListener(v -> {
            Toast.makeText(this, "Đã thêm vào tủ truyện", Toast.LENGTH_SHORT).show();
        });
    }
}
