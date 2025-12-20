package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Homeparts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_homeparts_screen, container, false);

        // --- GỌI API USER ---
        android.widget.TextView tvUserName = view.findViewById(R.id.tvUserName);
        android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs",
                android.content.Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1); // Default to -1

        if (userId != -1) {
            com.example.quanlyoto.network.RetrofitClient.getApiService().getNguoiDungById(userId)
                    .enqueue(new retrofit2.Callback<com.example.quanlyoto.model.NguoiDung>() {
                        @Override
                        public void onResponse(retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call,
                                retrofit2.Response<com.example.quanlyoto.model.NguoiDung> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String userName = response.body().getHoTen();
                                if (tvUserName != null) {
                                    tvUserName.setText(userName);
                                }
                            } else {
                                android.util.Log.e("API_USER", "Lỗi: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call, Throwable t) {
                            android.util.Log.e("API_USER", "Thất bại: " + t.getMessage());
                        }
                    });
        } else {
            if (tvUserName != null) {
                tvUserName.setText("Khách");
            }
        }
        // ------------------------

        // --- GỌI API DANH MỤC ---
        com.example.quanlyoto.network.RetrofitClient.getApiService().getAllDanhMuc()
                .enqueue(new retrofit2.Callback<java.util.List<com.example.quanlyoto.model.DmPhuTung>>() {
                    @Override
                    public void onResponse(retrofit2.Call<java.util.List<com.example.quanlyoto.model.DmPhuTung>> call,
                            retrofit2.Response<java.util.List<com.example.quanlyoto.model.DmPhuTung>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            android.util.Log.d("API_DMPHUTUNG",
                                    "Lấy thành công " + response.body().size() + " danh mục");
                            for (com.example.quanlyoto.model.DmPhuTung dm : response.body()) {
                                android.util.Log.d("API_DMPHUTUNG", " - " + dm.getTenDanhMuc());
                            }
                            // TODO: Hiển thị lên UI (nếu cần)
                        } else {
                            android.util.Log.e("API_DMPHUTUNG", "Lỗi: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<java.util.List<com.example.quanlyoto.model.DmPhuTung>> call,
                            Throwable t) {
                        android.util.Log.e("API_DMPHUTUNG", "Thất bại: " + t.getMessage());
                    }
                });
        // ------------------------

        // --- GỌI API LOẠI XE & SETUP RECYCLER VIEW ---
        androidx.recyclerview.widget.RecyclerView rvLoaiXe = view.findViewById(R.id.rvLoaiXe);
        rvLoaiXe.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));

        com.example.quanlyoto.network.RetrofitClient.getApiService().getAllLoaiXe()
                .enqueue(new retrofit2.Callback<java.util.List<com.example.quanlyoto.model.LoaiXe>>() {
                    @Override
                    public void onResponse(retrofit2.Call<java.util.List<com.example.quanlyoto.model.LoaiXe>> call,
                            retrofit2.Response<java.util.List<com.example.quanlyoto.model.LoaiXe>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            com.example.quanlyoto.adapter.LoaiXeAdapter adapter = new com.example.quanlyoto.adapter.LoaiXeAdapter(
                                    response.body());
                            rvLoaiXe.setAdapter(adapter);
                        } else {
                            android.util.Log.e("API_LOAIXE", "Lỗi: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<java.util.List<com.example.quanlyoto.model.LoaiXe>> call,
                            Throwable t) {
                        android.util.Log.e("API_LOAIXE", "Thất bại: " + t.getMessage());
                    }
                });
        // ------------------------

        // ⭐ Icon giỏ hàng — mở trang Cart
        View imgCart = view.findViewById(R.id.imgCart);
        if (imgCart != null) {
            imgCart.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Cart())
                        .addToBackStack(null)
                        .commit();
            });
        }
        // ⭐ Nút "Phụ tùng thân vỏ"
        Button btnBodyParts = view.findViewById(R.id.btnBodyParts);

        btnBodyParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Bodyparts())
                    .addToBackStack(null)
                    .commit();
        });

        // ⭐ Nút "Phụ tùng động cơ"
        Button btnEngineParts = view.findViewById(R.id.btnEngineParts);

        btnEngineParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Engineparts())
                    .addToBackStack(null)
                    .commit();
        });

        // ⭐ Nút "Hệ truyền động" — DrivetrainParts
        Button btnDrivetrainParts = view.findViewById(R.id.btnTransmissionParts);

        btnDrivetrainParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Drivetrainparts())
                    .addToBackStack(null)
                    .commit();
        });

        // ⭐ Nút "Phụ tùng điện"
        Button btnElectricalParts = view.findViewById(R.id.btnLightingParts);

        btnElectricalParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Electricalparts())
                    .addToBackStack(null) // quay lại Homeparts
                    .commit();
        });

        // ======================================================
        // BOTTOM NAV
        // ======================================================

        // 2. Trang chủ
        View navParts = view.findViewById(R.id.navHome);
        if (navParts != null) {
            navParts.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // 1. Xe của tôi
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 3. Voucher
        View navVoucher = view.findViewById(R.id.navVoucher);
        if (navVoucher != null) {
            navVoucher.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // 4. ĐẠI LÝ
        View navAgency = view.findViewById(R.id.navAgency);
        if (navAgency != null) {
            navAgency.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Agency_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }
}
