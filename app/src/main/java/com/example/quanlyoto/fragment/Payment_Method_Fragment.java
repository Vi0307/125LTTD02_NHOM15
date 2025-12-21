package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Payment_Method_Fragment extends Fragment {

    private ImageView radioCash, radioApple, radioBank;

    public Payment_Method_Fragment() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_payment_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // HEADER
        ImageView icBack = view.findViewById(R.id.ic_back_complete_payment);
        Button btnApply = view.findViewById(R.id.btn_apply);

        // RADIO ICONS
        radioCash = view.findViewById(R.id.radio_cash);
        radioApple = view.findViewById(R.id.radio_apple);
        radioBank = view.findViewById(R.id.radio_bank);

        // LAYOUTS CLICKABLE
        LinearLayout layoutCash = view.findViewById(R.id.layout_cash);
        LinearLayout layoutApple = view.findViewById(R.id.layout_applepay);
        LinearLayout layoutBank = view.findViewById(R.id.layout_bank);

        // ------- HANDLE RADIO SELECT -------
        layoutCash.setOnClickListener(v -> selectMethod("cash"));
        layoutApple.setOnClickListener(v -> selectMethod("apple"));
        layoutBank.setOnClickListener(v -> selectMethod("bank"));

        // Back - quay về trang đơn hàng
        icBack.setOnClickListener(v -> {
            Detail_Payment_Fragment fragment = new Detail_Payment_Fragment();
            if (getArguments() != null) {
                fragment.setArguments(getArguments());
            }
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Apply → chuyển Orderconfirm
        btnApply.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Orderconfirm())
                    .addToBackStack(null)
                    .commit();
        });
    }

    // Chỉ chọn 1 radio
    private void selectMethod(String type) {

        radioCash.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioApple.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioBank.setImageResource(R.drawable.ic_radio_button_unchecked);

        switch (type) {
            case "cash":
                radioCash.setImageResource(R.drawable.ic_radio_button);
                break;
            case "apple":
                radioApple.setImageResource(R.drawable.ic_radio_button);
                break;
            case "bank":
                radioBank.setImageResource(R.drawable.ic_radio_button);
                break;
        }
    }
}
