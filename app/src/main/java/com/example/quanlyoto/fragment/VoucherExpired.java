package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.Voucher;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherExpired extends Fragment {

    private TextView tabActive, tabUsed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voucher_expired, container, false);

        tabActive = view.findViewById(R.id.tab_active); // "Còn hiệu lực"
        tabUsed = view.findViewById(R.id.tab_used); // "Đã sử dụng"

        // Click vào tab "Còn hiệu lực" → chuyển sang VoucherStillValidFragment
        tabActive.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .addToBackStack(null) // nếu muốn back quay lại VoucherExpired
                    .commit();
        });

        // Optional: tabUsed đang ở fragment này, không cần click
        tabUsed.setOnClickListener(v -> {
            /* Không làm gì */ });

        ImageView btnBack = view.findViewById(R.id.btn_back);

        // Quay lại PersonalActivity (Fragment)
        btnBack.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PersonalActivity())
                    .commit();
        });

        // ================= XỬ LÝ HIỂN THỊ VOUCHER (ĐÃ SỬ DỤNG / HẾT HẠN)
        // =================
        // RecyclerView Setup
        androidx.recyclerview.widget.RecyclerView rcvVoucher = view.findViewById(R.id.rcv_voucher);
        rcvVoucher.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        com.example.quanlyoto.adapter.VoucherAdapter adapter = new com.example.quanlyoto.adapter.VoucherAdapter(
                getContext(), new java.util.ArrayList<>(), true);
        rcvVoucher.setAdapter(adapter);

        ApiService apiService = RetrofitClient.getApiService();

        apiService.getVoucherByUser(1).enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher> vouchers = response.body();
                    List<Voucher> filteredList = new java.util.ArrayList<>();

                    for (Voucher v : vouchers) {
                        try {
                            // Filter: Not "Còn hiệu lực"
                            if (!"Còn hiệu lực".equalsIgnoreCase(v.getTrangThai())) {
                                filteredList.add(v);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.setData(filteredList);

                } else {
                    if (getContext() != null)
                        Toast.makeText(getContext(), "Không lấy được dữ liệu voucher: " + response.message(),
                                Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
