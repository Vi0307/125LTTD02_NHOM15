package com.example.quanlyoto.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.widget.TextView;
import com.example.quanlyoto.fragment.AppointmentPeriodActivity;
import com.example.quanlyoto.fragment.AppointmentDesActivity;


import com.example.quanlyoto.R;

public class AppointmentFixActivity extends Fragment {

    private CardView cardBaoDuong, cardSuaChua;
    private String selectedService = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_fix, container, false);

        // Khởi tạo CardViews
        cardBaoDuong = view.findViewById(R.id.cardBaoDuong);
        cardSuaChua = view.findViewById(R.id.cardSuaChua);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);

        // Nút Back
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });
        tvCancel.setOnClickListener(v -> {
            // Chuyển về HomeFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Detail_Fragment())
                    .commit();
        });

        // Click vào card Bảo dưỡng
        cardBaoDuong.setOnClickListener(v -> {
            selectCard(cardBaoDuong);
            deselectCard(cardSuaChua);
            selectedService = "baoDuong";
        });

        // Click vào card Sửa chữa
        cardSuaChua.setOnClickListener(v -> {
            selectCard(cardSuaChua);
            deselectCard(cardBaoDuong);
            selectedService = "suaChua";
        });

        // Nút Next
        Button btnNext = view.findViewById(R.id.button);
        btnNext.setOnClickListener(v -> {
            if (!selectedService.isEmpty()) {

                Fragment nextFragment;

                if (selectedService.equals("baoDuong")) {
                    nextFragment = new AppointmentPeriodActivity(); // → chuyển đến lịch theo kỳ
                } else {
                    nextFragment = new AppointmentDesActivity(); // → chuyển đến mô tả sửa chữa
                }

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    // Đổi viền đỏ khi chọn
    private void selectCard(CardView card) {
        card.setCardBackgroundColor(Color.parseColor("#DDDDDD")); // Nền hồng nhạt
        card.setCardElevation(16f); // Nổi lên
    }

    // Xóa viền khi bỏ chọn
    private void deselectCard(CardView card) {
        card.setCardBackgroundColor(Color.parseColor("#F8F8F8")); // Nền xám
        card.setCardElevation(8f); // Bình thường
    }
}