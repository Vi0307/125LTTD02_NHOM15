package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Select_Billing_Address_Fragment extends Fragment {

    // radio icons
    ImageView radioHome, radioOffice;

    // container click
    LinearLayout layoutHome, layoutOffice;

    public Select_Billing_Address_Fragment() {
        // Required empty public constructor
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
                Fragment DetailPaymentFragment = new Detail_Payment_Fragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, DetailPaymentFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        //======================== NÚT Thêm địa chỉ ========================//
        Button btnThemDiaChi = view.findViewById(R.id.btn_themDiaChi);
        if (btnThemDiaChi != null) {
            btnThemDiaChi.setOnClickListener(v -> {
                Fragment addNewAddressFragment = new Add_New_Address_Fragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, addNewAddressFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        //======================== RADIO BUTTON ========================//

        radioHome = view.findViewById(R.id.radio_home);
        radioOffice = view.findViewById(R.id.radio_office);

        layoutHome = view.findViewById(R.id.layout_home);
        layoutOffice = view.findViewById(R.id.layout_office);

        layoutHome.setOnClickListener(v -> selectHome());
        layoutOffice.setOnClickListener(v -> selectOffice());
    }

    private void selectHome() {
        radioHome.setImageResource(R.drawable.ic_radio_button);
        radioOffice.setImageResource(R.drawable.ic_radio_button_unchecked);
    }

    private void selectOffice() {
        radioOffice.setImageResource(R.drawable.ic_radio_button);
        radioHome.setImageResource(R.drawable.ic_radio_button_unchecked);
    }
}
