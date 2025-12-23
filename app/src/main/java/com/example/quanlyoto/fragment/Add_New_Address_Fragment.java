package com.example.quanlyoto.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.DiaChi;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_New_Address_Fragment extends Fragment {

    private LinearLayout optionHome, optionOffice;
    private ImageView radioHome, radioOffice;
    private EditText etTinhThanh, etQuanHuyen, etPhuongXa, etAddress;
    private Button btnAddAddress;
    
    private int selected = 0; // 1 = nhà riêng, 2 = văn phòng
    private Integer userId;
    private ApiService apiService;
    
    // Địa chỉ vừa được thêm
    private DiaChi newlyAddedAddress = null;

    public Add_New_Address_Fragment() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_new_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo API service
        apiService = RetrofitClient.getApiService();
        
        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = prefs.getInt("userId", -1);

        initViews(view);
        setupClickListeners(view);
    }

    private void initViews(View view) {
        // Input fields
        etTinhThanh = view.findViewById(R.id.et_tinh_thanh);
        etQuanHuyen = view.findViewById(R.id.et_quan_huyen);
        etPhuongXa = view.findViewById(R.id.et_phuong_xa);
        etAddress = view.findViewById(R.id.et_address);
        
        // Radio options
        optionHome = view.findViewById(R.id.option_home);
        optionOffice = view.findViewById(R.id.option_office);
        radioHome = view.findViewById(R.id.radio_home);
        radioOffice = view.findViewById(R.id.radio_office);
        
        // Buttons
        btnAddAddress = view.findViewById(R.id.btn_add_address);
    }

    private void setupClickListeners(View view) {
        ImageView ivBack = view.findViewById(R.id.iv_back);
        
        // Xử lý radio
        optionHome.setOnClickListener(v -> selectHome());
        optionOffice.setOnClickListener(v -> selectOffice());
        radioHome.setOnClickListener(v -> selectHome());
        radioOffice.setOnClickListener(v -> selectOffice());

        // BACK
        ivBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // THÊM ĐỊA CHỈ - Gọi API để lưu vào database
        btnAddAddress.setOnClickListener(v -> {
            addNewAddress();
        });
    }

    private void addNewAddress() {
        // Lấy dữ liệu từ các input
        String tinhThanh = etTinhThanh.getText().toString().trim();
        String quanHuyen = etQuanHuyen.getText().toString().trim();
        String phuongXa = etPhuongXa.getText().toString().trim();
        String diaChiChiTiet = etAddress.getText().toString().trim();
        
        // Validate - ít nhất phải có địa chỉ chi tiết
        if (diaChiChiTiet.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập địa chỉ cụ thể", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selected == 0) {
            Toast.makeText(getContext(), "Vui lòng chọn loại địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId == -1) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập để thêm địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng DiaChi
        DiaChi diaChi = new DiaChi();
        diaChi.setTinhThanhPho(tinhThanh);
        diaChi.setQuanHuyen(quanHuyen);
        diaChi.setPhuongXa(phuongXa);
        diaChi.setDiaChiChiTiet(diaChiChiTiet);
        diaChi.setLoaiDiaChi(selected == 1 ? "Nhà riêng" : "Công ty");
        diaChi.setMacDinh(false); // Mặc định không phải địa chỉ mặc định

        // Disable button để tránh click nhiều lần
        btnAddAddress.setEnabled(false);
        btnAddAddress.setText("Đang thêm...");

        // Gọi API
        apiService.addDiaChi(userId, diaChi).enqueue(new Callback<DiaChi>() {
            @Override
            public void onResponse(Call<DiaChi> call, Response<DiaChi> response) {
                btnAddAddress.setEnabled(true);
                btnAddAddress.setText("Thêm địa chỉ");

                if (response.isSuccessful() && response.body() != null) {
                    newlyAddedAddress = response.body();
                    Toast.makeText(getContext(), "Thêm địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                    
                    // Chuyển về trang chọn địa chỉ
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    String errorMsg = "Lỗi thêm địa chỉ (Code: " + response.code() + ")";
                    try {
                        if (response.errorBody() != null) {
                            String errorDetail = response.errorBody().string();
                            android.util.Log.e("AddAddress", "Error: " + errorDetail);
                            if (errorDetail.length() > 100) {
                                errorDetail = errorDetail.substring(0, 100);
                            }
                            errorMsg += " - " + errorDetail;
                        }
                    } catch (Exception e) {
                        android.util.Log.e("AddAddress", "Parse error: " + e.getMessage());
                    }
                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DiaChi> call, Throwable t) {
                btnAddAddress.setEnabled(true);
                btnAddAddress.setText("Thêm địa chỉ");
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInputs() {
        etTinhThanh.setText("");
        etQuanHuyen.setText("");
        etPhuongXa.setText("");
        etAddress.setText("");
        selected = 0;
        radioHome.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioOffice.setImageResource(R.drawable.ic_radio_button_unchecked);
    }

    private void selectHome() {
        selected = 1;
        radioHome.setImageResource(R.drawable.ic_radio_button);
        radioOffice.setImageResource(R.drawable.ic_radio_button_unchecked);
    }

    private void selectOffice() {
        selected = 2;
        radioHome.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioOffice.setImageResource(R.drawable.ic_radio_button);
    }
}
