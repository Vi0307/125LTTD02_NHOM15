package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.viewmodel.DaiLyViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    // ViewModel
    private DaiLyViewModel daiLyViewModel;

    // Views cho phần Đại lý
    private TextView tvTenDaiLy, tvDiaChiDaiLy, tvGioLamViec, tvSoDienThoai;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        // Khởi tạo Views cho Đại lý
        initDaiLyViews(view);

        // Khởi tạo ViewModel và load dữ liệu
        setupViewModel();

        // ======================================================
        // BOTTOM NAV
        // ======================================================

        // 1. Xe của tôi
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // 2. Phụ tùng
        View navParts = view.findViewById(R.id.navParts);
        if (navParts != null) {
            navParts.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Homeparts())
                        .addToBackStack(null)
                        .commit();
            });
        }

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

        // ======================================================
        // CÁC NÚT TRONG TRANG HOME
        // ======================================================

        // Xem chi tiết
        View btnXem = view.findViewById(R.id.btnXemChiTiet);
        if (btnXem != null) {
            btnXem.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarDetailFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Arrow icon → sang chi tiết
        View arrow = view.findViewById(R.id.arrowIcon);
        if (arrow != null) {
            arrow.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyCarDetailFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Mua sắm ngay → sang Homeparts
        View btnMuaSam = view.findViewById(R.id.btnMuaSamNgay);
        if (btnMuaSam != null) {
            btnMuaSam.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Homeparts())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Xem thêm voucher
        View btnXemVC = view.findViewById(R.id.btnXemVoucher);
        if (btnXemVC != null) {
            btnXemVC.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // ================================
        // BẤM VÀO TÊN USER -> TRANG INFO
        // ================================
        View userName = view.findViewById(R.id.UserName);
        if (userName != null) {
            userName.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PersonalActivity())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // ======================================================
        // CHI TIẾT & ĐẶT HẸN DỊCH VỤ
        // ======================================================
        View btnChitietvaDat = view.findViewById(R.id.btnChitietvaDat);
        if (btnChitietvaDat != null) {
            btnChitietvaDat.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Agency_Fragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // ======================================================
        // FAB CHAT — MỞ TRANG CHAT
        // ======================================================
        View btnChat = view.findViewById(R.id.btnChat);
        if (btnChat != null) {
            btnChat.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }

    /**
     * Khởi tạo các View cho phần Đại lý
     */
    private void initDaiLyViews(View view) {
        tvTenDaiLy = view.findViewById(R.id.tvTenDaiLy);
        tvDiaChiDaiLy = view.findViewById(R.id.tvDiaChiDaiLy);
        tvGioLamViec = view.findViewById(R.id.tvGioLamViec);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
    }

    /**
     * Setup ViewModel và observe dữ liệu
     */
    private void setupViewModel() {
        // Khởi tạo ViewModel
        daiLyViewModel = new ViewModelProvider(this).get(DaiLyViewModel.class);

        // Observe danh sách đại lý
        daiLyViewModel.getDaiLyList().observe(getViewLifecycleOwner(), daiLyList -> {
            if (daiLyList != null && !daiLyList.isEmpty()) {
                // Hiển thị đại lý đầu tiên
                updateDaiLyUI(daiLyList.get(0));
                Log.d(TAG, "Loaded " + daiLyList.size() + " đại lý");
            }
        });

        // Observe lỗi
        daiLyViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error: " + error);
            }
        });

        // Gọi API load dữ liệu
        daiLyViewModel.loadDaiLy();
    }

    /**
     * Cập nhật UI với dữ liệu đại lý từ API
     */
    private void updateDaiLyUI(DaiLy daiLy) {
        if (tvTenDaiLy != null) {
            tvTenDaiLy.setText(daiLy.getTenDaiLy() != null ? daiLy.getTenDaiLy() : "N/A");
        }
        if (tvDiaChiDaiLy != null) {
            tvDiaChiDaiLy.setText(daiLy.getDiaChi() != null ? daiLy.getDiaChi() : "N/A");
        }
        if (tvGioLamViec != null) {
            tvGioLamViec.setText(daiLy.getGioLamViec() != null ? daiLy.getGioLamViec() : "N/A");
        }
        if (tvSoDienThoai != null) {
            tvSoDienThoai.setText(daiLy.getSoDienThoai() != null ? daiLy.getSoDienThoai() : "N/A");
        }
    }
}
