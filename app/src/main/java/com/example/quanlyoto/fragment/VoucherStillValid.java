package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class VoucherStillValid extends Fragment {

    private TextView tabActive, tabUsed;
    private View tabIndicator;
    private Button btnJoinGame;
    private ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voucher_still_valid, container, false);

        // Ánh xạ view
        tabActive = view.findViewById(R.id.tab_active);
        tabUsed = view.findViewById(R.id.tab_used);
        tabIndicator = view.findViewById(R.id.tab_indicator);
        btnJoinGame = view.findViewById(R.id.btn_join_game);
        btnBack = view.findViewById(R.id.btn_back);

        // Nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang trước (pop stack), tránh tạo vòng lặp
                getParentFragmentManager().popBackStack();
            }
        });

        // Tab "Đã sử dụng"
        tabUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VoucherExpired())
                        // .addToBackStack(null) // KHÔNG add vào stack khi chuyển tab để tránh loop
                        .commit();
            }
        });

        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PlayHome())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Container chứa danh sách voucher
        LinearLayout voucherContainer = view.findViewById(R.id.voucher_container);

        // Gọi API lấy voucher của user 1
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
                            // Lọc: Chỉ hiện voucher 'Còn hiệu lực'
                            if (!"Còn hiệu lực".equalsIgnoreCase(v.getTrangThai())) {
                                continue;
                            }

                            // Tạo CardView
                            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(
                                    getContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 0, 0, 24);
                            card.setLayoutParams(params);
                            card.setRadius(32f);
                            card.setCardElevation(8f);
                            card.setContentPadding(32, 32, 32, 32);

                            // Layout horizonal bên trong card
                            LinearLayout innerLayout = new LinearLayout(getContext());
                            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
                            innerLayout.setGravity(android.view.Gravity.CENTER_VERTICAL);

                            // Icon
                            ImageView icon = new ImageView(getContext());
                            icon.setImageResource(R.drawable.car_voucher);
                            icon.setColorFilter(android.graphics.Color.BLACK);
                            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(100, 100);
                            innerLayout.addView(icon, iconParams);

                            // Text Layout
                            LinearLayout textLayout = new LinearLayout(getContext());
                            textLayout.setOrientation(LinearLayout.VERTICAL);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                            textParams.setMargins(32, 0, 0, 0);
                            textLayout.setLayoutParams(textParams);

                            // Voucher Name
                            TextView tvName = new TextView(getContext());
                            tvName.setText(v.getLoaiVoucher());
                            tvName.setTextSize(16);
                            tvName.setTypeface(null, android.graphics.Typeface.BOLD);
                            tvName.setTextColor(android.graphics.Color.BLACK);
                            textLayout.addView(tvName);

                            // Expiry Date
                            TextView tvDate = new TextView(getContext());
                            tvDate.setText("HSD: " + v.getHanSuDung());
                            tvDate.setTextSize(14);
                            tvDate.setTextColor(android.graphics.Color.DKGRAY);
                            textLayout.addView(tvDate);

                            innerLayout.addView(textLayout);
                            card.addView(innerLayout);

                            // Thêm card vào container
                            voucherContainer.addView(card);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (getContext() != null)
                        Toast.makeText(getContext(), "Không lấy được dữ liệu voucher", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}