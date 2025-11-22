package com.example.quanlyoto.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.Payment_Method;
import com.example.quanlyoto.R;
import com.example.quanlyoto.Select_Billing_Address;

public class Detail_Payment_Fragment extends Fragment {

    public Detail_Payment_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detail_payment, container, false);

        ImageView icPen = view.findViewById(R.id.ic_pen);

        icPen.setOnClickListener(v -> {
            // Chuyển sang Fragment Select_Billing_Address
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Chuyẻn sang phương thức vận chuyển
        view.findViewById(R.id.ic_shipping_method).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Shipping_Method_Fragment())
                    .commit();
        });

        // Chuyẻn sang áp dụng khuyến mãi
        view.findViewById(R.id.ic_payment_method).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Payment_Method_Fragment())
                    .commit();
        });

        // Chuyẻn sang trang tieép theo
        view.findViewById(R.id.btn_billing_address).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                    .commit();
        });

        return view;
    }
}
