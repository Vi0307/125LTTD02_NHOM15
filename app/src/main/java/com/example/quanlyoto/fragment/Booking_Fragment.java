package com.example.quanlyoto.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Booking_Fragment extends Fragment {

    public Booking_Fragment() {
        // Bắt buộc phải có constructor rỗng
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Gắn layout thay cho setContentView()
        return inflater.inflate(R.layout.activity_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btnBack = view.findViewById(R.id.btnBack_agency_detail);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // Chuyển fragment thay vì startActivity
                Fragment agencyFragment = new Agency_Fragment();

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, agencyFragment) // container trong Activity
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
