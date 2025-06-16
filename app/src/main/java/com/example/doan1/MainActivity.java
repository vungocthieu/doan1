package com.example.doan1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.doan1.fragments.CategoryFragment;
import com.example.doan1.fragments.HomeFragment;
import com.example.doan1.fragments.LibraryFragment;
import com.example.doan1.fragments.RankFragment;
import com.example.doan1.fragments.TaiKhoanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        // Mặc định hiển thị HomeFragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_library) {
                selectedFragment = new LibraryFragment();
            } else if (id == R.id.nav_category) {
                selectedFragment = new CategoryFragment();
            } else if (id == R.id.nav_rank) {
                selectedFragment = new RankFragment();
            } else if (id == R.id.nav_account) {
                selectedFragment = new TaiKhoanFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
}
