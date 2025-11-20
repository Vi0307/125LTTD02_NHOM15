package com.example.quanlyoto.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.quanlyoto.R; // Vẫn phải import R từ package gốc


import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

public class PersonalActivity extends Fragment {

    private ImageView btnBack, btnEdit;
    private LinearLayout btnLogout, itemVoucher, itemOrder;
    private FrameLayout logoutOverlay;
    private Button btnCancel, btnConfirmLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_personal, container, false);

        btnBack = view.findViewById(R.id.btn_back);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnLogout = view.findViewById(R.id.btn_logout);
        itemVoucher = view.findViewById(R.id.item_voucher);
        itemOrder = view.findViewById(R.id.item_order);
        logoutOverlay = view.findViewById(R.id.logout_overlay);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnConfirmLogout = view.findViewById(R.id.btn_confirm_logout);

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        btnEdit.setOnClickListener(v -> Toast.makeText(getActivity(), "Chỉnh sửa thông tin", Toast.LENGTH_SHORT).show());
        itemVoucher.setOnClickListener(v -> Toast.makeText(getActivity(), "Mở voucher của tôi", Toast.LENGTH_SHORT).show());
        itemOrder.setOnClickListener(v -> Toast.makeText(getActivity(), "Mở đơn hàng của tôi", Toast.LENGTH_SHORT).show());

        btnLogout.setOnClickListener(v -> logoutOverlay.setVisibility(View.VISIBLE));
        btnCancel.setOnClickListener(v -> logoutOverlay.setVisibility(View.GONE));
        logoutOverlay.setOnClickListener(v -> logoutOverlay.setVisibility(View.GONE));

        btnConfirmLogout.setOnClickListener(v -> {
            // Ví dụ về fragment AppointmentCheck
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AppointmentCheckActivity())
                    .addToBackStack(null)
                    .commit();
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (logoutOverlay.getVisibility() == View.VISIBLE) {
                            logoutOverlay.setVisibility(View.GONE);
                        } else {
                            getParentFragmentManager().popBackStack();
                        }
                    }
                });

        return view;
    }
}
