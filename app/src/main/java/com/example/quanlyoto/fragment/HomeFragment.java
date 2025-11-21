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

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        // ======================================================
        // BOTTOM NAV
        // ======================================================

        // 1. Xe của tôi
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 2. Phụ tùng
        View navParts = view.findViewById(R.id.navParts);
        if (navParts != null) {
            navParts.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Homeparts())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // 3. Voucher
        View navVoucher = view.findViewById(R.id.navVoucher);
        if (navVoucher != null) {
            navVoucher.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // ======================================================
        // CÁC NÚT TRONG TRANG HOME
        // ======================================================

        // Xem chi tiết
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

        // Arrow icon → sang chi tiết
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

        // Mua sắm ngay → sang Homeparts
        View btnMuaSam = view.findViewById(R.id.btnMuaSamNgay);
        if (btnMuaSam != null) {
            btnMuaSam.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Homeparts())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Xem thêm voucher
        View btnXemVC = view.findViewById(R.id.btnXemVoucher);
        if (btnXemVC != null) {
            btnXemVC.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .addToBackStack(null)
                        .commit();
            });
        }
        // ================================
        // BẤM VÀO TÊN USER -> TRANG INFO
        // ================================
        View userName = view.findViewById(R.id.UserName);
        if (userName != null) {
            userName.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PersonalActivity())
                        .addToBackStack(null)
                        .commit();
            });
        }


        // ======================================================
        // FAB CHAT — MỞ TRANG CHAT
        // ======================================================
        View btnChat = view.findViewById(R.id.btnChat);
        if (btnChat != null) {
            btnChat.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())  // tạo ChatFragment
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }
}
