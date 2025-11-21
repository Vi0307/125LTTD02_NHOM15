package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Agency_Detail_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_agency_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBackButton(view);
        setupBottomNav(view);
        setupButtonDatDichVu(view);
    }

    private void setupBackButton(View view) {
        ImageView icBack = view.findViewById(R.id.ic_back_agency);

        if (icBack != null) {
            icBack.setOnClickListener(v ->
                    requireActivity().getSupportFragmentManager().popBackStack());
        }
    }

    private void setupButtonDatDichVu(View view) {
        View btnDatDichVu = view.findViewById(R.id.btnDatDichVu);

        if (btnDatDichVu != null) {
            btnDatDichVu.setOnClickListener(v -> navigate(new Booking_Fragment()));
        }
    }

    private void setupBottomNav(View view) {

        LinearLayout navHome = view.findViewById(R.id.navHome);
        LinearLayout navCar = view.findViewById(R.id.navCar);
        LinearLayout navVoucher = view.findViewById(R.id.navVoucher);
        LinearLayout navParts = view.findViewById(R.id.navParts);
        LinearLayout navAgency = view.findViewById(R.id.navAgency);

        navHome.setOnClickListener(v -> navigate(new HomeFragment()));
        navCar.setOnClickListener(v -> navigate(new MyCarFragment()));
//        navVoucher.setOnClickListener(v -> navigate(new VoucherFragment()));
//        navParts.setOnClickListener(v -> navigate(new PartsFragment()));
        navAgency.setOnClickListener(v -> {}); // Đang ở trang này nên không làm gì
    }

    private void navigate(Fragment fragment) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}

