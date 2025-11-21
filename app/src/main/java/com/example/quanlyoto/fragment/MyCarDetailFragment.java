package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        LinearLayout layoutNhacNhoPhuTung = view.findViewById(R.id.layoutNhacNhoPhuTung);

        // =================== NÚT BACK ===================
        view.findViewById(R.id.btn_back).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarFragment())
                        .commit()
        );

        // =================== EXPAND LỊCH SỬ ===================
        btnExpandHistory.setOnClickListener(v -> toggleHistory());

        // =================== NÚT ĐẶT DỊCH VỤ ===================
        View btnDatDichVu1 = view.findViewById(R.id.btnDatDichVu1);
        View btnDatDichVu2 = view.findViewById(R.id.btnDatDichVu);

        View.OnClickListener goToAgency = v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Fragment())
                    .addToBackStack(null)
                    .commit();
        };

        if (btnDatDichVu1 != null) btnDatDichVu1.setOnClickListener(goToAgency);
        if (btnDatDichVu2 != null) btnDatDichVu2.setOnClickListener(goToAgency);

        // =================== CHATBOX FLOATING BUTTON ===================
        View fabChat = view.findViewById(R.id.btnChat);
        if (fabChat != null) {
            fabChat.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // =================== BUTTON THAY THẾ PHỤ TÙNG ===================
        View btnThayPhuTung = view.findViewById(R.id.btnThayPhuTung);
        if (btnThayPhuTung != null && scrollView != null && layoutNhacNhoPhuTung != null) {
            btnThayPhuTung.setOnClickListener(v -> {
                // Scroll smooth xuống layout Nhắc nhở phụ tùng
                scrollView.post(() -> scrollView.smoothScrollTo(0, layoutNhacNhoPhuTung.getTop()));
            });
        }

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
}
