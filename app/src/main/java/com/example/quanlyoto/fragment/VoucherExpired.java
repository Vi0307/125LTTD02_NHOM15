package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class VoucherExpired extends Fragment {

    private TextView tabActive, tabUsed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voucher_expired, container, false);

        tabActive = view.findViewById(R.id.tab_active); // "Còn hiệu lực"
        tabUsed = view.findViewById(R.id.tab_used);     // "Đã sử dụng"

        // Click vào tab "Còn hiệu lực" → chuyển sang VoucherStillValidFragment
        tabActive.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .addToBackStack(null) // nếu muốn back quay lại VoucherExpired
                    .commit();
        });

        // Optional: tabUsed đang ở fragment này, không cần click
        tabUsed.setOnClickListener(v -> { /* Không làm gì */ });

        ImageView btnBack = view.findViewById(R.id.btn_back);

        // Quay lại Welcome
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
