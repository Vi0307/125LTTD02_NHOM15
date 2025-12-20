package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
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

public class Drivetrainparts extends Fragment {

    private static final String TAG = "DrivetrainpartsFragment";
    private GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_drivetrainparts_screen, container, false);

        // 1. Find GridLayout recursively
        gridLayout = findGridLayout(view);
        if (gridLayout == null) {
            Toast.makeText(getContext(), "Không tìm thấy GridLayout!", Toast.LENGTH_SHORT).show();
        }

        // 2. Load API Data (Category "DM02" - Phụ tùng truyền động)
        loadPhuTungData();

        // 3. Navigation
        setupNavigation(view);

        return view;
    }

    private GridLayout findGridLayout(View root) {
        if (root instanceof GridLayout) {
            return (GridLayout) root;
        }
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                GridLayout result = findGridLayout(group.getChildAt(i));
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    private void loadPhuTungData() {
        RetrofitClient.getApiService().getPhuTungByDanhMuc("DM02").enqueue(new Callback<List<PhuTung>>() {
            @Override
            public void onResponse(Call<List<PhuTung>> call, Response<List<PhuTung>> response) {
                if (getContext() == null)
                    return; // Prevent crash if fragment detached

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
                Log.e(TAG, "API Error: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGridLayout(List<PhuTung> list) {
        if (gridLayout == null)
            return;

        gridLayout.removeAllViews(); // Clear static items

        if (list.isEmpty()) {
            Toast.makeText(getContext(), "Không có phụ tùng nào trong danh mục này", Toast.LENGTH_SHORT).show();
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        DecimalFormat formatter = new DecimalFormat("#,### VND");

        for (PhuTung item : list) {
            View itemView = inflater.inflate(R.layout.item_phutung, gridLayout, false);

            ImageView img = itemView.findViewById(R.id.imgPhuTung);
            TextView tvName = itemView.findViewById(R.id.tvTenPhuTung);
            TextView tvPrice = itemView.findViewById(R.id.tvGiaPhuTung);
            Button btnAdd = itemView.findViewById(R.id.btnAddToCart);

            tvName.setText(item.getTenPhuTung());
            if (item.getGiaBan() != null) {
                tvPrice.setText(formatter.format(item.getGiaBan()));
            } else {
                tvPrice.setText("Liên hệ");
            }

            // Image Loading Logic
            String imageName = item.getHinhAnh();
            if (imageName != null && !imageName.isEmpty()) {
                if (imageName.contains(".")) {
                    imageName = imageName.substring(0, imageName.lastIndexOf('.'));
                }

                // 1. Try exact match
                int resId = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());

                // 2. Try removing underscores
                if (resId == 0) {
                    String cleanName = imageName.replace("_", "");
                    resId = getResources().getIdentifier(cleanName, "drawable", getContext().getPackageName());
                }

                // 3. Manual mapping
                if (resId == 0) {
                    switch (imageName) {
                        case "giam_xoc_sau":
                            resId = getResources().getIdentifier("giamxocsauxe", "drawable",
                                    getContext().getPackageName());
                            break;
                    }
                }

                if (resId != 0) {
                    img.setImageResource(resId);
                } else {
                    // Fallback
                    img.setImageResource(R.drawable.giamxocsauxe); // Fallback to a generic drivetrain part image
                }
            } else {
                img.setImageResource(R.drawable.giamxocsauxe);
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

            // Click Add -> Cart
            btnAdd.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Cart())
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(getContext(), "Đã thêm " + item.getTenPhuTung() + " vào giỏ", Toast.LENGTH_SHORT).show();
            });

            // Layout Params for 2 columns
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(16, 16, 16, 16);
            itemView.setLayoutParams(params);

            gridLayout.addView(itemView);
        }
    }

    private void setupNavigation(View view) {
        // Back
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        }

        // Home
        View navHome = view.findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Car
        View navCar = view.findViewById(R.id.navCar);
        if (navCar != null) {
            navCar.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Voucher
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

        // Agency
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

        // Cart Header Icon
        View btnCartHeader = view.findViewById(R.id.btnCartHeader);
        if (btnCartHeader != null) {
            btnCartHeader.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Cart())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
