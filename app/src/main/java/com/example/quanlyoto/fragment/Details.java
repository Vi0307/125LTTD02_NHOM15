package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.PhuTung;

import java.text.DecimalFormat;

public class Details extends Fragment {

        private PhuTung mPhuTung;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                        @Nullable ViewGroup container,
                        @Nullable Bundle savedInstanceState) {

                View view = inflater.inflate(R.layout.activity_details_screen, container, false);

                // Retrieve data
                if (getArguments() != null) {
                        mPhuTung = (PhuTung) getArguments().getSerializable("phutung_item");
                }

                initViews(view);
                setupActions(view);

                return view;
        }

        private void initViews(View view) {
                TextView tvName = view.findViewById(R.id.tvProductName);
                TextView tvPrice = view.findViewById(R.id.tvPrice);
                TextView tvDesc = view.findViewById(R.id.tvDescription);
                ImageView imgProduct = view.findViewById(R.id.imgProduct);

                TextView tvCarType = view.findViewById(R.id.tvCarType);
                TextView tvBrand = view.findViewById(R.id.tvBrand);
                TextView tvSupplier = view.findViewById(R.id.tvSupplier);

                if (mPhuTung != null) {
                        tvName.setText(mPhuTung.getTenPhuTung());

                        DecimalFormat formatter = new DecimalFormat("#,### VND");
                        if (mPhuTung.getGiaBan() != null) {
                                tvPrice.setText(formatter.format(mPhuTung.getGiaBan()));
                        } else {
                                tvPrice.setText("Liên hệ");
                        }

                        tvDesc.setText(mPhuTung.getMoTa() != null ? mPhuTung.getMoTa() : "Đang cập nhật mô tả...");

                        // Additional info
                        tvCarType.setText(mPhuTung.getMaLoaiXe() != null ? mPhuTung.getMaLoaiXe() : "Chưa cập nhật"); // Ideally
                        tvBrand.setText(mPhuTung.getMaHangXe() != null ? mPhuTung.getMaHangXe() : "Chưa cập nhật"); // Ideally
                        tvSupplier.setText(mPhuTung.getNhaCC() != null ? mPhuTung.getNhaCC() : "Chưa cập nhật");

                        // Image Loading
                        String imageName = mPhuTung.getHinhAnh();
                        if (imageName != null && !imageName.isEmpty()) {
                                if (imageName.contains(".")) {
                                        imageName = imageName.substring(0, imageName.lastIndexOf('.'));
                                }

                                int resId = getResources().getIdentifier(imageName, "drawable",
                                                requireContext().getPackageName());
                                if (resId == 0) {
                                        String cleanName = imageName.replace("_", "");
                                        resId = getResources().getIdentifier(cleanName, "drawable",
                                                        requireContext().getPackageName());
                                }

                                // 3. Manual mapping for specific cases (Fixing reported issues)
                                if (resId == 0) {
                                        switch (imageName) {
                                                case "bom_nuoc_dft":
                                                        resId = getResources().getIdentifier("dongcobomnuocdft",
                                                                        "drawable", requireContext().getPackageName());
                                                        break;
                                                case "dongcobnbon":
                                                        resId = getResources().getIdentifier("dongcobomnuocdft",
                                                                        "drawable", requireContext().getPackageName());
                                                        break;
                                                case "giam_xoc_sau":
                                                        resId = getResources().getIdentifier("giamxocsauxe", "drawable",
                                                                        requireContext().getPackageName());
                                                        break;
                                                case "giamxoctruc":
                                                        resId = getResources().getIdentifier("giamxoctruoc", "drawable",
                                                                        requireContext().getPackageName());
                                                        break;
                                                case "thuoc_lai_mercedes":
                                                        resId = getResources().getIdentifier("thuoclai", "drawable",
                                                                        requireContext().getPackageName());
                                                        break;
                                        }
                                }

                                if (resId != 0) {
                                        imgProduct.setImageResource(resId);
                                } else {
                                        imgProduct.setImageResource(R.drawable.onggionaphoi); // Default fallback
                                }
                        } else {
                                imgProduct.setImageResource(R.drawable.onggionaphoi); // Default fallback
                        }
                }
        }

        private void setupActions(View view) {
                ImageView btnBack = view.findViewById(R.id.btnBack);
                ImageView btnCart = view.findViewById(R.id.btnCart); // Retrieve Cart Button
                Button btnAddToCart = view.findViewById(R.id.btnAddToCart);
                Button btnOrder = view.findViewById(R.id.btnOrder);

                // Back
                if (btnBack != null) {
                        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
                }

                // Cart Navigation
                if (btnCart != null) {
                        btnCart.setOnClickListener(v -> {
                                requireActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_container, new Cart())
                                                .addToBackStack(null)
                                                .commit();
                        });
                }

                // Add to Cart
                if (btnAddToCart != null) {
                        btnAddToCart.setOnClickListener(v -> {
                                if (mPhuTung != null) {
                                        android.content.SharedPreferences prefs = requireActivity()
                                                        .getSharedPreferences("UserPrefs",
                                                                        android.content.Context.MODE_PRIVATE);
                                        int userId = prefs.getInt("userId", -1);

                                        if (userId == -1) {
                                                Toast.makeText(getContext(), "Vui lòng đăng nhập để thêm vào giỏ hàng",
                                                                Toast.LENGTH_SHORT).show();
                                                return;
                                        }

                                        com.example.quanlyoto.model.ThemVaoGioHangRequest request = new com.example.quanlyoto.model.ThemVaoGioHangRequest(
                                                        mPhuTung.getMaPhuTung(),
                                                        1,
                                                        mPhuTung.getHinhAnh(),
                                                        mPhuTung.getGiaBan());

                                        com.example.quanlyoto.network.RetrofitClient.getApiService()
                                                        .themVaoGioHang(userId, request)
                                                        .enqueue(new retrofit2.Callback<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>>() {
                                                                @Override
                                                                public void onResponse(
                                                                                retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> call,
                                                                                retrofit2.Response<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> response) {
                                                                        if (response.isSuccessful()) {
                                                                                Toast.makeText(getContext(), "Đã thêm "
                                                                                                + mPhuTung.getTenPhuTung()
                                                                                                + " vào giỏ",
                                                                                                Toast.LENGTH_SHORT)
                                                                                                .show();
                                                                                // Navigate to Cart screen
                                                                                requireActivity()
                                                                                                .getSupportFragmentManager()
                                                                                                .beginTransaction()
                                                                                                .replace(R.id.fragment_container,
                                                                                                                new Cart())
                                                                                                .addToBackStack(null)
                                                                                                .commit();
                                                                        } else {
                                                                                try {
                                                                                        String errorBody = response
                                                                                                        .errorBody()
                                                                                                        .string();
                                                                                        android.util.Log.e("Details",
                                                                                                        "AddCart Error: "
                                                                                                                        + errorBody);
                                                                                        Toast.makeText(getContext(),
                                                                                                        "Lỗi: " + response
                                                                                                                        .code(),
                                                                                                        Toast.LENGTH_SHORT)
                                                                                                        .show();
                                                                                } catch (Exception e) {
                                                                                        Toast.makeText(getContext(),
                                                                                                        "Lỗi khi thêm vào giỏ hàng",
                                                                                                        Toast.LENGTH_SHORT)
                                                                                                        .show();
                                                                                }
                                                                        }
                                                                }

                                                                @Override
                                                                public void onFailure(
                                                                                retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> call,
                                                                                Throwable t) {
                                                                        Toast.makeText(getContext(),
                                                                                        "Lỗi kết nối: " + t
                                                                                                        .getMessage(),
                                                                                        Toast.LENGTH_SHORT).show();
                                                                }
                                                        });
                                }
                        });
                }

                // Order
                if (btnOrder != null) {
                        btnOrder.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new Detail_Payment_Fragment())
                                        .addToBackStack(null)
                                        .commit());
                }
        }
}