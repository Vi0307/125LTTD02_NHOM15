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
            if (tvServiceType != null) {
                if (serviceDetail != null && !serviceDetail.isEmpty()) {
                    tvServiceType.setText(serviceDetail);
                } else if (serviceType != null) {
                    tvServiceType.setText(serviceType);
                }
            }

            // Cập nhật ngày giờ
            TextView tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
            if (tvSelectedDate != null && date != null) {
                tvSelectedDate.setText(date);
            }
            TextView tvSelectedTime = view.findViewById(R.id.tvSelectedTime);
            if (tvSelectedTime != null && time != null) {
                tvSelectedTime.setText(time);
            }

            // Get agency_id, default to 1 if invalid
            int passedId = getArguments().getInt("agency_id", -1);
            if (passedId != -1) {
                dealerId = passedId;
                // Fetch dealer info
                com.example.quanlyoto.network.RetrofitClient.getClient()
                        .create(com.example.quanlyoto.network.ApiService.class)
                        .getDaiLyById(dealerId)
                        .enqueue(
                                new retrofit2.Callback<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DaiLy>>() {
                                    @Override
                                    public void onResponse(
                                            retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DaiLy>> call,
                                            retrofit2.Response<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DaiLy>> response) {
                                        if (response.isSuccessful() && response.body() != null
                                                && response.body().getData() != null) {
                                            com.example.quanlyoto.model.DaiLy daiLy = response.body().getData();
                                            TextView tvDealerName = view.findViewById(R.id.tvDealerName);
                                            if (tvDealerName != null) {
                                                tvDealerName.setText(daiLy.getTenDaiLy());
                                            }
                                            // Update other dealer fields if needed (address, etc.)
                                        }
                                    }

                                    @Override
                                    public void onFailure(
                                            retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DaiLy>> call,
                                            Throwable t) {
                                        // Ignore or log
                                    }
                                });
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
                // Xử lý format thời gian: HH:mm-HH:mm -> lấy giờ bắt đầu
                String timeStart = time;
                if (time != null && time.contains("-")) {
                    timeStart = time.split("-")[0].trim();
                    combined = date + " " + timeStart;
                }

                java.text.SimpleDateFormat inputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm",
                        java.util.Locale.getDefault());
                java.util.Date parsed = inputFormat.parse(combined);
                java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                        java.util.Locale.getDefault());
                dateTimeIso = outputFormat.format(parsed);
            } catch (Exception e) {
                e.printStackTrace();
                dateTimeIso = date + "T" + time + ":00"; // Fallback
            }

            // Use full package name if imports are missing, or rely on imports if added
            com.example.quanlyoto.model.DichVuDTO dto = new com.example.quanlyoto.model.DichVuDTO(
                    userId,
                    serviceType,
                    serviceDetail,
                    dealerId,
                    dateTimeIso);

            // Call API using RetrofitClient
            com.example.quanlyoto.network.ApiService apiService = com.example.quanlyoto.network.RetrofitClient
                    .getClient()
                    .create(com.example.quanlyoto.network.ApiService.class);

            apiService.createDichVu(dto)
                    .enqueue(
                            new retrofit2.Callback<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DichVuDTO>>() {
                                @Override
                                public void onResponse(
                                        retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DichVuDTO>> call,
                                        retrofit2.Response<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.DichVuDTO>> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        // Đặt lịch thành công -> Cập nhật số lần bảo dưỡng
                                        // Chỉ cập nhật nếu là bảo dưỡng định kỳ (dựa vào serviceDetail hoặc logic khác)
                                        // Hoặc user yêu cầu luôn cập nhật.

                                        // Lấy số lần bảo dưỡng hiện tại từ Arguments (đã được truyền từ Activity trước)
                                        // Hoặc fetch lại user. Để đơn giản và chính xác nhất, fetch user hiện tại, +1,
                                        // rồi update.
                                        // Tuy nhiên, Activity trước đã tính toán rồi? Kiểm tra lại
                                        // AppointmentPeriodActivity.

                                        // Cách tốt nhất: Gọi API updateMaintenanceCount với logic:
                                        // Backend nên handle việc +1, nhưng API thiết kế là update(count).
                                        // Vậy ta cần biết count hiện tại.
                                        // Giả sử count mới đã được gởi sang đây qua Bundle?
                                        int nextCount = getArguments() != null
                                                ? getArguments().getInt("nextMaintenanceCount", -1)
                                                : -1;

                                        if (nextCount != -1) {
                                            apiService.updateMaintenanceCount(userId, nextCount).enqueue(
                                                    new retrofit2.Callback<com.example.quanlyoto.model.NguoiDung>() {
                                                        @Override
                                                        public void onResponse(
                                                                retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call,
                                                                retrofit2.Response<com.example.quanlyoto.model.NguoiDung> response) {
                                                            // Updated
                                                        }

                                                        @Override
                                                        public void onFailure(
                                                                retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call,
                                                                Throwable t) {

                                                        }
                                                    });
                                        }

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
