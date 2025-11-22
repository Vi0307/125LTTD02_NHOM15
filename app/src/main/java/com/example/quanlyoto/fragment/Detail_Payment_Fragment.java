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
import com.example.quanlyoto.Promotion_Applies;
import com.example.quanlyoto.R;

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

        return inflater.inflate(R.layout.activity_detail_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ==================== NÚT BACK ==================== //
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v ->
                    requireActivity()
                            .getSupportFragmentManager()
                            .popBackStack() 
            );
        }

        // ==================== NÚT CHỈNH SỬA ĐỊA CHỈ ==================== //
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
                    .replace(R.id.fragment_container, new Promotion_Applies_Fragment())
                    .commit();
        });

        // Chuyẻn sang trang tiếp theo
        view.findViewById(R.id.btn_billing_address).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                    .commit();
        });

        return view;
        if (icPen != null) {
            icPen.setOnClickListener(v -> {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Select_Billing_Address_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}