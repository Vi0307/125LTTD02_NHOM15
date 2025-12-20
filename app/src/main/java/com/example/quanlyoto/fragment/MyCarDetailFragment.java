package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.BaoDuong;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.model.LichSuBaoDuong;
import com.example.quanlyoto.model.LoaiXe;
import com.example.quanlyoto.model.NguoiDung;
import com.example.quanlyoto.model.Xe;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCarDetailFragment extends Fragment {

    private static final String TAG = "MyCarDetailFragment";

    // User ID từ SharedPreferences
    private int currentUserId = -1;

    private LinearLayout layoutHistoryDetail;
    private ImageButton btnExpandHistory;
    private Button btnViewHistoryDetail;
    private Button btnThayPhuTung;
    private ScrollView scrollView;

    // Views cho thông tin xe
    private TextView tvTenXe, tvBienSo, tvMauXe, tvLoaiXe, tvMauSac, tvDungTich, tvSoKhung;

    // Views cho các ô bảo dưỡng
    private TextView tvLan1, tvLan2, tvLan3, tvLan4, tvLan5, tvLan6;

    // Views cho nhắc nhở phụ tùng
    private LinearLayout containerNhacNho;
    private TextView tvKhongCoNhacNho;

    // Views cho lịch sử bảo dưỡng
    private LinearLayout containerLichSu;
    private TextView tvKhongCoLichSu;

    // Cache tên đại lý
    private Map<Integer, String> daiLyCache = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycar_main_detail, container, false);

        // Khởi tạo Views
        initViews(view);

        // Lấy userId từ SharedPreferences
        android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs",
                android.content.Context.MODE_PRIVATE);
        currentUserId = prefs.getInt("userId", -1);

        // Load thông tin xe
        loadXeInfo();

        // Load thông tin người dùng (để lấy số lần bảo dưỡng)
        loadUserInfo();

        // Load nhắc nhở bảo dưỡng
        loadNhacNhoBaoDuong();

        // Load lịch sử bảo dưỡng
        loadLichSuBaoDuong();

        // ScrollView
        scrollView = view.findViewById(R.id.scrollView);

        // Layout các phần
        layoutHistoryDetail = view.findViewById(R.id.layoutHistoryDetail);
        CardView layoutNhacNhoPhuTung = view.findViewById(R.id.layoutNhacNhoPhuTung);

        // Nút expand lịch sử
        btnExpandHistory = view.findViewById(R.id.btn_expand_history);

        // Nút xem chi tiết lịch sử (dưới cùng sau khi expand)
        btnViewHistoryDetail = view.findViewById(R.id.btnViewHistoryDetail);

        // Nút thay thế phụ tùng
        btnThayPhuTung = view.findViewById(R.id.btnThayPhuTung);

        // Nút đặt dịch vụ
        View btnDatDichVu1 = view.findViewById(R.id.btnDatDichVu1);
        View btnDatDichVu2 = view.findViewById(R.id.btnDatDichVu);

        // NÚT BACK
        view.findViewById(R.id.btn_back).setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MyCarFragment())
                .commit());

        // EXPAND LỊCH SỬ
        btnExpandHistory.setOnClickListener(v -> toggleHistory());

        // NÚT XEM CHI TIẾT LỊCH SỬ
        if (btnViewHistoryDetail != null) {
            btnViewHistoryDetail.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Maintenance_History_Fragment())
                    .addToBackStack(null)
                    .commit());
        }

        // NÚT THAY THẾ PHỤ TÙNG → scroll xuống layoutNhacNhoPhuTung
        if (btnThayPhuTung != null && layoutNhacNhoPhuTung != null && scrollView != null) {
            btnThayPhuTung.setOnClickListener(
                    v -> scrollView.post(() -> scrollView.smoothScrollTo(0, layoutNhacNhoPhuTung.getTop())));
        }

        // NÚT ĐẶT DỊCH VỤ
        View.OnClickListener goToAgency = v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new Agency_Fragment())
                .addToBackStack(null)
                .commit();

        if (btnDatDichVu1 != null)
            btnDatDichVu1.setOnClickListener(goToAgency);
        if (btnDatDichVu2 != null)
            btnDatDichVu2.setOnClickListener(goToAgency);

        // ==========================
        // CHATBOX → ChatFragment
        // ==========================
        View chatBtn = view.findViewById(R.id.btnChat);
        if (chatBtn != null) {
            chatBtn.setOnClickListener(v -> {
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
        // Views cho thông tin xe
        tvTenXe = view.findViewById(R.id.tvTenXe);
        tvBienSo = view.findViewById(R.id.tvBienSo);
        tvMauXe = view.findViewById(R.id.tvMauXe);
        tvLoaiXe = view.findViewById(R.id.tvLoaiXe);
        tvMauSac = view.findViewById(R.id.tvMauSac);
        tvDungTich = view.findViewById(R.id.tvDungTich);
        tvSoKhung = view.findViewById(R.id.tvSoKhung);

        // Views cho các ô bảo dưỡng
        tvLan1 = view.findViewById(R.id.tvLan1);
        tvLan2 = view.findViewById(R.id.tvLan2);
        tvLan3 = view.findViewById(R.id.tvLan3);
        tvLan4 = view.findViewById(R.id.tvLan4);
        tvLan5 = view.findViewById(R.id.tvLan5);
        tvLan6 = view.findViewById(R.id.tvLan6);

        // Views cho nhắc nhở
        containerNhacNho = view.findViewById(R.id.containerNhacNho);
        tvKhongCoNhacNho = view.findViewById(R.id.tvKhongCoNhacNho);

        // Views cho lịch sử
        containerLichSu = view.findViewById(R.id.containerLichSu);
        tvKhongCoLichSu = view.findViewById(R.id.tvKhongCoLichSu);
    }

    /**
     * Load thông tin người dùng để lấy số lần bảo dưỡng
     */
    private void loadUserInfo() {
        RetrofitClient.getApiService().getNguoiDungById(currentUserId).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NguoiDung user = response.body();
                    Integer soLanBaoDuong = user.getSoLanBaoDuong();
                    if (soLanBaoDuong != null) {
                        updateBaoDuongUI(soLanBaoDuong);
                        Log.d(TAG, "Số lần bảo dưỡng: " + soLanBaoDuong);
                    }
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
     * Load danh sách nhắc nhở bảo dưỡng từ API
     */
    private void loadNhacNhoBaoDuong() {
        RetrofitClient.getApiService().getBaoDuongByNguoiDung(currentUserId)
                .enqueue(new Callback<ApiResponse<List<BaoDuong>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<BaoDuong>>> call,
                            Response<ApiResponse<List<BaoDuong>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<List<BaoDuong>> apiResponse = response.body();
                            if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                                List<BaoDuong> danhSachBaoDuong = apiResponse.getData();

                                // Lọc những bản ghi có TrangThai = 'Nhắc nhở'
                                int count = 0;
                                for (BaoDuong bd : danhSachBaoDuong) {
                                    if ("Nhắc nhở".equals(bd.getTrangThai())) {
                                        addNhacNhoItem(bd.getMoTa());
                                        count++;
                                    }
                                }

                                if (count == 0) {
                                    // Không có nhắc nhở
                                    if (tvKhongCoNhacNho != null) {
                                        tvKhongCoNhacNho.setVisibility(View.VISIBLE);
                                    }
                                }

                                Log.d(TAG, "Loaded " + count + " nhắc nhở bảo dưỡng");
                            }
                        } else {
                            Log.e(TAG, "Error loading bao duong: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<BaoDuong>>> call, Throwable t) {
                        Log.e(TAG, "Error loading bao duong: " + t.getMessage());
                    }
                });
    }

    /**
     * Load lịch sử bảo dưỡng từ API
     */
    private void loadLichSuBaoDuong() {
        RetrofitClient.getApiService().getLichSuBaoDuongByNguoiDung(currentUserId)
                .enqueue(new Callback<ApiResponse<List<LichSuBaoDuong>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<LichSuBaoDuong>>> call,
                            Response<ApiResponse<List<LichSuBaoDuong>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<List<LichSuBaoDuong>> apiResponse = response.body();
                            if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                                List<LichSuBaoDuong> danhSachLichSu = apiResponse.getData();

                                if (danhSachLichSu.isEmpty()) {
                                    if (tvKhongCoLichSu != null) {
                                        tvKhongCoLichSu.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    for (LichSuBaoDuong ls : danhSachLichSu) {
                                        // Load tên đại lý và thêm item
                                        loadDaiLyAndAddHistoryItem(ls);
                                    }
                                }

                                Log.d(TAG, "Loaded " + danhSachLichSu.size() + " lịch sử bảo dưỡng");
                            }
                        } else {
                            Log.e(TAG, "Error loading lich su: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<LichSuBaoDuong>>> call, Throwable t) {
                        Log.e(TAG, "Error loading lich su: " + t.getMessage());
                    }
                });
    }

    /**
     * Load tên đại lý và thêm item lịch sử
     */
    private void loadDaiLyAndAddHistoryItem(LichSuBaoDuong lichSu) {
        Integer maDaiLy = lichSu.getMaDaiLy();

        // Kiểm tra cache
        if (daiLyCache.containsKey(maDaiLy)) {
            addLichSuItem(daiLyCache.get(maDaiLy), lichSu.getNgayThucHien());
            return;
        }

        // Gọi API lấy tên đại lý
        RetrofitClient.getApiService().getDaiLyById(maDaiLy).enqueue(new Callback<ApiResponse<DaiLy>>() {
            @Override
            public void onResponse(Call<ApiResponse<DaiLy>> call, Response<ApiResponse<DaiLy>> response) {
                String tenDaiLy = "Đại lý #" + maDaiLy;
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<DaiLy> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        tenDaiLy = apiResponse.getData().getTenDaiLy();
                        daiLyCache.put(maDaiLy, tenDaiLy);
                    }
                }
                addLichSuItem(tenDaiLy, lichSu.getNgayThucHien());
            }

            @Override
            public void onFailure(Call<ApiResponse<DaiLy>> call, Throwable t) {
                addLichSuItem("Đại lý #" + maDaiLy, lichSu.getNgayThucHien());
            }
        });
    }

    /**
     * Thêm một item lịch sử vào container
     */
    private void addLichSuItem(String tenDaiLy, String ngayThucHien) {
        if (containerLichSu == null || getContext() == null)
            return;

        // Tạo LinearLayout cho item (outer)
        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setBackgroundColor(0xFFF8F8F8); // #F8F8F8
        itemLayout.setPadding(36, 36, 36, 36);

        // Margin bottom
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
        params.bottomMargin = 30;
        itemLayout.setLayoutParams(params);

        // Inner horizontal layout
        LinearLayout innerLayout = new LinearLayout(getContext());
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        innerLayout.setGravity(android.view.Gravity.CENTER_VERTICAL);

        // ImageView (icon)
        ImageView icon = new ImageView(getContext());
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(84, 84);
        icon.setLayoutParams(iconParams);
        icon.setImageResource(R.drawable.ic_build);
        icon.setColorFilter(0xFF000000); // Black

        // Text container
        LinearLayout textContainer = new LinearLayout(getContext());
        LinearLayout.LayoutParams textContainerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textContainerParams.leftMargin = 36;
        textContainer.setLayoutParams(textContainerParams);
        textContainer.setOrientation(LinearLayout.VERTICAL);

        // Tên đại lý
        TextView tvTenDaiLy = new TextView(getContext());
        tvTenDaiLy.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        tvTenDaiLy.setText(tenDaiLy);
        tvTenDaiLy.setTextColor(0xFF000000);
        tvTenDaiLy.setTextSize(16);
        tvTenDaiLy.setTypeface(null, android.graphics.Typeface.BOLD);

        // Ngày bảo dưỡng
        TextView tvNgay = new TextView(getContext());
        tvNgay.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Format ngày
        String ngayFormatted = formatNgay(ngayThucHien);
        tvNgay.setText("Ngày bảo dưỡng: " + ngayFormatted);
        tvNgay.setTextColor(0xFF6B7280);
        tvNgay.setTextSize(14);

        // Build layout
        textContainer.addView(tvTenDaiLy);
        textContainer.addView(tvNgay);

        innerLayout.addView(icon);
        innerLayout.addView(textContainer);

        itemLayout.addView(innerLayout);

        // Thêm item vào container
        containerLichSu.addView(itemLayout);
    }

    /**
     * Format ngày từ ISO string sang dd/MM/yyyy
     */
    private String formatNgay(String isoDate) {
        if (isoDate == null)
            return "N/A";
        try {
            // Format từ "2025-05-06T00:00:00" sang "06/05/2025"
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

    /**
     * Thêm một item nhắc nhở vào container
     */
    private void addNhacNhoItem(String moTa) {
        if (containerNhacNho == null || getContext() == null)
            return;

        // Tạo LinearLayout cho item
        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setBackgroundColor(0xFFFFF3F3); // #FFF3F3
        itemLayout.setPadding(36, 36, 36, 36);
        itemLayout.setGravity(android.view.Gravity.CENTER_VERTICAL);

        // Margin bottom
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
        params.bottomMargin = 24;
        itemLayout.setLayoutParams(params);

        // ImageView
        ImageView icon = new ImageView(getContext());
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(72, 72);
        icon.setLayoutParams(iconParams);
        icon.setImageResource(android.R.drawable.ic_menu_info_details);
        icon.setColorFilter(0xFFD32F2F); // #D32F2F

        // TextView
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        textParams.leftMargin = 36;
        textView.setLayoutParams(textParams);
        textView.setText(moTa != null ? moTa : "Nhắc nhở bảo dưỡng");
        textView.setTextColor(0xFFD32F2F); // #D32F2F
        textView.setTextSize(15);

        // Thêm vào item
        itemLayout.addView(icon);
        itemLayout.addView(textView);

        // Thêm item vào container
        containerNhacNho.addView(itemLayout);
    }

    /**
     * Cập nhật UI các ô bảo dưỡng theo số lần đã bảo dưỡng
     * - Xanh lá (progress_green): đã hoàn thành
     * - Xám (progress_gray): chưa đến
     */
    private void updateBaoDuongUI(int soLanBaoDuong) {
        TextView[] lanViews = { tvLan1, tvLan2, tvLan3, tvLan4, tvLan5, tvLan6 };

        for (int i = 0; i < lanViews.length; i++) {
            if (lanViews[i] != null) {
                if (i < soLanBaoDuong) {
                    // Đã hoàn thành - màu xanh
                    lanViews[i].setBackgroundResource(R.drawable.progress_green);
                    lanViews[i].setTextColor(getResources().getColor(android.R.color.white));
                } else {
                    // Chưa đến - màu xám
                    lanViews[i].setBackgroundResource(R.drawable.progress_gray);
                    lanViews[i].setTextColor(getResources().getColor(android.R.color.black));
                }
            }
        }
    }

    /**
     * Load thông tin xe của người dùng từ API
     */
    private void loadXeInfo() {
        RetrofitClient.getApiService().getXeByNguoiDung(currentUserId).enqueue(new Callback<ApiResponse<List<Xe>>>() {
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
                    if (tvLoaiXe != null && loaiXe.getTenLoaiXe() != null) {
                        tvLoaiXe.setText("Loại xe: " + loaiXe.getTenLoaiXe());
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
     * Cập nhật UI với thông tin xe
     */
    private void updateXeUI(Xe xe) {
        if (tvBienSo != null && xe.getBienSo() != null) {
            tvBienSo.setText(xe.getBienSo());
        }
        if (tvMauXe != null && xe.getMaLoaiXe() != null) {
            tvMauXe.setText("Mẫu xe: " + xe.getMaLoaiXe());
        }
        if (tvMauSac != null && xe.getMauSac() != null) {
            tvMauSac.setText("Màu sắc: " + xe.getMauSac());
        }
        if (tvDungTich != null && xe.getDungTich() != null) {
            tvDungTich.setText("Dung tích: " + xe.getDungTich());
        }
        if (tvSoKhung != null && xe.getSoKhung() != null) {
            tvSoKhung.setText("Số khung: " + xe.getSoKhung());
        }
    }

    private void toggleHistory() {
        if (layoutHistoryDetail.getVisibility() == View.GONE) {
            layoutHistoryDetail.setVisibility(View.VISIBLE);
            btnExpandHistory.setImageResource(R.drawable.ic_collapse);
        } else {
            layoutHistoryDetail.setVisibility(View.GONE);
            btnExpandHistory.setImageResource(R.drawable.ic_expand);
        }
    }
}
