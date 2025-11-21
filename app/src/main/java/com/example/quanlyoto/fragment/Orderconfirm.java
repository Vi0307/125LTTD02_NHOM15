package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;      // ⭐ THÊM
import android.widget.ImageView;  // ⭐ THÊM

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Orderconfirm extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.orderconfirm_screen, container, false);

        Button btnComplete = view.findViewById(R.id.btnComplete);
        ImageView btnBack = view.findViewById(R.id.btnBack);

        // ⭐ BACK → quay lại Payment_Method_Fragment
        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // ⭐ COMPLETE → sang Ordersuccess
        btnComplete.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Ordersuccess())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
