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

public class Details extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_details_screen, container, false);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        Button btnAddToCart = view.findViewById(R.id.btnAddToCart);

        // 🔙 Nút back → quay về Bodyparts
        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // 🛒 Nút "THÊM GIỎ HÀNG" → sang Cart
        btnAddToCart.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Cart())
                        .addToBackStack(null)
                        .commit()
        );

        return view;
    }
}
