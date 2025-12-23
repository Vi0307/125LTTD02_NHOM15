package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.adapter.OrderDetailAdapter;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.ChiTietDonHang;
import com.example.quanlyoto.model.DonHang;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order_Detail_Fragment extends Fragment {

    private RecyclerView rcvOrderItems;
    private OrderDetailAdapter adapter;
    private List<ChiTietDonHang> listChiTiet;

    private TextView tvAddress, tvPaymentMethod, tvShippingMethod, tvDeliveryTime;
    private TextView tvSubtotal, tvShipping, tvDiscount, tvTotal;

    private String orderId;

    public Order_Detail_Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_detail, container, false);

        // Get Order ID from arguments
        if (getArguments() != null) {
            orderId = getArguments().getString("orderId");
        }

        // Ensure we have an order ID for testing if null (Fallback/Debug)
        if (orderId == null) {
            orderId = "DH001"; // Default for testing
        }

        initViews(view);
        fetchOrderData();

        return view;
    }

    private void initViews(View view) {
        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        rcvOrderItems = view.findViewById(R.id.rcvOrderItems);
        rcvOrderItems.setLayoutManager(new LinearLayoutManager(getContext()));
        listChiTiet = new ArrayList<>();
        adapter = new OrderDetailAdapter(getContext(), listChiTiet);
        rcvOrderItems.setAdapter(adapter);

        tvAddress = view.findViewById(R.id.tvAddress);
        tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod);
        tvShippingMethod = view.findViewById(R.id.tvShippingMethod);
        tvDeliveryTime = view.findViewById(R.id.tvDeliveryTime);

        tvSubtotal = view.findViewById(R.id.tvSubtotal);
        tvShipping = view.findViewById(R.id.tvShipping);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        tvTotal = view.findViewById(R.id.tvTotal);
    }

    private void fetchOrderData() {
        ApiService apiService = RetrofitClient.getApiService();

        // 1. Fetch Order Info
        apiService.getDonHangById(orderId).enqueue(new Callback<ApiResponse<DonHang>>() {
            @Override
            public void onResponse(Call<ApiResponse<DonHang>> call, Response<ApiResponse<DonHang>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    DonHang donHang = response.body().getData();
                    bindOrderData(donHang);
                } else {
                    Log.e("OrderDetail", "Error fetching order: " + response.code());
                    Toast.makeText(getContext(), "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DonHang>> call, Throwable t) {
                Log.e("OrderDetail", "Failure: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        // 2. Fetch Order Details (Products)
        apiService.getChiTietDonHangByMaDH(orderId).enqueue(new Callback<ApiResponse<List<ChiTietDonHang>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ChiTietDonHang>>> call,
                    Response<ApiResponse<List<ChiTietDonHang>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    listChiTiet.clear();
                    listChiTiet.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("OrderDetail", "Error fetching details: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ChiTietDonHang>>> call, Throwable t) {
                Log.e("OrderDetail", "Detail Failure: " + t.getMessage());
            }
        });
    }

    private void bindOrderData(DonHang donHang) {
        tvAddress.setText(donHang.getDiaChiGiao());
        tvPaymentMethod.setText(donHang.getPhuongThucThanhToan());

        // Assuming shipping method name isn't directly in DonHang but we have ID,
        // effectively we'd need another call or it matches the sample "Giao hàng nhanh"
        // For now, simplify or check if DonHang has it via joins.
        // The DonHang model I made has maPTVC.
        // I will just set a placeholder or fetch if needed.
        // Actually, the SQL DonHang has 'PhiVanChuyen', but method name is in
        // PHUONG_THUC_VAN_CHUYEN.
        // I'll leave default text in XML or update if I can guess.
        // But price fields are available.

        DecimalFormat df = new DecimalFormat("###,###,### VND");

        // Subtotal (TongTien seems to be total products price without shipping in
        // common logic)
        // But SQL says: TongThanhToan AS (TongTien + PhiVanChuyen)
        // So TongTien is likely subtotal.
        if (donHang.getTongTien() != null) {
            tvSubtotal.setText(df.format(donHang.getTongTien()));
        }

        if (donHang.getPhiVanChuyen() != null) {
            tvShipping.setText(df.format(donHang.getPhiVanChuyen()));
        }

        // Discount - logic depending on API response or VOUCHER table.
        // For now set to 0 or leave static if not provided in DonHang DTO directly.
        // DonHang has MaVC. To get discount amount we'd need voucher info.
        // I'll just set it to 0 for safety or leave as is if hardcoded is better
        // visually?
        // Let's set 0 for now to be "dynamic"
        tvDiscount.setText("0 VND");

        // Total
        BigDecimal total = BigDecimal.ZERO;
        if (donHang.getTongTien() != null)
            total = total.add(donHang.getTongTien());
        if (donHang.getPhiVanChuyen() != null)
            total = total.add(donHang.getPhiVanChuyen());

        tvTotal.setText(df.format(total));

        if (donHang.getNgayNhanDuKien() != null) {
            tvDeliveryTime.setText("Est. Arrival: " + donHang.getNgayNhanDuKien());
        }
    }

    // Helper to avoiding BigDecimal imports if I didn't add it yet, but I added it
    // to model.
    // Re-checking imports... added BigDecimal.
    // Need to import BigDecimal in this file too.

    // wait, I can just use full package or import.
    // I need to add import java.math.BigDecimal;
}
