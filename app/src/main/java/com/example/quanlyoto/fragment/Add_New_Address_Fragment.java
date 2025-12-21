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
    private EditText etAddress;
    private Button btnAddAddress, btnApply;
    
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
        etAddress = view.findViewById(R.id.et_address);
        optionHome = view.findViewById(R.id.option_home);
        optionOffice = view.findViewById(R.id.option_office);
        radioHome = view.findViewById(R.id.radio_home);
        radioOffice = view.findViewById(R.id.radio_office);
        btnAddAddress = view.findViewById(R.id.btn_add_address);
        btnApply = view.findViewById(R.id.btn_apply);
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

        // ÁP DỤNG - Quay về trang chọn địa chỉ
        btnApply.setOnClickListener(v -> {
            if (newlyAddedAddress == null) {
                Toast.makeText(getContext(), "Vui lòng thêm địa chỉ trước", Toast.LENGTH_SHORT).show();
                return;
            }

            // Quay về trang chọn địa chỉ
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void addNewAddress() {
        String address = etAddress.getText().toString().trim();
        
        // Validate
        if (address.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
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
        diaChi.setDiaChiChiTiet(address);
        diaChi.setLoaiDiaChi(selected == 1 ? "Nhà riêng" : "Văn phòng");
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
                    
                    // Xóa input để người dùng có thể thêm địa chỉ khác
                    etAddress.setText("");
                    selected = 0;
                    radioHome.setImageResource(R.drawable.ic_radio_button_unchecked);
                    radioOffice.setImageResource(R.drawable.ic_radio_button_unchecked);
                } else {
                    String errorMsg = "Lỗi thêm địa chỉ";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg += ": " + response.errorBody().string();
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
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
