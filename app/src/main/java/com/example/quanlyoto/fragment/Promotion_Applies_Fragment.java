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

public class Promotion_Applies_Fragment extends Fragment {

    public Promotion_Applies_Fragment() {
        // Public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_promotion_applies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //==================== NÚT BACK ====================//
        ImageView icBack = view.findViewById(R.id.ic_back);
        if (icBack != null) {
            icBack.setOnClickListener(v -> {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Select_Shipping_Method_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        //==================== NÚT ÁP DỤNG ====================//
        Button btnComplete = view.findViewById(R.id.btn_complete_payment_detail);
        if (btnComplete != null) {
            btnComplete.setOnClickListener(v -> {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Complete_Detail_Payment_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
