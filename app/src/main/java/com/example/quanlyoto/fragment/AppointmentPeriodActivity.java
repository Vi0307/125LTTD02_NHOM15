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

        int userId = 1; // Hardcoded user
        final int[] currentMaintenanceCount = { 0 };
        android.widget.RadioButton rbPeriod = view.findViewById(R.id.rbPeriod);

        // Fetch User Info to get maintenance count
        com.example.quanlyoto.network.RetrofitClient.getClient()
                .create(com.example.quanlyoto.network.ApiService.class)
                .getNguoiDungById(userId)
                .enqueue(new retrofit2.Callback<com.example.quanlyoto.model.NguoiDung>() {
                    @Override
                    public void onResponse(retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call,
                            retrofit2.Response<com.example.quanlyoto.model.NguoiDung> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getSoLanBaoDuong() != null) {
                                currentMaintenanceCount[0] = response.body().getSoLanBaoDuong();
                            } else {
                                currentMaintenanceCount[0] = 0;
                            }
                            int nextCount = currentMaintenanceCount[0] + 1;
                            if (rbPeriod != null) {
                                rbPeriod.setText("KTDK lần " + nextCount);
                                rbPeriod.setChecked(true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call, Throwable t) {
                        // Fail silently or toast
                    }
                });

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

            // Logic: ktdk = so lan bao duong + 1
            int nextCount = currentMaintenanceCount[0] + 1;
            String serviceDetail = "KTDK lần " + nextCount;

            args.putString("serviceDetail", serviceDetail);
            args.putInt("nextMaintenanceCount", nextCount);

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
