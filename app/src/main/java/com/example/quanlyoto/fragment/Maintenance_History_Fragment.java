package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.model.LichSuBaoDuong;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Maintenance_History_Fragment extends Fragment {

    private static final String TAG = "MaintenanceHistory";
    
    private RecyclerView recyclerView;
    private MaintenanceHistoryAdapter adapter;
    private List<MaintenanceRecord> recordList;
    private int currentUserId = -1;
    
    // Cache tên đại lý
    private Map<Integer, String> daiLyCache = new HashMap<>();

    public Maintenance_History_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_maintenance_history, container, false);

        // Lấy userId từ SharedPreferences
        android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs",
                android.content.Context.MODE_PRIVATE);
        currentUserId = prefs.getInt("userId", -1);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo list và adapter
        recordList = new ArrayList<>();
        adapter = new MaintenanceHistoryAdapter(recordList, this);
        recyclerView.setAdapter(adapter);

        // Load dữ liệu từ API
        loadLichSuBaoDuong();

        // BOTTOM NAV — HOME
        view.findViewById(R.id.navHome).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // BOTTOM NAV — PARTS
        view.findViewById(R.id.navParts).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homeparts())
                    .commit();
        });

        // BOTTOM NAV — VOUCHER
        view.findViewById(R.id.navVoucher).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .commit();
        });

        // BOTTOM NAV — AGENCY
        view.findViewById(R.id.navAgency).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        //==================== XỬ LÝ NÚT BACK ====================//
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }

        return view;
    }
    
    /**
     * Load lịch sử bảo dưỡng từ API
     */
    private void loadLichSuBaoDuong() {
        if (currentUserId == -1) {
            Log.w(TAG, "User chưa đăng nhập");
            return;
        }
        
        RetrofitClient.getApiService().getLichSuBaoDuongByNguoiDung(currentUserId)
                .enqueue(new Callback<ApiResponse<List<LichSuBaoDuong>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<LichSuBaoDuong>>> call,
                            Response<ApiResponse<List<LichSuBaoDuong>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<List<LichSuBaoDuong>> apiResponse = response.body();
                            if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                                List<LichSuBaoDuong> danhSachLichSu = apiResponse.getData();
                                
                                for (LichSuBaoDuong ls : danhSachLichSu) {
                                    loadDaiLyAndAddRecord(ls);
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
     * Load tên đại lý và thêm record vào list
     */
    private void loadDaiLyAndAddRecord(LichSuBaoDuong lichSu) {
        Integer maDaiLy = lichSu.getMaDaiLy();
        
        if (daiLyCache.containsKey(maDaiLy)) {
            addRecordToList(daiLyCache.get(maDaiLy), lichSu);
            return;
        }
        
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
                addRecordToList(tenDaiLy, lichSu);
            }

            @Override
            public void onFailure(Call<ApiResponse<DaiLy>> call, Throwable t) {
                addRecordToList("Đại lý #" + maDaiLy, lichSu);
            }
        });
    }
    
    /**
     * Thêm record vào list và notify adapter
     */
    private void addRecordToList(String tenDaiLy, LichSuBaoDuong lichSu) {
        String ngayFormatted = formatNgay(lichSu.getNgayThucHien());
        MaintenanceRecord record = new MaintenanceRecord(tenDaiLy, ngayFormatted, "N/A", lichSu.getMaLSBD());
        recordList.add(record);
        adapter.notifyItemInserted(recordList.size() - 1);
    }
    
    /**
     * Format ngày từ ISO string sang dd/MM/yyyy
     */
    private String formatNgay(String isoDate) {
        if (isoDate == null) return "N/A";
        try {
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

    //================= MODEL CHO ADAPTER =================//
    public static class MaintenanceRecord {
        private String dealer;
        private String date;
        private String km;
        private Integer maLSBD;

        public MaintenanceRecord(String dealer, String date, String km, Integer maLSBD) {
            this.dealer = dealer;
            this.date = date;
            this.km = km;
            this.maLSBD = maLSBD;
        }

        public String getDealer() { return dealer; }
        public String getDate() { return date; }
        public String getKm() { return km; }
        public Integer getMaLSBD() { return maLSBD; }
    }

    //================= ADAPTER =================//
    public static class MaintenanceHistoryAdapter extends RecyclerView.Adapter<MaintenanceHistoryAdapter.ViewHolder> {

        private List<MaintenanceRecord> recordList;
        private Fragment fragment;

        public MaintenanceHistoryAdapter(List<MaintenanceRecord> recordList, Fragment fragment) {
            this.recordList = recordList;
            this.fragment = fragment;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_maintenance_history, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MaintenanceRecord record = recordList.get(position);

            holder.tvDealer.setText("Đại lý: " + record.getDealer());
            holder.tvDate.setText(record.getDate());
            holder.tvKm.setText(record.getKm());

            holder.arrow.setOnClickListener(v -> {
                Maintenance_Detail_Fragment detailFragment = new Maintenance_Detail_Fragment();

                Bundle bundle = new Bundle();
                bundle.putString("dealer", record.getDealer());
                bundle.putString("date", record.getDate());
                bundle.putString("km", record.getKm());
                if (record.getMaLSBD() != null) {
                    bundle.putInt("maLSBD", record.getMaLSBD());
                }
                detailFragment.setArguments(bundle);

                fragment.getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        @Override
        public int getItemCount() {
            return recordList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvDealer, tvDate, tvKm;
            ImageView arrow;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDealer = itemView.findViewById(R.id.tvDealer);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvKm = itemView.findViewById(R.id.tvKm);
                arrow = itemView.findViewById(R.id.manitenace_history_detail);
            }
        }
    }
}
