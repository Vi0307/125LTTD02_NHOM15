package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button btnViewHistoryDetail;
    private Button btnThayPhuTung;
    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycar_main_detail, container, false);

        // ScrollView
        scrollView = view.findViewById(R.id.scrollView);

        // Layout các phần
        layoutHistoryDetail = view.findViewById(R.id.layoutHistoryDetail);
        LinearLayout layoutNhacNhoPhuTung = view.findViewById(R.id.layoutNhacNhoPhuTung);

        // Nút expand lịch sử
        btnExpandHistory = view.findViewById(R.id.btn_expand_history);

        // Nút xem chi tiết lịch sử (dưới cùng sau khi expand)
        btnViewHistoryDetail = view.findViewById(R.id.btnViewHistoryDetail); // nhớ đặt id trong XML

        // Nút thay thế phụ tùng
        btnThayPhuTung = view.findViewById(R.id.btnThayPhuTung);

        // Nút đặt dịch vụ
        View btnDatDichVu1 = view.findViewById(R.id.btnDatDichVu1);
        View btnDatDichVu2 = view.findViewById(R.id.btnDatDichVu);

        // NÚT BACK
        view.findViewById(R.id.btn_back).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarFragment())
                        .commit()
        );

        // EXPAND LỊCH SỬ
        btnExpandHistory.setOnClickListener(v -> toggleHistory());

        // NÚT XEM CHI TIẾT LỊCH SỬ
        if (btnViewHistoryDetail != null) {
            btnViewHistoryDetail.setOnClickListener(v ->
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Maintenance_History_Fragment())
                            .addToBackStack(null)
                            .commit()
            );
        }

        // NÚT THAY THẾ PHỤ TÙNG → scroll xuống layoutNhacNhoPhuTung
        if (btnThayPhuTung != null && layoutNhacNhoPhuTung != null && scrollView != null) {
            btnThayPhuTung.setOnClickListener(v ->
                    scrollView.post(() ->
                            scrollView.smoothScrollTo(0, layoutNhacNhoPhuTung.getTop())
                    )
            );
        }

        // NÚT ĐẶT DỊCH VỤ
        View.OnClickListener goToAgency = v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new Agency_Fragment())
                .addToBackStack(null)
                .commit();

        if (btnDatDichVu1 != null) btnDatDichVu1.setOnClickListener(goToAgency);
        if (btnDatDichVu2 != null) btnDatDichVu2.setOnClickListener(goToAgency);

        return view;
    }

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
