package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.PhuTung;
import com.example.quanlyoto.network.RetrofitClient;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bodyparts extends Fragment {

    private static final String TAG = "BodypartsFragment";
    private GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_bodyparts_screen, container, false);

        // 👉 Nút back → quay về Homeparts
        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .popBackStack(); // trở về fragment trước đó (Homeparts)
        });

        // ======================================================
        // GRID LAYOUT SETUP (Recursive Find)
        // ======================================================
        gridLayout = findGridLayout(view);

        if (gridLayout != null) {
            // Remove hardcoded items immediately so user knows logic is running
            gridLayout.removeAllViews();
            loadPhuTungData();
        } else {
            Log.e(TAG, "Could not find GridLayout to populate data");
            Toast.makeText(getContext(), "Lỗi giao diện: Không tìm thấy lưới sản phẩm", Toast.LENGTH_SHORT).show();
        }

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

    // Helper to find GridLayout recursively
    private GridLayout findGridLayout(View root) {
        if (root instanceof GridLayout) {
            return (GridLayout) root;
        }
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                GridLayout found = findGridLayout(group.getChildAt(i));
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    private void loadPhuTungData() {
        // "DM01" là mã danh mục cho Phụ tùng thân vỏ
        RetrofitClient.getApiService().getPhuTungByDanhMuc("DM01").enqueue(new Callback<List<PhuTung>>() {
            @Override
            public void onResponse(Call<List<PhuTung>> call, Response<List<PhuTung>> response) {
                if (getContext() == null)
                    return; // Fragment died

                if (response.isSuccessful() && response.body() != null) {
                    List<PhuTung> phuTungList = response.body();
                    Log.d(TAG, "API Success: Received " + phuTungList.size() + " items");
                    for (PhuTung pt : phuTungList) {
                        Log.d(TAG, "Item: " + pt.getTenPhuTung() + " - " + pt.getHinhAnh());
                    }
                    updateGridLayout(phuTungList);
                } else {
                    Log.e(TAG, "Failed to load phu tung: " + response.code());
                    Toast.makeText(getContext(), "Không thể tải danh sách phụ tùng (Code: " + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PhuTung>> call, Throwable t) {
                if (getContext() == null)
                    return;
                Log.e(TAG, "Error loading phu tung: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGridLayout(List<PhuTung> list) {
        if (gridLayout == null || getContext() == null)
            return;

        gridLayout.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        DecimalFormat formatter = new DecimalFormat("#,###");

        if (list == null || list.isEmpty()) {
            Toast.makeText(getContext(), "Không có phụ tùng nào", Toast.LENGTH_SHORT).show();
            return;
        }

        for (PhuTung item : list) {
            View itemView = inflater.inflate(R.layout.item_phutung, gridLayout, false);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            int margin = (int) (8 * getResources().getDisplayMetrics().density);
            params.setMargins(margin, margin, margin, margin);

            itemView.setLayoutParams(params);

            ImageView img = itemView.findViewById(R.id.imgPhuTung);
            TextView tvName = itemView.findViewById(R.id.tvTenPhuTung);
            TextView tvPrice = itemView.findViewById(R.id.tvGiaPhuTung);
            Button btnAdd = itemView.findViewById(R.id.btnAddToCart);

            tvName.setText(item.getTenPhuTung());
            if (item.getGiaBan() != null) {
                tvPrice.setText(formatter.format(item.getGiaBan()) + " VND");
            } else {
                tvPrice.setText("Liên hệ");
            }

            // Image
            String imageName = item.getHinhAnh();
            if (imageName != null && !imageName.isEmpty()) {
                if (imageName.contains(".")) {
                    imageName = imageName.substring(0, imageName.lastIndexOf('.'));
                }

                // 1. Try exact match
                int resId = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());

                // 2. Try removing underscores (e.g., den_pha -> denpha)
                if (resId == 0) {
                    String cleanName = imageName.replace("_", "");
                    resId = getResources().getIdentifier(cleanName, "drawable", getContext().getPackageName());
                }

                // 3. Manual mapping for specific cases
                if (resId == 0) {
                    if (imageName.equals("bom_nuoc_dft")) {
                        resId = getResources().getIdentifier("dongcobomnuocdft", "drawable",
                                getContext().getPackageName());
                    }
                }

                if (resId != 0) {
                    img.setImageResource(resId);
                } else {
                    // Fallback
                    img.setImageResource(R.drawable.onggionaphoi);
                }
            } else {
                img.setImageResource(R.drawable.onggionaphoi);
            }

            itemView.setOnClickListener(v -> {
                Details detailsFragment = new Details();
                Bundle args = new Bundle();
                args.putSerializable("phutung_item", item);
                detailsFragment.setArguments(args);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment)
                        .addToBackStack(null)
                        .commit();
            });

            btnAdd.setOnClickListener(v -> {
                android.content.SharedPreferences prefs = requireActivity()
                        .getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE);
                int userId = prefs.getInt("userId", -1);

                if (userId == -1) {
                    Toast.makeText(getContext(), "Vui lòng đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                com.example.quanlyoto.model.ThemVaoGioHangRequest request = new com.example.quanlyoto.model.ThemVaoGioHangRequest(
                        item.getMaPhuTung(),
                        1,
                        item.getHinhAnh(),
                        item.getGiaBan());

                com.example.quanlyoto.network.RetrofitClient.getApiService()
                        .themVaoGioHang(userId, request)
                        .enqueue(
                                new retrofit2.Callback<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>>() {
                                    @Override
                                    public void onResponse(
                                            retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> call,
                                            retrofit2.Response<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getContext(), "Đã thêm " + item.getTenPhuTung() + " vào giỏ",
                                                    Toast.LENGTH_SHORT).show();
                                            requireActivity().getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.fragment_container, new Cart())
                                                    .addToBackStack(null)
                                                    .commit();
                                        } else {
                                            Toast.makeText(getContext(), "Lỗi khi thêm vào giỏ hàng",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(
                                            retrofit2.Call<com.example.quanlyoto.model.ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> call,
                                            Throwable t) {
                                        Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
            });

            gridLayout.addView(itemView);
        }
    }
}
