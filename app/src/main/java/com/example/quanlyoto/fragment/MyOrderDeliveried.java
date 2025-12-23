package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class MyOrderDeliveried extends Fragment {

    private TextView tabDelivering;
    private View tabIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_myorder_deliveried, container, false);

        tabDelivering = view.findViewById(R.id.tab_delivering);
        tabIndicator = view.findViewById(R.id.tab_indicator);

        // Click tab "Đã giao" → chuyển sang fragment MyOrderDeliveriedFragment
        tabDelivering.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MyOrderDelivering())
                    // .addToBackStack(null) // KHÔNG add vào stack để tránh loop
                    .commit();
        });

        androidx.cardview.widget.CardView cardDelivered = view.findViewById(R.id.card_product_delivered);
        if (cardDelivered != null) {
            cardDelivered.setOnClickListener(v -> {
                // Chuyển sang fragment OrderDetailFragment
                Order_Detail_Fragment fragment = new Order_Detail_Fragment();
                Bundle args = new Bundle();
                args.putString("orderId", "DH001"); // Sample ID
                fragment.setArguments(args);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        ImageView btnBack = view.findViewById(R.id.btn_back);

        // Quay lại trang trước (pop stack)
        btnBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }
}
