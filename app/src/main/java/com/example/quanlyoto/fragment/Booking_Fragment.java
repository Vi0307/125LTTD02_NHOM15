package com.example.quanlyoto.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Booking_Fragment extends Fragment {

    public Booking_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        // BOTTOM NAV — HOME
        view.findViewById(R.id.navHome).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // BOTTOM NAV — PARTS
        view.findViewById(R.id.navParts).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homeparts())
                    .commit();
        });

        // BOTTOM NAV — MYCAR
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarFragment())
                    .commit();
        });

        // BOTTOM NAV — VOUCHER
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

        // CHATBOX → ChatFragment
        View chatBtn = view.findViewById(R.id.btnChat);
        if (chatBtn != null) {
            chatBtn.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())
                        .addToBackStack(null)
                        .commit();
            });
        }
        super.onViewCreated(view, savedInstanceState);

        setupBackButton(view);
    }

    private void setupBackButton(View view) {
        ImageView btnBack = view.findViewById(R.id.btnBack_agency_detail);
        TextView txtCancel = view.findViewById(R.id.txtCancel);

        View.OnClickListener goBackListener = v ->
                requireActivity()
                        .getSupportFragmentManager()
                        .popBackStack();

        if (btnBack != null) {
            btnBack.setOnClickListener(goBackListener);
        }

        if (txtCancel != null) {
            txtCancel.setOnClickListener(goBackListener);
        }
    }
}
