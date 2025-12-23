package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.NguoiDung;
import com.example.quanlyoto.network.RetrofitClient;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalActivity extends Fragment {

    private static final String TAG = "PersonalActivity";

    private ImageView btnBack, btnEdit;
    private LinearLayout btnLogout, itemVoucher, itemOrder;
    private FrameLayout logoutOverlay;
    private CardView btnCancel, btnConfirmLogout;
    private FloatingActionButton fabChat;

    // Views hiển thị thông tin
    private TextView tvUserNameHeader, tvHoTen, tvSoDienThoai, tvNgaySinh, tvEmail, tvTinhThanh;

    private int currentUserId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_personal, container, false);

        // Lấy userId từ SharedPreferences
        android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs",
                android.content.Context.MODE_PRIVATE);
        currentUserId = prefs.getInt("userId", -1);

        // Ánh xạ các view
        btnBack = view.findViewById(R.id.btn_back);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnLogout = view.findViewById(R.id.btn_logout);
        itemVoucher = view.findViewById(R.id.item_voucher);
        itemOrder = view.findViewById(R.id.item_order);
        logoutOverlay = view.findViewById(R.id.logout_overlay);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnConfirmLogout = view.findViewById(R.id.btn_confirm_logout);
        fabChat = view.findViewById(R.id.btn_logout_confirm);

        // Ánh xạ views thông tin
        tvUserNameHeader = view.findViewById(R.id.tvUserNameHeader);
        tvHoTen = view.findViewById(R.id.tvHoTen);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTinhThanh = view.findViewById(R.id.tvTinhThanh);

        // Load thông tin người dùng
        loadUserInfo();

        // Nút Back
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Nút Edit
        btnEdit.setOnClickListener(
                v -> Toast.makeText(getActivity(), "Chỉnh sửa thông tin", Toast.LENGTH_SHORT).show());

        // Voucher
        itemVoucher.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .addToBackStack(null)
                    .commit();
        });

        // Đơn hàng
        itemOrder.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MyOrderFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Dịch vụ của tôi
        View itemService = view.findViewById(R.id.item_service);
        if (itemService != null) {
            itemService.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AppointmentSee_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Nút Đăng xuất - Hiện dialog
        btnLogout.setOnClickListener(v -> logoutOverlay.setVisibility(View.VISIBLE));

        // Nút Đóng - Đóng dialog
        btnCancel.setOnClickListener(v -> logoutOverlay.setVisibility(View.GONE));

        // Nút Confirm Logout
        btnConfirmLogout.setOnClickListener(v -> {
            logoutOverlay.setVisibility(View.GONE);

            // Xóa SharedPreferences
            android.content.SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            // Chuyển sang WelcomeFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Welcome())
                    .commit();
        });

        // Click vào overlay cũng đóng dialog
        logoutOverlay.setOnClickListener(v -> logoutOverlay.setVisibility(View.GONE));

        // FAB Chat
        fabChat.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ChatBox())
                    .addToBackStack(null)
                    .commit();
        });

        // Xử lý nút Back của hệ thống
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (logoutOverlay.getVisibility() == View.VISIBLE) {
                            logoutOverlay.setVisibility(View.GONE);
                        } else {
                            getParentFragmentManager().popBackStack();
                        }
                    }
                });

        return view;
    }

    /**
     * Load thông tin người dùng từ API
     */
    private void loadUserInfo() {
        if (currentUserId == -1) {
            Log.w(TAG, "User chưa đăng nhập");
            return;
        }

        RetrofitClient.getApiService().getNguoiDungById(currentUserId).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NguoiDung user = response.body();
                    updateUI(user);
                    Log.d(TAG, "Loaded user: " + user.getHoTen());
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
     * Cập nhật UI với thông tin user
     */
    private void updateUI(NguoiDung user) {
        if (tvUserNameHeader != null) {
            tvUserNameHeader.setText(user.getHoTen() != null ? user.getHoTen() : "N/A");
        }
        if (tvHoTen != null) {
            tvHoTen.setText(user.getHoTen() != null ? user.getHoTen() : "N/A");
        }
        if (tvSoDienThoai != null) {
            tvSoDienThoai.setText(user.getDienThoai() != null ? user.getDienThoai() : "N/A");
        }
        if (tvNgaySinh != null) {
            String ngaySinh = formatNgay(user.getNgaySinh());
            tvNgaySinh.setText(ngaySinh);
        }
        if (tvEmail != null) {
            tvEmail.setText(user.getEmail() != null ? user.getEmail() : "N/A");
        }
        if (tvTinhThanh != null) {
            // Hiện tại model không có getDiaChi, có thể thêm sau
            tvTinhThanh.setText("Đà Nẵng"); // Default
        }
    }

    /**
     * Format ngày từ ISO string sang dd/MM/yyyy
     */
    private String formatNgay(String isoDate) {
        if (isoDate == null)
            return "N/A";
        try {
            if (isoDate.contains("T")) {
                String[] parts = isoDate.split("T")[0].split("-");
                if (parts.length == 3) {
                    return parts[2] + "/" + parts[1] + "/" + parts[0];
                }
            }
            return isoDate;
        } catch (Exception e) {
            return isoDate;
        }
    }
}