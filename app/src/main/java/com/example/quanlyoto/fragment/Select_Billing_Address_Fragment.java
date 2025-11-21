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

public class Select_Billing_Address_Fragment extends Fragment {

    public Select_Billing_Address_Fragment() {
        // Constructor rỗng bắt buộc
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_select_billing_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //======================== NÚT BACK ========================//
        ImageView ivBack = view.findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> {

                Fragment backFragment = new Detail_Payment_Fragment();

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, backFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        //======================== NÚT ÁP DỤNG ========================//
        Button btnApply = view.findViewById(R.id.btn_apply_address);
        if (btnApply != null) {
            btnApply.setOnClickListener(v -> {

                Fragment shippingMethodFragment = new Select_Shipping_Method_Fragment();

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, shippingMethodFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

}

