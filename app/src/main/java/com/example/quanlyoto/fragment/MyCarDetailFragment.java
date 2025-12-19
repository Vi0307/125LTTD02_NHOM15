package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.LoaiXe;
import com.example.quanlyoto.model.NguoiDung;
import com.example.quanlyoto.model.Xe;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCarDetailFragment extends Fragment {

    private static final String TAG = "MyCarDetailFragment";

    // Demo user ID - thay đổi theo database của bạn
    private static final int DEMO_USER_ID = 1;

    private LinearLayout layoutHistoryDetail;
    private ImageButton btnExpandHistory;
    private Button btnViewHistoryDetail;
    private Button btnThayPhuTung;
    private ScrollView scrollView;

    // Views cho thông tin xe
    private TextView tvTenXe, tvBienSo, tvMauXe, tvLoaiXe, tvMauSac, tvDungTich, tvSoKhung;

    // Views cho các ô bảo dưỡng
    private TextView tvLan1, tvLan2, tvLan3, tvLan4, tvLan5, tvLan6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycar_main_detail, container, false);

        // Khởi tạo Views
        initViews(view);

        // Load thông tin xe
        loadXeInfo();

        // Load thông tin người dùng (để lấy số lần bảo dưỡng)
        loadUserInfo();

        // ScrollView
        scrollView = view.findViewById(R.id.scrollView);

        // Layout các phần
        layoutHistoryDetail = view.findViewById(R.id.layoutHistoryDetail);
        CardView layoutNhacNhoPhuTung = view.findViewById(R.id.layoutNhacNhoPhuTung);

        // Nút expand lịch sử
        btnExpandHistory = view.findViewById(R.id.btn_expand_history);

        // Nút xem chi tiết lịch sử (dưới cùng sau khi expand)
        btnViewHistoryDetail = view.findViewById(R.id.btnViewHistoryDetail);

        // Nút thay thế phụ tùng
        btnThayPhuTung = view.findViewById(R.id.btnThayPhuTung);

        // Nút đặt dịch vụ
        View btnDatDichVu1 = view.findViewById(R.id.btnDatDichVu1);
        View btnDatDichVu2 = view.findViewById(R.id.btnDatDichVu);

        // NÚT BACK
        view.findViewById(R.id.btn_back).setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MyCarFragment())
                .commit());

        // EXPAND LỊCH SỬ
        btnExpandHistory.setOnClickListener(v -> toggleHistory());

        // NÚT XEM CHI TIẾT LỊCH SỬ
        if (btnViewHistoryDetail != null) {
            btnViewHistoryDetail.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Maintenance_History_Fragment())
                    .addToBackStack(null)
                    .commit());
        }

        // NÚT THAY THẾ PHỤ TÙNG → scroll xuống layoutNhacNhoPhuTung
        if (btnThayPhuTung != null && layoutNhacNhoPhuTung != null && scrollView != null) {
            btnThayPhuTung.setOnClickListener(
                    v -> scrollView.post(() -> scrollView.smoothScrollTo(0, layoutNhacNhoPhuTung.getTop())));
        }

        // NÚT ĐẶT DỊCH VỤ
        View.OnClickListener goToAgency = v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new Agency_Fragment())
                .addToBackStack(null)
                .commit();

        if (btnDatDichVu1 != null)
            btnDatDichVu1.setOnClickListener(goToAgency);
        if (btnDatDichVu2 != null)
            btnDatDichVu2.setOnClickListener(goToAgency);

        // ==========================
        // CHATBOX → ChatFragment
        // ==========================
        View chatBtn = view.findViewById(R.id.btnChat);
        if (chatBtn != null) {
            chatBtn.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())
                        .addToBackStack(null)
                        .commit();
            });
        }
        return view;
    }

    /**
     * Khởi tạo các View
     */
    private void initViews(View view) {
        // Views cho thông tin xe
        tvTenXe = view.findViewById(R.id.tvTenXe);
        tvBienSo = view.findViewById(R.id.tvBienSo);
        tvMauXe = view.findViewById(R.id.tvMauXe);
        tvLoaiXe = view.findViewById(R.id.tvLoaiXe);
        tvMauSac = view.findViewById(R.id.tvMauSac);
        tvDungTich = view.findViewById(R.id.tvDungTich);
        tvSoKhung = view.findViewById(R.id.tvSoKhung);

        // Views cho các ô bảo dưỡng
        tvLan1 = view.findViewById(R.id.tvLan1);
        tvLan2 = view.findViewById(R.id.tvLan2);
        tvLan3 = view.findViewById(R.id.tvLan3);
        tvLan4 = view.findViewById(R.id.tvLan4);
        tvLan5 = view.findViewById(R.id.tvLan5);
        tvLan6 = view.findViewById(R.id.tvLan6);
    }

    /**
     * Load thông tin người dùng để lấy số lần bảo dưỡng
     */
    private void loadUserInfo() {
        RetrofitClient.getApiService().getNguoiDungById(DEMO_USER_ID).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NguoiDung user = response.body();
                    Integer soLanBaoDuong = user.getSoLanBaoDuong();
                    if (soLanBaoDuong != null) {
                        updateBaoDuongUI(soLanBaoDuong);
                        Log.d(TAG, "Số lần bảo dưỡng: " + soLanBaoDuong);
                    }
                } else {
                    Log.e(TAG, "Error loading user: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Log.e(TAG, "Error loading user: " + t.getMessage());
            }
        });
    }

    /**
     * Cập nhật UI các ô bảo dưỡng theo số lần đã bảo dưỡng
     * - Xanh lá (progress_green): đã hoàn thành
     * - Xám (progress_gray): chưa đến
     */
    private void updateBaoDuongUI(int soLanBaoDuong) {
        TextView[] lanViews = { tvLan1, tvLan2, tvLan3, tvLan4, tvLan5, tvLan6 };

        for (int i = 0; i < lanViews.length; i++) {
            if (lanViews[i] != null) {
                if (i < soLanBaoDuong) {
                    // Đã hoàn thành - màu xanh
                    lanViews[i].setBackgroundResource(R.drawable.progress_green);
                    lanViews[i].setTextColor(getResources().getColor(android.R.color.white));
                } else {
                    // Chưa đến - màu xám
                    lanViews[i].setBackgroundResource(R.drawable.progress_gray);
                    lanViews[i].setTextColor(getResources().getColor(android.R.color.black));
                }
            }
        }
    }

    /**
     * Load thông tin xe của người dùng từ API
     */
    private void loadXeInfo() {
        RetrofitClient.getApiService().getXeByNguoiDung(DEMO_USER_ID).enqueue(new Callback<ApiResponse<List<Xe>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Xe>>> call, Response<ApiResponse<List<Xe>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Xe>> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null && !apiResponse.getData().isEmpty()) {
                        Xe xe = apiResponse.getData().get(0); // Lấy xe đầu tiên
                        updateXeUI(xe);
                        Log.d(TAG, "Loaded xe: " + xe.getBienSo());

                        // Load tên loại xe
                        if (xe.getMaLoaiXe() != null) {
                            loadLoaiXeInfo(xe.getMaLoaiXe());
                        }
                    } else {
                        Log.w(TAG, "Người dùng chưa có xe");
                        if (tvTenXe != null)
                            tvTenXe.setText("Chưa có xe");
                        if (tvBienSo != null)
                            tvBienSo.setText("Thêm xe của bạn");
                    }
                } else {
                    Log.e(TAG, "Error loading xe: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Xe>>> call, Throwable t) {
                Log.e(TAG, "Error loading xe: " + t.getMessage());
            }
        });
    }

    /**
     * Load tên loại xe để hiển thị
     */
    private void loadLoaiXeInfo(String maLoaiXe) {
        RetrofitClient.getApiService().getLoaiXeById(maLoaiXe).enqueue(new Callback<LoaiXe>() {
            @Override
            public void onResponse(Call<LoaiXe> call, Response<LoaiXe> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoaiXe loaiXe = response.body();
                    if (tvTenXe != null && loaiXe.getTenLoaiXe() != null) {
                        tvTenXe.setText(loaiXe.getTenLoaiXe());
                    }
                    if (tvLoaiXe != null && loaiXe.getTenLoaiXe() != null) {
                        tvLoaiXe.setText("Loại xe: " + loaiXe.getTenLoaiXe());
                    }
                    Log.d(TAG, "Loaded loại xe: " + loaiXe.getTenLoaiXe());
                }
            }

            @Override
            public void onFailure(Call<LoaiXe> call, Throwable t) {
                Log.e(TAG, "Error loading loại xe: " + t.getMessage());
            }
        });
    }

    /**
     * Cập nhật UI với thông tin xe
     */
    private void updateXeUI(Xe xe) {
        if (tvBienSo != null && xe.getBienSo() != null) {
            tvBienSo.setText(xe.getBienSo());
        }
        if (tvMauXe != null && xe.getMaLoaiXe() != null) {
            tvMauXe.setText("Mẫu xe: " + xe.getMaLoaiXe());
        }
        if (tvMauSac != null && xe.getMauSac() != null) {
            tvMauSac.setText("Màu sắc: " + xe.getMauSac());
        }
        if (tvDungTich != null && xe.getDungTich() != null) {
            tvDungTich.setText("Dung tích: " + xe.getDungTich());
        }
        if (tvSoKhung != null && xe.getSoKhung() != null) {
            tvSoKhung.setText("Số khung: " + xe.getSoKhung());
        }
    }

    private void toggleHistory() {
        if (layoutHistoryDetail.getVisibility() == View.GONE) {
            layoutHistoryDetail.setVisibility(View.VISIBLE);
            btnExpandHistory.setImageResource(R.drawable.ic_collapse);
        } else {
            layoutHistoryDetail.setVisibility(View.GONE);
            btnExpandHistory.setImageResource(R.drawable.ic_expand);
        }
    }
}
