package com.example.quanlyoto.fragment;

import android.annotation.SuppressLint;
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

    // Khai báo option
    LinearLayout optionFast, optionSave;
    ImageView radioFast, radioSave;

    // 1 = giao nhanh | 2 = giao tiết kiệm
    int selected = 0;

    public Select_Shipping_Method_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_select_shipping_method, container, false);

        // Ánh xạ View
        ImageView ivBack = view.findViewById(R.id.iv_back);
        Button btnPromotion = view.findViewById(R.id.iv_Promotion_applies);

        optionFast = view.findViewById(R.id.linearLayout);        // option 1
        optionSave = view.findViewById(R.id.linearLayout2);       // option 2

        radioFast = view.findViewById(R.id.radio_fast);
        radioSave = view.findViewById(R.id.radio_save);

        // Sự kiện chọn từng option
        optionFast.setOnClickListener(v -> selectFast());
        optionSave.setOnClickListener(v -> selectSave());

        // click icon cũng được
        radioFast.setOnClickListener(v -> selectFast());
        radioSave.setOnClickListener(v -> selectSave());

        // Nút back → chuyển fragment
        ivBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Nút Áp dụng
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
