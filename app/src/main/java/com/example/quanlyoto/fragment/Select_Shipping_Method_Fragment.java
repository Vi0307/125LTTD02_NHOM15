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

        View view = inflater.inflate(R.layout.activity_select_shipping_method, container, false);

        ImageView ivBack = view.findViewById(R.id.iv_back);
        Button btnPromotion = view.findViewById(R.id.iv_Promotion_applies);

        // Chuyển sang fragment Select_Billing_Address
        ivBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Chuyển sang fragment Promotion_Applies
        btnPromotion.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Promotion_Applies_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}

