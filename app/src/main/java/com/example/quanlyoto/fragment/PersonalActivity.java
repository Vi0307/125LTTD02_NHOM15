package com.example.quanlyoto.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalActivity extends Fragment {

    private static final String TAG = "PersonalActivity";

    private ImageView btnBack, btnEdit;
    private LinearLayout btnLogout, itemVoucher, itemOrder;
    private FrameLayout logoutOverlay;
    private CardView btnCancel, btnConfirmLogout, btnSave;

    // Views hiển thị thông tin
    private TextView tvUserNameHeader, tvHoTen, tvSoDienThoai, tvNgaySinh, tvEmail, tvTinhThanh;

    // EditText để chỉnh sửa
    private EditText etHoTen, etSoDienThoai, etEmail, etTinhThanh;

    private int currentUserId = -1;
    private boolean isEditMode = false;
    private NguoiDung currentUser = null;
    private String selectedNgaySinh = null; // Lưu ngày sinh định dạng ISO

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
        btnSave = view.findViewById(R.id.btnSave);

        // Ánh xạ views thông tin (TextView)
        tvUserNameHeader = view.findViewById(R.id.tvUserNameHeader);
        tvHoTen = view.findViewById(R.id.tvHoTen);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTinhThanh = view.findViewById(R.id.tvTinhThanh);

        // Ánh xạ EditText
        etHoTen = view.findViewById(R.id.etHoTen);
        etSoDienThoai = view.findViewById(R.id.etSoDienThoai);
        etEmail = view.findViewById(R.id.etEmail);
        etTinhThanh = view.findViewById(R.id.etTinhThanh);

        // Load thông tin người dùng
        loadUserInfo();

        // Nút Back
        btnBack.setOnClickListener(v -> {
            if (isEditMode) {
                toggleEditMode(false);
            } else {
                getParentFragmentManager().popBackStack();
            }
        });

        // Nút Edit - Toggle edit mode
        btnEdit.setOnClickListener(v -> toggleEditMode(!isEditMode));

        // Nút Lưu
        btnSave.setOnClickListener(v -> saveUserInfo());

        // Click vào ngày sinh để chọn ngày (khi Edit Mode)
        tvNgaySinh.setOnClickListener(v -> {
            if (isEditMode) {
                showDatePicker();
            }
        });

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

        // Xử lý nút Back của hệ thống
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (logoutOverlay.getVisibility() == View.VISIBLE) {
                            logoutOverlay.setVisibility(View.GONE);
                        } else if (isEditMode) {
                            toggleEditMode(false);
                        } else {
                            getParentFragmentManager().popBackStack();
                        }
                    }
                });

        return view;
    }

    /**
     * Toggle giữa chế độ xem và chế độ chỉnh sửa
     */
    private void toggleEditMode(boolean enable) {
        isEditMode = enable;

        int tvVisibility = enable ? View.GONE : View.VISIBLE;
        int etVisibility = enable ? View.VISIBLE : View.GONE;
        int saveVisibility = enable ? View.VISIBLE : View.GONE;

        // Toggle visibility của TextView và EditText
        tvHoTen.setVisibility(tvVisibility);
        tvSoDienThoai.setVisibility(tvVisibility);
        tvEmail.setVisibility(tvVisibility);
        tvTinhThanh.setVisibility(tvVisibility);

        etHoTen.setVisibility(etVisibility);
        etSoDienThoai.setVisibility(etVisibility);
        etEmail.setVisibility(etVisibility);
        etTinhThanh.setVisibility(etVisibility);

        btnSave.setVisibility(saveVisibility);

        if (enable) {
            // Copy giá trị từ TextView sang EditText
            etHoTen.setText(tvHoTen.getText());
            etSoDienThoai.setText(tvSoDienThoai.getText());
            etEmail.setText(tvEmail.getText());
            etTinhThanh.setText(tvTinhThanh.getText());

            Toast.makeText(getActivity(), "Đang chỉnh sửa thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Hiển thị DatePicker để chọn ngày sinh
     */
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String display = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    tvNgaySinh.setText(display);
                    // Lưu dạng ISO để gửi lên server
                    selectedNgaySinh = String.format("%d-%02d-%02dT00:00:00", selectedYear, selectedMonth + 1,
                            selectedDay);
                },
                year, month, day);
        datePickerDialog.show();
    }

    /**
     * Lưu thông tin người dùng
     */
    private void saveUserInfo() {
        if (currentUserId == -1) {
            Toast.makeText(getActivity(), "Lỗi: Không xác định được người dùng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo object NguoiDung với thông tin mới
        NguoiDung updateRequest = new NguoiDung();
        updateRequest.setHoTen(etHoTen.getText().toString().trim());
        updateRequest.setDienThoai(etSoDienThoai.getText().toString().trim());
        updateRequest.setEmail(etEmail.getText().toString().trim());

        if (selectedNgaySinh != null) {
            updateRequest.setNgaySinh(selectedNgaySinh);
        }

        // Gọi API cập nhật
        RetrofitClient.getApiService().updateNguoiDung(currentUserId, updateRequest).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getActivity(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    currentUser = response.body();
                    updateUI(currentUser);
                    toggleEditMode(false);
                } else {
                    Toast.makeText(getActivity(), "Lỗi cập nhật: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error updating user: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error updating user: " + t.getMessage());
            }
        });
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
                    currentUser = response.body();
                    updateUI(currentUser);
                    Log.d(TAG, "Loaded user: " + currentUser.getHoTen());
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