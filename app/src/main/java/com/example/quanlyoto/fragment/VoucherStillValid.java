package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class VoucherStillValid extends Fragment {

    private TextView tabActive, tabUsed;
    private View tabIndicator;
    private Button btnJoinGame;
    private ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voucher_still_valid, container, false);

        // Ánh xạ view
        tabActive = view.findViewById(R.id.tab_active);
        tabUsed = view.findViewById(R.id.tab_used);
        tabIndicator = view.findViewById(R.id.tab_indicator);
        btnJoinGame = view.findViewById(R.id.btn_join_game);
        btnBack = view.findViewById(R.id.btn_back);

        // Nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        // Tab "Đã sử dụng"
        tabUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VoucherExpired())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Test xem nút có hoạt động không


                // Sau đó mới chuyển fragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PlayHome())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}