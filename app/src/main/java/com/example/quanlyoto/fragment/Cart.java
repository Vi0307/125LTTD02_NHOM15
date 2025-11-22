package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Cart extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cart_screen, container, false);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        Button btnCheckout = view.findViewById(R.id.btnCheckout); // ⭐ ĐÃ CÓ ID NÀY

        // 🔙 Quay lại Bodyparts
        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // ⭐ BẤM "TIẾN HÀNH THANH TOÁN" → sang Detail_Payment_Fragment
        btnCheckout.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Detail_Payment_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
