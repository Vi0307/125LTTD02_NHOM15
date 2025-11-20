package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.Select_Billing_Address;

public class Detail_Payment_Fragment extends Fragment {

    public Detail_Payment_Fragment() {
        // Required empty public constructor
    }

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

        return view;
    }
}
