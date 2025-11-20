package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class HomeFragment extends Fragment {

    public HomeFragment() { /* required empty constructor */ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        // ================================
        // 1. Bấm nav "Xe của tôi"
        // ================================
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // ================================
        // 2. Bấm nav "Phụ tùng"
        // ================================
        View navParts = view.findViewById(R.id.navParts);
        if (navParts != null) {
            navParts.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Homeparts()) // <-- Fragment phụ tùng
                        .addToBackStack(null)
                        .commit();
            });
        }

        // ================================
        // 3. Bấm "Xem chi tiết"
        // ================================
        View btnXem = view.findViewById(R.id.btnXemChiTiet);
        if (btnXem != null) {
            btnXem.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarDetailFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // ================================
        // 4. Bấm arrowIcon cũng sang chi tiết
        // ================================
        View arrow = view.findViewById(R.id.arrowIcon);
        if (arrow != null) {
            arrow.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarDetailFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }
}
