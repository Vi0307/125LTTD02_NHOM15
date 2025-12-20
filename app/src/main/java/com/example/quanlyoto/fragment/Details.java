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
                Button btnAddToCart = view.findViewById(R.id.btnAddToCart);
                Button btnOrder = view.findViewById(R.id.btnOrder);

                // Back
                if (btnBack != null) {
                        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
                }

                // Add to Cart
                if (btnAddToCart != null) {
                        btnAddToCart.setOnClickListener(v -> {
                                // Add to cart logic here (e.g., saving to database or shared prefs)
                                // For now, simple navigation as before
                                requireActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_container, new Cart())
                                                .addToBackStack(null)
                                                .commit();
                                if (mPhuTung != null) {
                                        Toast.makeText(getContext(), "Đã thêm " + mPhuTung.getTenPhuTung() + " vào giỏ",
                                                        Toast.LENGTH_SHORT).show();
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