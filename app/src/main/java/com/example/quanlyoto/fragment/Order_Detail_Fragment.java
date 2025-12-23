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
        apiService.getDonHangById(orderId).enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DonHang donHang = response.body();
                    bindOrderData(donHang);
                } else {
                    Log.e("OrderDetail", "Error fetching order: " + response.code());
                    Toast.makeText(getContext(), "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                Log.e("OrderDetail", "Failure: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        // 2. Fetch Order Details (Products)
        apiService.getChiTietDonHangByMaDH(orderId).enqueue(new Callback<List<ChiTietDonHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonHang>> call,
                    Response<List<ChiTietDonHang>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    listChiTiet.clear();
                    listChiTiet.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("OrderDetail", "No order details found, will use order info");
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietDonHang>> call, Throwable t) {
                Log.e("OrderDetail", "Detail Failure: " + t.getMessage());
            }
        });
    }

    /**
     * Tạo ChiTietDonHang từ thông tin DonHang khi không có chi tiết riêng
     */
    private void createChiTietFromDonHang(DonHang donHang) {
        if (donHang.getTenPhuTung() != null && !donHang.getTenPhuTung().isEmpty()) {
            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setMaDH(donHang.getMaDH());
            chiTiet.setTenPhuTung(donHang.getTenPhuTung());
            chiTiet.setHinhAnh(donHang.getHinhAnh());
            chiTiet.setSoLuong(1);
            chiTiet.setGiaTien(donHang.getTongTien());
            
            listChiTiet.clear();
            listChiTiet.add(chiTiet);
            adapter.notifyDataSetChanged();
        }
    }

    private void bindOrderData(DonHang donHang) {
        tvAddress.setText(donHang.getDiaChiGiao());
        tvPaymentMethod.setText(donHang.getPhuongThucThanhToan());

        DecimalFormat df = new DecimalFormat("###,###,### VND");

        // Subtotal
        if (donHang.getTongTien() != null) {
            tvSubtotal.setText(df.format(donHang.getTongTien()));
        }

        if (donHang.getPhiVanChuyen() != null) {
            tvShipping.setText(df.format(donHang.getPhiVanChuyen()));
        }

        // Discount - set to 0 for now
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

        // Hiển thị sản phẩm từ thông tin đơn hàng nếu chưa có chi tiết
        if (listChiTiet.isEmpty()) {
            createChiTietFromDonHang(donHang);
        }
    }

    // Helper to avoiding BigDecimal imports if I didn't add it yet, but I added it
    // to model.
    // Re-checking imports... added BigDecimal.
    // Need to import BigDecimal in this file too.

    // wait, I can just use full package or import.
    // I need to add import java.math.BigDecimal;
}
