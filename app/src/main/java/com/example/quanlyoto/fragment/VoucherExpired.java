package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.Voucher;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherExpired extends Fragment {

    private TextView tabActive, tabUsed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voucher_expired, container, false);

        tabActive = view.findViewById(R.id.tab_active); // "Còn hiệu lực"
        tabUsed = view.findViewById(R.id.tab_used); // "Đã sử dụng"

        // Click vào tab "Còn hiệu lực" → chuyển sang VoucherStillValidFragment
        tabActive.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .addToBackStack(null) // nếu muốn back quay lại VoucherExpired
                    .commit();
        });

        // Optional: tabUsed đang ở fragment này, không cần click
        tabUsed.setOnClickListener(v -> {
            /* Không làm gì */ });

        ImageView btnBack = view.findViewById(R.id.btn_back);

        // Quay lại Welcome
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // ================= XỬ LÝ HIỂN THỊ VOUCHER (ĐÃ SỬ DỤNG / HẾT HẠN)
        // =================
        LinearLayout voucherContainer = view.findViewById(R.id.voucher_container);

        ApiService apiService = RetrofitClient.getApiService();

        apiService.getVoucherByUser(1).enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher> vouchers = response.body();

                    if (getContext() == null || voucherContainer == null)
                        return;

                    // Xóa các view cũ
                    voucherContainer.removeAllViews();

                    for (Voucher v : vouchers) {
                        try {
                            // Lọc: KHÔNG PHẢI 'Còn hiệu lực' (tức là đã dùng hoặc hết hạn)
                            if ("Còn hiệu lực".equalsIgnoreCase(v.getTrangThai())) {
                                continue;
                            }

                            // --- CODE TẠO VIEW ---
                            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(
                                    getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 0, 0, 24);
                            card.setLayoutParams(params);
                            card.setRadius(32f);
                            card.setCardElevation(4f); // Thấp hơn chút
                            card.setContentPadding(32, 32, 32, 32);

                            LinearLayout innerLayout = new LinearLayout(getContext());
                            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
                            innerLayout.setGravity(android.view.Gravity.CENTER_VERTICAL);

                            ImageView icon = new ImageView(getContext());
                            icon.setImageResource(R.drawable.car_voucher);
                            icon.setColorFilter(android.graphics.Color.GRAY); // Icon xám
                            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(100, 100);
                            innerLayout.addView(icon, iconParams);

                            LinearLayout textLayout = new LinearLayout(getContext());
                            textLayout.setOrientation(LinearLayout.VERTICAL);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                            textParams.setMargins(32, 0, 0, 0);
                            textLayout.setLayoutParams(textParams);

                            TextView tvName = new TextView(getContext());
                            tvName.setText(v.getLoaiVoucher());
                            tvName.setTextSize(16);
                            tvName.setTypeface(null, android.graphics.Typeface.BOLD);
                            tvName.setTextColor(android.graphics.Color.GRAY); // Text xám
                            textLayout.addView(tvName);

                            TextView tvStatus = new TextView(getContext());
                            tvStatus.setText(
                                    "Trạng thái: " + (v.getTrangThai() != null ? v.getTrangThai() : "Không xác định"));
                            tvStatus.setTextSize(14);
                            tvStatus.setTextColor(android.graphics.Color.RED);
                            textLayout.addView(tvStatus);

                            

                            innerLayout.addView(textLayout);
                            card.addView(innerLayout);
                            voucherContainer.addView(card);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
