package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Login extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_login_screen, container, false);

        ImageView btnBack   = view.findViewById(R.id.btnBack);
        Button btnLogin     = view.findViewById(R.id.btnLogin); // ID bạn yêu cầu

        // 🔙 Nút quay lại trang GetStarted
        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // ✔️ Nút ĐĂNG NHẬP → chuyển sang HomeFragment
        btnLogin.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit()   // Không addToBackStack → không quay lại login
        );

        return view;
    }
}
