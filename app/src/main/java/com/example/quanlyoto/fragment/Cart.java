package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.adapter.CartAdapter;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.ChiTietGioHangDTO;
import com.example.quanlyoto.network.RetrofitClient;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends Fragment {

    private RecyclerView rvCartItems;
    private CartAdapter adapter;
    private TextView tvTotal;
    private List<ChiTietGioHangDTO> cartList = new ArrayList<>();
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cart_screen, container, false);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        Button btnCheckout = view.findViewById(R.id.btnCheckout);
        tvTotal = view.findViewById(R.id.tvTotal);
        rvCartItems = view.findViewById(R.id.rvCartItems);

        // Get User ID
        android.content.SharedPreferences prefs = requireActivity()
                .getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE);
        userId = prefs.getInt("userId", -1);

        // Setup RecyclerView
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(getContext(), cartList, new CartAdapter.OnCartItemInteractionListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteItem(position);
            }

            @Override
            public void onIncreaseClick(int position) {
                updateQuantity(position, 1);
            }

            @Override
            public void onDecreaseClick(int position) {
                updateQuantity(position, -1);
            }
        });
        rvCartItems.setAdapter(adapter);

        // Load Data
        if (userId != -1) {
            loadCartData();
        } else {
            android.widget.Toast
                    .makeText(getContext(), "Vui lòng đăng nhập để xem giỏ hàng", android.widget.Toast.LENGTH_SHORT)
                    .show();
        }

        // Back
        btnBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homeparts())
                    .addToBackStack(null)
                    .commit();
        });

        // Checkout
        btnCheckout.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Detail_Payment_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void loadCartData() {
        RetrofitClient.getApiService().getGioHangByNguoiDung(userId)
                .enqueue(new Callback<ApiResponse<List<ChiTietGioHangDTO>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<ChiTietGioHangDTO>>> call,
                            Response<ApiResponse<List<ChiTietGioHangDTO>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            cartList.clear();
                            if (response.body().getData() != null) {
                                cartList.addAll(response.body().getData());
                            }
                            adapter.notifyDataSetChanged();
                            calculateTotal();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<ChiTietGioHangDTO>>> call, Throwable t) {
                        android.widget.Toast.makeText(getContext(), "Lỗi tải giỏ hàng: " + t.getMessage(),
                                android.widget.Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteItem(int position) {
        ChiTietGioHangDTO item = cartList.get(position);
        RetrofitClient.getApiService().xoaKhoiGioHang(item.getMaCTGH())
                .enqueue(new Callback<ApiResponse<Void>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            cartList.remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, cartList.size()); // Refresh positions
                            calculateTotal();
                            android.widget.Toast
                                    .makeText(getContext(), "Đã xóa sản phẩm", android.widget.Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                        android.widget.Toast
                                .makeText(getContext(), "Lỗi xóa sản phẩm", android.widget.Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateQuantity(int position, int delta) {
        ChiTietGioHangDTO item = cartList.get(position);
        int newQuantity = item.getSoLuong() + delta;
        if (newQuantity < 1)
            return; // Minimum 1

        RetrofitClient.getApiService().capNhatSoLuong(item.getMaCTGH(), newQuantity)
                .enqueue(new Callback<ApiResponse<ChiTietGioHangDTO>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<ChiTietGioHangDTO>> call,
                            Response<ApiResponse<ChiTietGioHangDTO>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            item.setSoLuong(newQuantity);
                            adapter.notifyItemChanged(position);
                            calculateTotal();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<ChiTietGioHangDTO>> call, Throwable t) {
                        android.widget.Toast
                                .makeText(getContext(), "Lỗi cập nhật số lượng", android.widget.Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ChiTietGioHangDTO item : cartList) {
            if (item.getDonGia() != null && item.getSoLuong() != null) {
                total = total.add(item.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())));
            }
        }
        DecimalFormat formatter = new DecimalFormat("#,### VND");
        tvTotal.setText(formatter.format(total));
    }
}
