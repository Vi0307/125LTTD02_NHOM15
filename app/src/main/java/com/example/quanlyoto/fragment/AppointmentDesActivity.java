package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.widget.TextView;
import com.example.quanlyoto.R;

public class AppointmentDesActivity extends Fragment {

    private android.widget.EditText etDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_des, container, false);

        etDescription = view.findViewById(R.id.et_description);

        // Nút Hủy
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(v -> {
            // Chuyển về HomeFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Detail_Fragment())
                    .commit();
        });

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
            AppointmentCheckActivity nextFragment = new AppointmentCheckActivity();
            Bundle args = new Bundle();
            if (getArguments() != null) {
                args.putString("selectedDate", getArguments().getString("selectedDate"));
                args.putString("selectedTime", getArguments().getString("selectedTime"));
                args.putString("serviceType", getArguments().getString("serviceType"));
                args.putInt("agency_id", getArguments().getInt("agency_id", -1));
            }
            String description = "";
            if (etDescription != null) {
                description = etDescription.getText().toString();
            }
            args.putString("serviceDetail", description);

            nextFragment.setArguments(args);

            // Chuyển sang fragment kế tiếp
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, nextFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
