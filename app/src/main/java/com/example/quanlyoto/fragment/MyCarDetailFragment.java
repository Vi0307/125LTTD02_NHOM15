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

        layoutHistoryDetail = view.findViewById(R.id.layoutHistoryDetail);
        btnExpandHistory = view.findViewById(R.id.btn_expand_history);

        // NÚT BACK
        view.findViewById(R.id.btn_back).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarFragment())
                        .commit()
        );

        // EXPAND LỊCH SỬ
        btnExpandHistory.setOnClickListener(v -> toggleHistory());

        // =================== NÚT ĐẶT DỊCH VỤ ===================
        View btnDatDichVu1 = view.findViewById(R.id.btnDatDichVu1);
        View btnDatDichVu2 = view.findViewById(R.id.btnDatDichVu);

        View.OnClickListener goToAngency = v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Fragment())
                    .addToBackStack(null)
                    .commit();
        };

        if (btnDatDichVu1 != null) btnDatDichVu1.setOnClickListener(goToAngency);
        if (btnDatDichVu2 != null) btnDatDichVu2.setOnClickListener(goToAngency);

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
