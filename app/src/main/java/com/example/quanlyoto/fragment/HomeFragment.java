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
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.model.LoaiXe;
import com.example.quanlyoto.model.NguoiDung;
import com.example.quanlyoto.model.Xe;
import com.example.quanlyoto.network.RetrofitClient;
import com.example.quanlyoto.viewmodel.DaiLyViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    // Demo user ID - thay đổi theo database của bạn
    private static final int DEMO_USER_ID = 1;

    // ViewModel
    private DaiLyViewModel daiLyViewModel;

    // Views cho phần Đại lý
    private TextView tvTenDaiLy, tvDiaChiDaiLy, tvGioLamViec, tvSoDienThoai;

    // View cho tên người dùng
    private TextView tvUserName;

    // Views cho thông tin xe
    private TextView tvTenXe, tvBienSo;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        // Khởi tạo Views
        initViews(view);

        // Khởi tạo ViewModel và load dữ liệu đại lý
        setupViewModel();

        // Load thông tin người dùng
        loadUserInfo();

        // Load thông tin xe
        loadXeInfo();

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
        if (tvUserName != null) {
            tvUserName.setOnClickListener(v -> {
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
     * Khởi tạo các View
     */
    private void initViews(View view) {
        // Views cho Đại lý
        tvTenDaiLy = view.findViewById(R.id.tvTenDaiLy);
        tvDiaChiDaiLy = view.findViewById(R.id.tvDiaChiDaiLy);
        tvGioLamViec = view.findViewById(R.id.tvGioLamViec);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);

        // View cho tên người dùng
        tvUserName = view.findViewById(R.id.UserName);

        // Views cho xe
        tvTenXe = view.findViewById(R.id.tvTenXe);
        tvBienSo = view.findViewById(R.id.tvBienSo);
    }

    /**
     * Setup ViewModel và observe dữ liệu Đại lý
     */
    private void setupViewModel() {
        daiLyViewModel = new ViewModelProvider(this).get(DaiLyViewModel.class);

        daiLyViewModel.getDaiLyList().observe(getViewLifecycleOwner(), daiLyList -> {
            if (daiLyList != null && !daiLyList.isEmpty()) {
                updateDaiLyUI(daiLyList.get(0));
                Log.d(TAG, "Loaded " + daiLyList.size() + " đại lý");
            }
        });

        daiLyViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error DaiLy: " + error);
            }
        });

        daiLyViewModel.loadDaiLy();
    }

    /**
     * Load thông tin người dùng từ API
     */
    private void loadUserInfo() {
        RetrofitClient.getApiService().getNguoiDungById(DEMO_USER_ID).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NguoiDung user = response.body();
                    updateUserUI(user);
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
     * Load thông tin xe của người dùng từ API
     */
    private void loadXeInfo() {
        RetrofitClient.getApiService().getXeByNguoiDung(DEMO_USER_ID).enqueue(new Callback<ApiResponse<List<Xe>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Xe>>> call, Response<ApiResponse<List<Xe>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Xe>> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null && !apiResponse.getData().isEmpty()) {
                        Xe xe = apiResponse.getData().get(0); // Lấy xe đầu tiên
                        updateXeUI(xe);
                        Log.d(TAG, "Loaded xe: " + xe.getBienSo());

                        // Load tên loại xe
                        if (xe.getMaLoaiXe() != null) {
                            loadLoaiXeInfo(xe.getMaLoaiXe());
                        }
                    } else {
                        Log.w(TAG, "Người dùng chưa có xe");
                        if (tvTenXe != null)
                            tvTenXe.setText("Chưa có xe");
                        if (tvBienSo != null)
                            tvBienSo.setText("Thêm xe của bạn");
                    }
                } else {
                    Log.e(TAG, "Error loading xe: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Xe>>> call, Throwable t) {
                Log.e(TAG, "Error loading xe: " + t.getMessage());
            }
        });
    }

    /**
     * Load tên loại xe để hiển thị
     */
    private void loadLoaiXeInfo(String maLoaiXe) {
        RetrofitClient.getApiService().getLoaiXeById(maLoaiXe).enqueue(new Callback<LoaiXe>() {
            @Override
            public void onResponse(Call<LoaiXe> call, Response<LoaiXe> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoaiXe loaiXe = response.body();
                    if (tvTenXe != null && loaiXe.getTenLoaiXe() != null) {
                        tvTenXe.setText(loaiXe.getTenLoaiXe());
                    }
                    Log.d(TAG, "Loaded loại xe: " + loaiXe.getTenLoaiXe());
                }
            }

            @Override
            public void onFailure(Call<LoaiXe> call, Throwable t) {
                Log.e(TAG, "Error loading loại xe: " + t.getMessage());
            }
        });
    }

    /**
     * Cập nhật UI với thông tin người dùng
     */
    private void updateUserUI(NguoiDung user) {
        if (tvUserName != null && user.getHoTen() != null) {
            tvUserName.setText(user.getHoTen());
        }
    }

    /**
     * Cập nhật UI với thông tin xe
     */
    private void updateXeUI(Xe xe) {
        if (tvBienSo != null && xe.getBienSo() != null) {
            tvBienSo.setText(xe.getBienSo());
        }
        // tvTenXe sẽ được cập nhật khi load LoaiXe
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
