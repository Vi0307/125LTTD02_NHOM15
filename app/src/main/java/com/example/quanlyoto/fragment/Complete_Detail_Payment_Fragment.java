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

        return inflater.inflate(R.layout.activity_complete_detail_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //==================== NÚT BACK ====================//
        ImageView icBack = view.findViewById(R.id.ic_back_promotion_applies);
        if (icBack != null) {
            icBack.setOnClickListener(v -> {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Promotion_Applies_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        //==================== NÚT THANH TOÁN ====================//
        Button btnPaymentMethod = view.findViewById(R.id.btn_payment_method);
        if (btnPaymentMethod != null) {
            btnPaymentMethod.setOnClickListener(v -> {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Payment_Method_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
