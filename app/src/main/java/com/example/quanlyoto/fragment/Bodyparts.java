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

public class Bodyparts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_bodyparts_screen, container, false);

        // Khai báo View
        Button btnAddToCart = view.findViewById(R.id.btnAddToCart);
        ImageView imgOngGioNapHoi = view.findViewById(R.id.imgOngGioNapHoi);
        ImageView btnBack = view.findViewById(R.id.btnBack);

        // 👉 Nút back → quay về Homeparts
        btnBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .popBackStack();   // trở về fragment trước đó (Homeparts)
        });

        // 👉 Click ảnh → sang Details
        imgOngGioNapHoi.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Details())
                    .addToBackStack(null)
                    .commit();
        });

        // 👉 Click nút "THÊM GIỎ HÀNG" → sang Cart
        btnAddToCart.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Cart())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
