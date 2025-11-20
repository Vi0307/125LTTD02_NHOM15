package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class AppointmentPeriodActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_period, container, false);

        // Nút back
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            // Quay về fragment trước
            getParentFragmentManager().popBackStack();
        });

        // Nút tiếp → chuyển sang fragment check
        Button btnNext = view.findViewById(R.id.button);
        btnNext.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AppointmentCheckActivity())
                    .addToBackStack(null) // để back được
                    .commit();
        });

        return view;
    }
}
