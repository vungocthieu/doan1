package com.example.doan1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doan1.LoginActivity;
import com.example.doan1.R;
import com.example.doan1.RegisterActivity;
import com.example.doan1.utils.UserSession;

public class TaiKhoanFragment extends Fragment {

    private TextView tvUserInfo;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        Button btnDangNhap = view.findViewById(R.id.btnLogin);
        Button btnDangKy = view.findViewById(R.id.btnRegister);

        tvUserInfo = view.findViewById(R.id.tvUserInfo);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Hiển thị thông tin người dùng
        if (UserSession.isLoggedIn(requireContext())) {
            int userId = UserSession.getUserId(requireContext());
            String username = UserSession.getUsername(requireContext());
            tvUserInfo.setText("Xin chào, " + username + " (ID: " + userId + ")");
        } else {
            tvUserInfo.setText("Bạn chưa đăng nhập");
            btnLogout.setEnabled(false);
        }

        btnDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        btnDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        // Xử lý nút đăng xuất
        btnLogout.setOnClickListener(v -> {
            UserSession.logout(requireContext());
            Toast.makeText(requireContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            // Chuyển về màn hình đăng nhập
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}
