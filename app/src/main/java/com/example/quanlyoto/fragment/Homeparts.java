package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Homeparts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_homeparts_screen, container, false);

        // ⭐ Icon giỏ hàng — mở trang Cart
        View imgCart = view.findViewById(R.id.imgCart);
        if (imgCart != null) {
            imgCart.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Cart())
                        .addToBackStack(null)
                        .commit();
            });
        }
        // ⭐ Nút "Phụ tùng thân vỏ"
        Button btnBodyParts = view.findViewById(R.id.btnBodyParts);

        btnBodyParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Bodyparts())
                    .addToBackStack(null)
                    .commit();
        });

        // ⭐ Nút "Phụ tùng động cơ"
        Button btnEngineParts = view.findViewById(R.id.btnEngineParts);

        btnEngineParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Engineparts())
                    .addToBackStack(null)
                    .commit();
        });

        // ⭐ Nút "Hệ truyền động" — DrivetrainParts
        Button btnDrivetrainParts = view.findViewById(R.id.btnTransmissionParts);

        btnDrivetrainParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Drivetrainparts())
                    .addToBackStack(null)
                    .commit();
        });

        // ⭐ Nút "Phụ tùng điện"
        Button btnElectricalParts = view.findViewById(R.id.btnLightingParts);

        btnElectricalParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Electricalparts())
                    .addToBackStack(null)     // quay lại Homeparts
                    .commit();
        });

        // ======================================================
        // BOTTOM NAV
        // ======================================================


        // 2. Trang chủ
        View navParts = view.findViewById(R.id.navHome);
        if (navParts != null) {
            navParts.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }


        // 1. Xe của tôi
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarFragment())
                    .addToBackStack(null)
                    .commit();
        });


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

        // 4. ĐẠI LÝ
        View navAgency = view.findViewById(R.id.navAgency);
        if (navAgency != null) {
            navAgency.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Agency_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // ======================================================
        // FAB CHAT — MỞ TRANG CHAT
        // ======================================================
        View btnChat = view.findViewById(R.id.fabChatbox);
        if (btnChat != null) {
            btnChat.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }
}
