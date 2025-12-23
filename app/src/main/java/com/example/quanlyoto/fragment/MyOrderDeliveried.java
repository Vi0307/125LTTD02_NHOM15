package com.example.quanlyoto.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.adapter.DonHangAdapter;
import com.example.quanlyoto.model.DonHang;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderDeliveried extends Fragment implements DonHangAdapter.OnOrderClickListener {

    private static final String TAG = "MyOrderDeliveried";
    
    private RecyclerView recyclerOrders;
    private LinearLayout layoutEmpty;
    private ProgressBar progressBar;
    private DonHangAdapter adapter;
    private List<DonHang> orderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorder_deliveried_content, container, false);

        // Initialize views
        recyclerOrders = view.findViewById(R.id.recycler_orders);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        progressBar = view.findViewById(R.id.progress_bar);

        // Setup RecyclerView
        recyclerOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DonHangAdapter(getContext(), orderList, this);
        recyclerOrders.setAdapter(adapter);

        // Load orders
        loadDeliveredOrders();

        return view;
    }

    private void loadDeliveredOrders() {
        // Get user ID from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int maND = prefs.getInt("maND", -1);

        if (maND == -1) {
            Log.e(TAG, "User not logged in");
            showEmptyState();
            return;
        }

        showLoading();

        ApiService apiService = RetrofitClient.getApiService();
        Call<List<DonHang>> call = apiService.getDeliveredOrders(maND);

        call.enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                hideLoading();
                
                if (response.isSuccessful() && response.body() != null) {
                    List<DonHang> orders = response.body();
                    Log.d(TAG, "Loaded " + orders.size() + " delivered orders");
                    
                    if (orders.isEmpty()) {
                        showEmptyState();
                    } else {
                        showOrders(orders);
                    }
                } else {
                    Log.e(TAG, "Error: " + response.code());
                    showEmptyState();
                }
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                hideLoading();
                Log.e(TAG, "Network error: " + t.getMessage());
                showEmptyState();
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerOrders.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showEmptyState() {
        recyclerOrders.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
    }

    private void showOrders(List<DonHang> orders) {
        orderList.clear();
        orderList.addAll(orders);
        adapter.notifyDataSetChanged();
        recyclerOrders.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onOrderClick(DonHang donHang) {
        // Navigate to order detail
        Order_Detail_Fragment fragment = new Order_Detail_Fragment();
        Bundle args = new Bundle();
        args.putString("orderId", donHang.getMaDH());
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
