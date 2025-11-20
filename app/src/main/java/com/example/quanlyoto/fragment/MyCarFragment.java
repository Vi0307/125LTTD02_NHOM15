package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class MyCarFragment extends Fragment {

    public MyCarFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycar, container, false);

        // ==========================
        // NÚT BACK → HOME
        // ==========================
        view.findViewById(R.id.btn_back).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // ==========================
        // BOTTOM NAV — HOME
        // ==========================
        view.findViewById(R.id.navHome).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // ==========================
        // BOTTOM NAV — PARTS
        // ==========================
        view.findViewById(R.id.navParts).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homeparts())
                    .commit();
        });

        // ==========================
        // BOTTOM NAV — CAR (đang ở đây)
        // ==========================
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
        });

        // ==========================
        // BUTTON — XEM CHI TIẾT
        // ==========================
        view.findViewById(R.id.btnXemChiTiet).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarDetailFragment())
                    .addToBackStack(null)   // cho phép back lại
                    .commit();
        });

        return view;
    }
}
