package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
            AppointmentCheckActivity nextFragment = new AppointmentCheckActivity();
            Bundle args = new Bundle();
            if (getArguments() != null) {
                args.putString("selectedDate", getArguments().getString("selectedDate"));
                args.putString("selectedTime", getArguments().getString("selectedTime"));
                args.putString("serviceType", getArguments().getString("serviceType"));
                args.putInt("agency_id", getArguments().getInt("agency_id", -1));
            }
            // For now, hardcode or grab value. Ideally find the selected radio button.
            // Since there is only one in the current xml shown:
            args.putString("serviceDetail", "KTDK lần 5");

            nextFragment.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, nextFragment)
                    .addToBackStack(null) // để back được
                    .commit();
        });
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(v -> {
            // Chuyển về HomeFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Detail_Fragment())
                    .commit();
        });

        return view;
    }
}
