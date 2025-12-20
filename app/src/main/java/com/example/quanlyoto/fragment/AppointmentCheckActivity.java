package com.example.quanlyoto.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.MainActivity;
import com.example.quanlyoto.R;
import androidx.cardview.widget.CardView;

public class AppointmentCheckActivity extends Fragment {

    private Button btnComplete;
    private FrameLayout dialogOverlay;
    private Button btnGoHome;

    private String date = "";
    private String time = "";
    private String serviceType = "";
    private String serviceDetail = "";
    private int dealerId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_check, container, false);

        // Nút back
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Parse arguments immediately
        if (getArguments() != null) {
            date = getArguments().getString("selectedDate");
            time = getArguments().getString("selectedTime");
            serviceType = getArguments().getString("serviceType");
            serviceDetail = getArguments().getString("serviceDetail");

            // Update UI immediately
            TextView tvServiceType = view.findViewById(R.id.tvServiceType);
            if (tvServiceType != null && serviceType != null) {
                tvServiceType.setText(serviceType);
            }

            // Get agency_id, default to 1 if invalid
            int passedId = getArguments().getInt("agency_id", -1);
            if (passedId != -1) {
                dealerId = passedId;
            }
        }

        // Dialog
        btnComplete = view.findViewById(R.id.button);
        dialogOverlay = view.findViewById(R.id.dialogOverlay);
        btnGoHome = view.findViewById(R.id.btnGoHome);

        btnComplete.setOnClickListener(v -> {
            // Create payload
            int userId = 1;
            // dealerId is set above

            String dateTimeIso = "";
            try {
                // Combine and format Date/Time to ISO 8601 for Backend LocalDateTime
                String combined = date + " " + time;
                java.text.SimpleDateFormat inputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm",
                        java.util.Locale.getDefault());
                java.util.Date parsed = inputFormat.parse(combined);
                java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                        java.util.Locale.getDefault());
                dateTimeIso = outputFormat.format(parsed);
            } catch (Exception e) {
                e.printStackTrace();
                dateTimeIso = date + "T" + time + ":00";
            }

            // Use full package name if imports are missing, or rely on imports if added
            com.example.quanlyoto.model.DichVuDTO dto = new com.example.quanlyoto.model.DichVuDTO(
                    userId,
                    serviceType,
                    serviceDetail,
                    dealerId,
                    dateTimeIso);

            // Call API using RetrofitClient
            com.example.quanlyoto.network.RetrofitClient.getClient()
                    .create(com.example.quanlyoto.network.ApiService.class)
                    .createDichVu(dto)
                    .enqueue(
                            new retrofit2.Callback<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DichVuDTO>>() {
                                @Override
                                public void onResponse(
                                        retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DichVuDTO>> call,
                                        retrofit2.Response<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DichVuDTO>> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        dialogOverlay.setVisibility(View.VISIBLE);
                                    } else {
                                        android.widget.Toast
                                                .makeText(getContext(), "Đặt lịch thất bại: " + response.message(),
                                                        android.widget.Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }

                                @Override
                                public void onFailure(
                                        retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DichVuDTO>> call,
                                        Throwable t) {
                                    android.widget.Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(),
                                            android.widget.Toast.LENGTH_SHORT).show();
                                }
                            });
        });
        btnGoHome.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
            }
        });
        ImageView btnEditService = view.findViewById(R.id.btnEditService);

        btnEditService.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AppointmentFixActivity())
                    .addToBackStack(null)
                    .commit();
        });

        // 👉 CLICK VÀO "SỬA CHỮA" NHẢY ĐẾN AppointmentFixFragment
        View cardServiceTime = view.findViewById(R.id.cardServiceTime);
        cardServiceTime.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Booking_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        TextView btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> {
            // Chuyển về HomeFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Detail_Fragment())
                    .commit();
        });

        // Back button xử lý khi dialog mở
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (dialogOverlay != null && dialogOverlay.getVisibility() == View.VISIBLE) {
                            dialogOverlay.setVisibility(View.GONE);
                        } else {
                            getParentFragmentManager().popBackStack();
                        }
                    }
                });

        return view;
    }
}
