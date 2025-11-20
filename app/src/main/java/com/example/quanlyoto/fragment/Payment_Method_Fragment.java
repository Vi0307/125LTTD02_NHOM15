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

public class Payment_Method_Fragment extends Fragment {

    public Payment_Method_Fragment() {
        // Public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_payment_method, container, false);

        ImageView icBackCompletePayment = view.findViewById(R.id.ic_back_complete_payment);

        // Quay lại Complete_Detail_Payment_Fragment
        icBackCompletePayment.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Complete_Detail_Payment_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
