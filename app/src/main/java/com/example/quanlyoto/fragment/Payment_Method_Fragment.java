package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.PhuongThucThanhToan;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment_Method_Fragment extends Fragment {

    private ImageView radioCash, radioApple, radioBank;
    private TextView txtCash, txtApple, txtBank;
    private LinearLayout layoutCash, layoutApple, layoutBank;
    
    private ApiService apiService;
    private List<PhuongThucThanhToan> paymentMethods;
    private PhuongThucThanhToan selectedMethod = null;
    private String selectedType = "cash"; // default

    public Payment_Method_Fragment() {}

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
        // RADIO ICONS
        radioCash = view.findViewById(R.id.radio_cash);
        radioApple = view.findViewById(R.id.radio_apple);
        radioBank = view.findViewById(R.id.radio_bank);

        // LAYOUTS CLICKABLE
        layoutCash = view.findViewById(R.id.layout_cash);
        layoutApple = view.findViewById(R.id.layout_applepay);
        layoutBank = view.findViewById(R.id.layout_bank);
    }

    private void setupClickListeners(View view) {
        ImageView icBack = view.findViewById(R.id.ic_back_complete_payment);
        Button btnApply = view.findViewById(R.id.btn_apply);

        // ------- HANDLE RADIO SELECT -------
        layoutCash.setOnClickListener(v -> selectMethod("cash", 0));
        layoutApple.setOnClickListener(v -> selectMethod("apple", 1));
        layoutBank.setOnClickListener(v -> selectMethod("bank", 2));

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
            if (selectedMethod == null && paymentMethods != null && !paymentMethods.isEmpty()) {
                // Mặc định chọn Tiền mặt nếu chưa chọn
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
        apiService.getAllPaymentMethods().enqueue(new Callback<List<PhuongThucThanhToan>>() {
            @Override
            public void onResponse(Call<List<PhuongThucThanhToan>> call, Response<List<PhuongThucThanhToan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    paymentMethods = response.body();
                    
                    // Tự động chọn phương thức mặc định
                    for (PhuongThucThanhToan method : paymentMethods) {
                        if (Boolean.TRUE.equals(method.getMacDinh())) {
                            selectedMethod = method;
                            // Hiển thị radio đã chọn
                            if ("Tiền mặt".equals(method.getTenPTTT())) {
                                selectMethod("cash", 0);
                            } else if ("Apple Pay".equals(method.getTenPTTT())) {
                                selectMethod("apple", 1);
                            } else {
                                selectMethod("bank", 2);
                            }
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PhuongThucThanhToan>> call, Throwable t) {
                Toast.makeText(getContext(), "Không thể tải phương thức thanh toán", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Chỉ chọn 1 radio
    private void selectMethod(String type, int index) {
        selectedType = type;
        
        // Cập nhật selectedMethod từ danh sách
        if (paymentMethods != null && index < paymentMethods.size()) {
            selectedMethod = paymentMethods.get(index);
        }

        radioCash.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioApple.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioBank.setImageResource(R.drawable.ic_radio_button_unchecked);

        switch (type) {
            case "cash":
                radioCash.setImageResource(R.drawable.ic_radio_button);
                break;
            case "apple":
                radioApple.setImageResource(R.drawable.ic_radio_button);
                break;
            case "bank":
                radioBank.setImageResource(R.drawable.ic_radio_button);
                break;
        }
    }
}
