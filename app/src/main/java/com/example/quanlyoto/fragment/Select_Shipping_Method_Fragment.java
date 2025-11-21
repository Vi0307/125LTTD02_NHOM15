package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Select_Shipping_Method_Fragment extends Fragment {

    public Select_Shipping_Method_Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_select_shipping_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //==================== NÚT BACK ====================//
        ImageView ivBack = view.findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        //==================== NÚT ÁP DỤNG ====================//
        Button btnPromotion = view.findViewById(R.id.iv_Promotion_applies);
        if (btnPromotion != null) {
            btnPromotion.setOnClickListener(v -> {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Promotion_Applies_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}

