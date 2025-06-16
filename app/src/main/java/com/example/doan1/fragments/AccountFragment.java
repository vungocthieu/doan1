package com.example.doan1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doan1.LoginActivity;
import com.example.doan1.R;
import com.example.doan1.RegisterActivity;
import com.example.doan1.utils.UserSession;

public class AccountFragment extends Fragment {

    private TextView tvUserInfo;
    private Button btnLogin, btnRegister, btnLogout;
    private ImageView imgAvatar;

    public AccountFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUserInfo = view.findViewById(R.id.tvUserInfo);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnLogout = view.findViewById(R.id.btnLogout);
        imgAvatar = view.findViewById(R.id.imgAvatar);

        updateUI();

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), RegisterActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            UserSession.logout(getContext());
            updateUI();
        });

        return view;
    }

    private void updateUI() {
        if (UserSession.isLoggedIn(getContext())) {
            String username = UserSession.getUsername(getContext());
            tvUserInfo.setText("Xin chào, " + username);
            btnLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            tvUserInfo.setText("Xin chào, Người dùng");
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }
    }
}
