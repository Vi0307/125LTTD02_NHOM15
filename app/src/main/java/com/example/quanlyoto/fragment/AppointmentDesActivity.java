package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class AppointmentDesActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_des, container, false);

        // Nút Back
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            // Quay lại fragment trước
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            }
        });
        Button btnNext = view.findViewById(R.id.button);
        btnNext.setOnClickListener(v -> {
            // Chuyển sang fragment kế tiếp
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AppointmentPeriodActivity())
                    .addToBackStack(null)
                    .commit();
        });


        return view;
    }
}
