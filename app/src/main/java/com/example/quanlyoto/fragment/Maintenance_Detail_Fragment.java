package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.ChiTietLichSuBaoDuong;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.network.RetrofitClient;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Maintenance_Detail_Fragment extends Fragment {

    private static final String TAG = "MaintenanceDetail";

    private TextView tvShopName, valueDate, valueKm, tvTotalValue;
    private LinearLayout containerChiTiet;

    private Integer maLSBD;
    private Integer maDaiLy;
    private String dealerName;
    private String date;
    private String km;

    public Maintenance_Detail_Fragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maintenance_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ views
        tvShopName = view.findViewById(R.id.tvShopName);
        valueDate = view.findViewById(R.id.valueDate);
        valueKm = view.findViewById(R.id.valueKm);
        tvTotalValue = view.findViewById(R.id.tvTotalValue);
        containerChiTiet = view.findViewById(R.id.containerChiTiet);

        // ==================== XỬ LÝ NÚT BACK ====================//
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        }

        // BOTTOM NAV
        setupBottomNav(view);

        // ==================== NHẬN DỮ LIỆU TỪ FRAGMENT TRƯỚC ====================//
        if (getArguments() != null) {
            dealerName = getArguments().getString("dealer", "N/A");
            date = getArguments().getString("date", "N/A");
            km = getArguments().getString("km", "N/A");
            maLSBD = getArguments().getInt("maLSBD", -1);
            maDaiLy = getArguments().getInt("maDaiLy", -1);

            // Hiển thị thông tin cơ bản
            if (tvShopName != null)
                tvShopName.setText(dealerName);
            if (valueDate != null)
                valueDate.setText(date);
            if (valueKm != null)
                valueKm.setText(km);

            // Load chi tiết từ API
            if (maLSBD != -1) {
                loadChiTietBaoDuong(maLSBD);
            }
        }

        // ==================== Agency detail ====================//
        View imgArrow = view.findViewById(R.id.imgArrow);
        if (imgArrow != null) {
            imgArrow.setOnClickListener(v -> navigateToAgencyDetail());
        }
    }

    /**
     * Navigate to Agency Detail with data from API
     */
    private void navigateToAgencyDetail() {
        if (maDaiLy == null || maDaiLy == -1) {
            // Fallback: just pass dealer name
            Agency_Detail_Fragment fragment = new Agency_Detail_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("agency_name", dealerName);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return;
        }

        // Gọi API lấy thông tin đại lý
        RetrofitClient.getApiService().getDaiLyById(maDaiLy).enqueue(new Callback<ApiResponse<DaiLy>>() {
            @Override
            public void onResponse(Call<ApiResponse<DaiLy>> call, Response<ApiResponse<DaiLy>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<DaiLy> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        DaiLy daiLy = apiResponse.getData();

                        Agency_Detail_Fragment fragment = new Agency_Detail_Fragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("agency_id", daiLy.getMaDaiLy());
                        bundle.putString("agency_name", daiLy.getTenDaiLy());
                        bundle.putString("agency_address", daiLy.getDiaChi());
                        bundle.putString("agency_phone", daiLy.getSoDienThoai());
                        bundle.putString("agency_hours", daiLy.getGioLamViec());
                        bundle.putString("agency_description", daiLy.getMoTa());
                        fragment.setArguments(bundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DaiLy>> call, Throwable t) {
                Log.e(TAG, "Error loading dai ly: " + t.getMessage());
            }
        });
    }

    /**
     * Load chi tiết lịch sử bảo dưỡng từ API
     */
    private void loadChiTietBaoDuong(Integer maLSBD) {
        RetrofitClient.getApiService().getChiTietLichSuBaoDuong(maLSBD)
                .enqueue(new Callback<ApiResponse<List<ChiTietLichSuBaoDuong>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<ChiTietLichSuBaoDuong>>> call,
                            Response<ApiResponse<List<ChiTietLichSuBaoDuong>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<List<ChiTietLichSuBaoDuong>> apiResponse = response.body();
                            if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                                List<ChiTietLichSuBaoDuong> chiTietList = apiResponse.getData();
                                updateChiTietUI(chiTietList);
                                Log.d(TAG, "Loaded " + chiTietList.size() + " chi tiết");
                            }
                        } else {
                            Log.e(TAG, "Error loading chi tiet: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<ChiTietLichSuBaoDuong>>> call, Throwable t) {
                        Log.e(TAG, "Error loading chi tiet: " + t.getMessage());
                    }
                });
    }

    /**
     * Cập nhật UI với danh sách chi tiết
     */
    private void updateChiTietUI(List<ChiTietLichSuBaoDuong> chiTietList) {
        if (containerChiTiet == null || getContext() == null)
            return;

        // Xóa items cũ nếu có
        containerChiTiet.removeAllViews();

        // Tính tổng tiền
        BigDecimal tongTien = BigDecimal.ZERO;
        DecimalFormat df = new DecimalFormat("#,###");

        // Thêm các chi tiết từ API
        for (int i = 0; i < chiTietList.size(); i++) {
            ChiTietLichSuBaoDuong ct = chiTietList.get(i);
            addChiTietItem(ct.getNoiDung(), ct.getChiPhi(), df);

            if (ct.getChiPhi() != null) {
                tongTien = tongTien.add(ct.getChiPhi());
            }

            // Thêm divider nếu không phải item cuối
            if (i < chiTietList.size() - 1) {
                addDivider();
            }
        }

        // Cập nhật tổng tiền
        if (tvTotalValue != null) {
            tvTotalValue.setText(df.format(tongTien));
        }
    }

    /**
     * Thêm một chi tiết item vào container
     */
    private void addChiTietItem(String noiDung, BigDecimal chiPhi, DecimalFormat df) {
        if (getContext() == null)
            return;

        // Tạo LinearLayout cho item
        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(0, 24, 0, 24);

        // Nội dung công việc
        TextView tvNoiDung = new TextView(getContext());
        LinearLayout.LayoutParams ndParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        tvNoiDung.setLayoutParams(ndParams);
        tvNoiDung.setText(noiDung != null ? noiDung : "N/A");
        tvNoiDung.setTextSize(14);
        tvNoiDung.setTextColor(0xFF000000);

        // Chi phí
        TextView tvChiPhi = new TextView(getContext());
        tvChiPhi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tvChiPhi.setText(chiPhi != null ? df.format(chiPhi) : "0");
        tvChiPhi.setTextSize(14);
        tvChiPhi.setTextColor(0xFF000000);

        itemLayout.addView(tvNoiDung);
        itemLayout.addView(tvChiPhi);

        containerChiTiet.addView(itemLayout);
    }

    /**
     * Thêm divider giữa các items
     */
    private void addDivider() {
        if (getContext() == null)
            return;

        View divider = new View(getContext());
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 2));
        divider.setBackgroundColor(0xFFF3F3F3);
        containerChiTiet.addView(divider);
    }

    /**
     * Setup Bottom Navigation
     */
    private void setupBottomNav(View view) {
        view.findViewById(R.id.navHome).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        view.findViewById(R.id.navParts).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homeparts())
                    .commit();
        });

        view.findViewById(R.id.navVoucher).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .commit();
        });

        view.findViewById(R.id.navAgency).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Fragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
