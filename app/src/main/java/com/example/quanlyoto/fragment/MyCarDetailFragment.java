package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.MainActivity;
import com.example.quanlyoto.R;

public class MyCarDetailFragment extends Fragment {

    private LinearLayout layoutHistoryDetail;
    private ImageButton btnExpandHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycar_main_detail, container, false);

        // =================== ÁNH XẠ ===================
        layoutHistoryDetail = view.findViewById(R.id.layoutHistoryDetail);
        btnExpandHistory = view.findViewById(R.id.btn_expand_history);

        // =================== NÚT BACK ===================
        view.findViewById(R.id.btn_back).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarFragment())
                        .commit()
        );

        // =================== EXPAND LỊCH SỬ ===================
        btnExpandHistory.setOnClickListener(v -> toggleHistory());

        // =================== BOTTOM NAV ===================
        setupBottomNav(view);

        return view;
    }

    // ======================================================
    //  HÀM XỬ LÝ MỞ / ĐÓNG LỊCH SỬ BẢO DƯỠNG
    // ======================================================
    private void toggleHistory() {
        if (layoutHistoryDetail.getVisibility() == View.GONE) {
            layoutHistoryDetail.setVisibility(View.VISIBLE);
            btnExpandHistory.setImageResource(R.drawable.ic_collapse);
        } else {
            layoutHistoryDetail.setVisibility(View.GONE);
            btnExpandHistory.setImageResource(R.drawable.ic_expand);
        }
    }

    // ======================================================
    //  BOTTOM NAV (HOME – CAR – VOUCHER – PARTS – AGENCY)
    // ======================================================
    private void setupBottomNav(View view) {

        // Trang chủ
        view.findViewById(R.id.navHome).setOnClickListener(v ->
                navigate(new HomeFragment())
        );

    }

    // ======================================================
    //  CHUYỂN FRAGMENT
    // ======================================================
    private void navigate(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
