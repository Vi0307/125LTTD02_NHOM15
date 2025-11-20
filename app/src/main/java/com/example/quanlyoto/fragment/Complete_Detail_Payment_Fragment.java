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

public class Complete_Detail_Payment_Fragment extends Fragment {

    public Complete_Detail_Payment_Fragment() {
        // Public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_complete_detail_payment, container, false);

        ImageView icBack = view.findViewById(R.id.ic_back_promotion_applies);
        Button btnPaymentMethod = view.findViewById(R.id.btn_payment_method);

        // Quay lại Promotion_Applies_Fragment
        icBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Promotion_Applies_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Chuyển sang Payment_Method_Fragment
        btnPaymentMethod.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Payment_Method_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
