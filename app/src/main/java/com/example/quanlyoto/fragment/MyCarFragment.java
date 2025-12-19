package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.LoaiXe;
import com.example.quanlyoto.model.Xe;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCarFragment extends Fragment {

    private static final String TAG = "MyCarFragment";

    // Demo user ID - thay đổi theo database của bạn
    private static final int DEMO_USER_ID = 1;

    // Views
    private TextView tvTenXe, tvBienSo, tvDungTich, tvSoKhung;

    public MyCarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycar, container, false);

        // Khởi tạo Views
        initViews(view);

        // Load thông tin xe
        loadXeInfo();

        // ==========================
        // NÚT BACK → HOME
        // ==========================
        view.findViewById(R.id.btn_back).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // ==========================
        // BOTTOM NAV — HOME
        // ==========================
        view.findViewById(R.id.navHome).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // ==========================
        // BOTTOM NAV — PARTS
        // ==========================
        view.findViewById(R.id.navParts).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homeparts())
                    .commit();
        });

        // ==========================
        // BOTTOM NAV — VOUCHER
        // ==========================
        view.findViewById(R.id.navVoucher).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .commit();
        });

        // ======================================================
        // BOTTOM NAV — AGENCY
        // ======================================================
        view.findViewById(R.id.navAgency).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

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

        // ======================================================
        // BUTTON — ĐẶT DỊCH VỤ → ĐẠI LÝ
        // ======================================================
        view.findViewById(R.id.btnDatDichVu).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        // ==========================
        // BUTTON — XEM CHI TIẾT
        // ==========================
        view.findViewById(R.id.btnXemChiTiet).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarDetailFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    /**
     * Khởi tạo các View
     */
    private void initViews(View view) {
        tvTenXe = view.findViewById(R.id.tvTenXe);
        tvBienSo = view.findViewById(R.id.tvBienSo);
        tvDungTich = view.findViewById(R.id.tvDungTich);
        tvSoKhung = view.findViewById(R.id.tvSoKhung);
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
                        if (tvDungTich != null)
                            tvDungTich.setText("");
                        if (tvSoKhung != null)
                            tvSoKhung.setText("");
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
        if (tvDungTich != null && xe.getDungTich() != null) {
            tvDungTich.setText("Động cơ: " + xe.getDungTich());
        }
        if (tvSoKhung != null && xe.getSoKhung() != null) {
            tvSoKhung.setText("Số khung: " + xe.getSoKhung());
        }
    }
}
