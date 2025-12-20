package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Login extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                        @Nullable Bundle savedInstanceState) {

                View view = inflater.inflate(R.layout.activity_login_screen, container, false);

                ImageView btnBack = view.findViewById(R.id.btnBack);
                Button btnLogin = view.findViewById(R.id.btnLogin);
                android.widget.EditText etPhone = view.findViewById(R.id.etPhone);
                android.widget.EditText etPassword = view.findViewById(R.id.etPassword);
                android.widget.CheckBox cbRememberMe = view.findViewById(R.id.cbRememberMe);

                // 🔙 Nút quay lại trang GetStarted
                btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

                // ✔️ Nút ĐĂNG NHẬP
                btnLogin.setOnClickListener(v -> {
                        String phone = etPhone.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                        if (phone.isEmpty() || password.isEmpty()) {
                                android.widget.Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin",
                                                android.widget.Toast.LENGTH_SHORT).show();
                                return;
                        }

                        com.example.quanlyoto.model.LoginRequest loginRequest = new com.example.quanlyoto.model.LoginRequest(
                                        phone, password);

                        com.example.quanlyoto.network.RetrofitClient.getApiService().login(loginRequest)
                                        .enqueue(new retrofit2.Callback<com.example.quanlyoto.model.NguoiDung>() {
                                                @Override
                                                public void onResponse(
                                                                retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call,
                                                                retrofit2.Response<com.example.quanlyoto.model.NguoiDung> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                                com.example.quanlyoto.model.NguoiDung user = response
                                                                                .body();

                                                                // Lưu thông tin User vào SharedPreferences
                                                                android.content.SharedPreferences prefs = requireActivity()
                                                                                .getSharedPreferences("UserPrefs",
                                                                                                android.content.Context.MODE_PRIVATE);
                                                                android.content.SharedPreferences.Editor editor = prefs
                                                                                .edit();
                                                                editor.putInt("userId", user.getMaND());
                                                                editor.putString("userName", user.getHoTen());
                                                                editor.putBoolean("isLoggedIn", true);
                                                                editor.apply();

                                                                android.widget.Toast.makeText(getContext(),
                                                                                "Đăng nhập thành công!",
                                                                                android.widget.Toast.LENGTH_SHORT)
                                                                                .show();

                                                                // Chuyển sang HomeFragment
                                                                requireActivity().getSupportFragmentManager()
                                                                                .beginTransaction()
                                                                                .replace(R.id.fragment_container,
                                                                                                new HomeFragment())
                                                                                .commit();
                                                        } else {
                                                                android.widget.Toast.makeText(getContext(),
                                                                                "Đăng nhập thất bại: Sai thông tin",
                                                                                android.widget.Toast.LENGTH_SHORT)
                                                                                .show();
                                                        }
                                                }

                                                @Override
                                                public void onFailure(
                                                                retrofit2.Call<com.example.quanlyoto.model.NguoiDung> call,
                                                                Throwable t) {
                                                        android.widget.Toast.makeText(getContext(),
                                                                        "Lỗi kết nối: " + t.getMessage(),
                                                                        android.widget.Toast.LENGTH_SHORT).show();
                                                }
                                        });
                });

                return view;
        }
}
