package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class VoucherStillValid extends Fragment {

    private TextView tabActive, tabUsed;
    private View tabIndicator;
    private Button btnJoinGame;
    private ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voucher_still_valid, container, false);

        // Ánh xạ view
        tabActive = view.findViewById(R.id.tab_active);
        tabUsed = view.findViewById(R.id.tab_used);
        tabIndicator = view.findViewById(R.id.tab_indicator);
        btnJoinGame = view.findViewById(R.id.btn_join_game);
        btnBack = view.findViewById(R.id.btn_back);

        // Nút Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang trước (pop stack), tránh tạo vòng lặp
                getParentFragmentManager().popBackStack();
            }
        });

        // Tab "Đã sử dụng"
        tabUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VoucherExpired())
                        // .addToBackStack(null) // KHÔNG add vào stack khi chuyển tab để tránh loop
                        .commit();
            }
        });

        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PlayHome())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // RecyclerView Setup
        androidx.recyclerview.widget.RecyclerView rcvVoucher = view.findViewById(R.id.rcv_voucher);
        rcvVoucher.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        com.example.quanlyoto.adapter.VoucherAdapter adapter = new com.example.quanlyoto.adapter.VoucherAdapter(
                getContext(), new java.util.ArrayList<>(), false);
        rcvVoucher.setAdapter(adapter);

        // Gọi API lấy voucher của user 1
        ApiService apiService = RetrofitClient.getApiService();

        apiService.getVoucherByUser(1).enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher> vouchers = response.body();
                    List<Voucher> filteredList = new java.util.ArrayList<>();

                    for (Voucher v : vouchers) {
                        try {
                            if ("Còn hiệu lực".equalsIgnoreCase(v.getTrangThai())) {
                                filteredList.add(v);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.setData(filteredList);

                } else {
                    if (getContext() != null)
                        Toast.makeText(getContext(), "Không lấy được dữ liệu voucher", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                if (getContext() != null)
                    Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}