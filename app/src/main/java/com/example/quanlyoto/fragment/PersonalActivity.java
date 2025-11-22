package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.quanlyoto.R;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PersonalActivity extends Fragment {

    private ImageView btnBack, btnEdit;
    private LinearLayout btnLogout, itemVoucher, itemOrder;
    private FrameLayout logoutOverlay;
    private CardView btnCancel, btnConfirmLogout;
    private FloatingActionButton fabChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_personal, container, false);

        // Ánh xạ các view
        btnBack = view.findViewById(R.id.btn_back);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnLogout = view.findViewById(R.id.btn_logout);
        itemVoucher = view.findViewById(R.id.item_voucher);
        itemOrder = view.findViewById(R.id.item_order);
        logoutOverlay = view.findViewById(R.id.logout_overlay);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnConfirmLogout = view.findViewById(R.id.btn_confirm_logout);
        fabChat = view.findViewById(R.id.btn_logout_confirm);

        // Nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        // Nút Edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Chỉnh sửa thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        // Voucher
        itemVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Đơn hàng
        itemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MyOrderDelivering())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Nút Đăng xuất - Hiện dialog
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutOverlay.setVisibility(View.VISIBLE);
            }
        });

        // Nút Đóng - Đóng dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutOverlay.setVisibility(View.GONE);
            }
        });

        btnConfirmLogout.setOnClickListener(v -> {
            logoutOverlay.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            // Chuyển sang WelcomeFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Welcome())
                    .commit();
        });


        // Click vào overlay cũng đóng dialog
        logoutOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutOverlay.setVisibility(View.GONE);
            }
        });

        // FAB Chat
        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Xử lý nút Back của hệ thống
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