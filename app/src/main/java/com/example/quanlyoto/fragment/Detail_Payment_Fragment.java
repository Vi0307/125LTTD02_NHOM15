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

public class Detail_Payment_Fragment extends Fragment {

    public Detail_Payment_Fragment() {
        // Required empty public constructor
    }

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

        //==================== NÚT CHỈNH SỬA ĐỊA CHỈ ====================//
        ImageView icPen = view.findViewById(R.id.ic_pen);
        if (icPen != null) {
            icPen.setOnClickListener(v -> {
                // Chuyển sang Fragment Select_Billing_Address
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
