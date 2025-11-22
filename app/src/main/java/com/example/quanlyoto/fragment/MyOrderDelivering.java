package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class MyOrderDelivering extends Fragment {

    private TextView tabDelivered;
    private View tabIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_myorder_delivering, container, false);

        tabDelivered = view.findViewById(R.id.tab_delivered);
        tabIndicator = view.findViewById(R.id.tab_indicator);

        // Click tab "Đã giao" → chuyển sang fragment MyOrderDeliveriedFragment
        tabDelivered.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MyOrderDeliveried())
                    .addToBackStack(null) // có thể quay lại tab đang giao
                    .commit();
        });



        ImageView btnBack = view.findViewById(R.id.btn_back);

        // Quay lại Welcome
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
