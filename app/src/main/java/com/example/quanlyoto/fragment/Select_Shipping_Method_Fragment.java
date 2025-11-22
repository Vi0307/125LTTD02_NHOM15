package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Select_Shipping_Method_Fragment extends Fragment {

    LinearLayout optionFast, optionSave;
    ImageView radioFast, radioSave;

    int selected = 0;

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

        optionFast = view.findViewById(R.id.linearLayout);
        optionSave = view.findViewById(R.id.linearLayout2);

        radioFast = view.findViewById(R.id.radio_fast);
        radioSave = view.findViewById(R.id.radio_save);

        // Chọn giao nhanh
        optionFast.setOnClickListener(v -> selectFast());
        radioFast.setOnClickListener(v -> selectFast());

        // Chọn giao tiết kiệm
        optionSave.setOnClickListener(v -> selectSave());
        radioSave.setOnClickListener(v -> selectSave());

        // Quay lại trang trước
        ivBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Chuyển sang màn Promotion Applies
        btnPromotion.setOnClickListener(v -> {
            if (selected == 0) {
                Toast.makeText(getContext(), "Vui lòng chọn phương thức vận chuyển", Toast.LENGTH_SHORT).show();
                return;
            }

            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Promotion_Applies_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void selectFast() {
        selected = 1;
        radioFast.setImageResource(R.drawable.ic_radio_button);
        radioSave.setImageResource(R.drawable.ic_radio_button_unchecked);
    }

    private void selectSave() {
        selected = 2;
        radioFast.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioSave.setImageResource(R.drawable.ic_radio_button);
    }
}
