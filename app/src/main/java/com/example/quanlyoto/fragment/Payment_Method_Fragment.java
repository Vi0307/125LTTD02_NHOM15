package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.adapter.PaymentMethodAdapter;
import com.example.quanlyoto.model.PhuongThucThanhToan;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment_Method_Fragment extends Fragment {

    private RecyclerView recyclerPaymentMethods;
    private ProgressBar progressLoading;
    private PaymentMethodAdapter adapter;

    private ApiService apiService;
    private List<PhuongThucThanhToan> paymentMethods;
    private PhuongThucThanhToan selectedMethod = null;

    public Payment_Method_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_payment_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = RetrofitClient.getApiService();

        initViews(view);
        setupClickListeners(view);
        loadPaymentMethods();
    }

    private void initViews(View view) {
        recyclerPaymentMethods = view.findViewById(R.id.recycler_payment_methods);
        progressLoading = view.findViewById(R.id.progress_loading);

        // Setup RecyclerView
        recyclerPaymentMethods.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupClickListeners(View view) {
        ImageView icBack = view.findViewById(R.id.ic_back_complete_payment);
        Button btnApply = view.findViewById(R.id.btn_apply);

        // Back - quay về trang đơn hàng
        icBack.setOnClickListener(v -> {
            Detail_Payment_Fragment fragment = new Detail_Payment_Fragment();
            if (getArguments() != null) {
                fragment.setArguments(getArguments());
            }
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Apply → chuyển Orderconfirm với phương thức thanh toán đã chọn
        btnApply.setOnClickListener(v -> {
            // Lấy phương thức đã chọn từ adapter
            if (adapter != null) {
                selectedMethod = adapter.getSelectedMethod();
            }

            if (selectedMethod == null && paymentMethods != null && !paymentMethods.isEmpty()) {
                // Mặc định chọn phương thức đầu tiên nếu chưa chọn
                selectedMethod = paymentMethods.get(0);
            }

            Orderconfirm orderConfirmFragment = new Orderconfirm();
            Bundle bundle = new Bundle();

            // Truyền dữ liệu từ trang trước
            if (getArguments() != null) {
                bundle.putAll(getArguments());
            }

            // Thêm phương thức thanh toán đã chọn
            if (selectedMethod != null) {
                bundle.putString("selected_payment_method", selectedMethod.getTenPTTT());
                bundle.putInt("selected_payment_method_id", selectedMethod.getMaPTTT());
            } else {
                bundle.putString("selected_payment_method", "Tiền mặt");
            }

            orderConfirmFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, orderConfirmFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadPaymentMethods() {
        // Hiển thị loading
        progressLoading.setVisibility(View.VISIBLE);
        recyclerPaymentMethods.setVisibility(View.GONE);

        apiService.getAllPaymentMethods().enqueue(new Callback<List<PhuongThucThanhToan>>() {
            @Override
            public void onResponse(Call<List<PhuongThucThanhToan>> call, Response<List<PhuongThucThanhToan>> response) {
                // Ẩn loading
                progressLoading.setVisibility(View.GONE);
                recyclerPaymentMethods.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    paymentMethods = response.body();

                    // Setup adapter với dữ liệu từ API
                    adapter = new PaymentMethodAdapter(getContext(), paymentMethods);
                    recyclerPaymentMethods.setAdapter(adapter);

                    // Set listener để cập nhật selectedMethod khi user chọn
                    adapter.setOnPaymentMethodSelectedListener((method, position) -> {
                        selectedMethod = method;
                    });

                    // Tự động chọn phương thức mặc định
                    for (int i = 0; i < paymentMethods.size(); i++) {
                        PhuongThucThanhToan method = paymentMethods.get(i);
                        if (Boolean.TRUE.equals(method.getMacDinh())) {
                            selectedMethod = method;
                            adapter.setSelectedPosition(i);
                            break;
                        }
                    }

                    // Nếu không có mặc định, chọn phương thức đầu tiên
                    if (selectedMethod == null && !paymentMethods.isEmpty()) {
                        selectedMethod = paymentMethods.get(0);
                    }
                } else {
                    Toast.makeText(getContext(), "Không thể tải phương thức thanh toán", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PhuongThucThanhToan>> call, Throwable t) {
                progressLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
