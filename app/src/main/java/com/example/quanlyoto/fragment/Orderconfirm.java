package com.example.quanlyoto.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.adapter.OrderProductAdapter;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.ChiTietGioHangDTO;
import com.example.quanlyoto.model.DonHangRequest;
import com.example.quanlyoto.model.DonHangResponse;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orderconfirm extends Fragment {

    // Views
    private TextView tvSubtotal, tvShipping, tvDiscount, tvTotal;
    private TextView tvAddressType, tvAddress;
    private TextView tvPaymentMethod;
    private TextView tvShippingMethod, tvDeliveryTime;
    private RecyclerView rvProducts;
    private OrderProductAdapter productAdapter;

    // Data
    private List<ChiTietGioHangDTO> cartItems;
    private String productPrice = "0";
    private String shippingFee = "0";
    private String discount = "0";
    private String voucherType = "";
    private String selectedAddress = "";
    private String selectedAddressType = "Nhà riêng";
    private String selectedShippingName = "Giao hàng nhanh";
    private String selectedPaymentMethod = "Tiền mặt";
    private Integer selectedPaymentMethodId = 1; // Mặc định là Tiền mặt (ID=1)
    private Integer selectedVoucherId = null; // Mã voucher
    private Integer selectedShippingMethodId = null; // Mã phương thức vận chuyển

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orderconfirm_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        loadDataFromArguments();
        setupProductsRecyclerView();
        updateUI();
        setupClickListeners(view);
    }

    private void initViews(View view) {
        // Products RecyclerView
        rvProducts = view.findViewById(R.id.rvProducts);

        // Price details
        tvSubtotal = view.findViewById(R.id.tvSubtotal);
        tvShipping = view.findViewById(R.id.tvShipping);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        tvTotal = view.findViewById(R.id.tvTotal);

        // Address
        tvAddressType = view.findViewById(R.id.tvAddressType);
        tvAddress = view.findViewById(R.id.tvAddress);

        // Payment method
        tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod);

        // Shipping method
        tvShippingMethod = view.findViewById(R.id.tvShippingMethod);
        tvDeliveryTime = view.findViewById(R.id.tvDeliveryTime);
    }

    private void setupProductsRecyclerView() {
        if (cartItems != null && !cartItems.isEmpty()) {
            rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
            productAdapter = new OrderProductAdapter(getContext(), cartItems);
            rvProducts.setAdapter(productAdapter);
        }
    }

    private void loadDataFromArguments() {
        Bundle args = getArguments();
        if (args == null)
            return;

        // Lấy danh sách sản phẩm
        Serializable cartSerializable = args.getSerializable("cart_items");
        if (cartSerializable instanceof ArrayList) {
            cartItems = (ArrayList<ChiTietGioHangDTO>) cartSerializable;
        }

        // Lấy giá sản phẩm
        if (args.containsKey("product_price")) {
            productPrice = args.getString("product_price", "0");
        }

        // Lấy phí vận chuyển (kiểm tra cả 2 key)
        if (args.containsKey("current_shipping_fee")) {
            shippingFee = args.getString("current_shipping_fee", "0");
        } else if (args.containsKey("selected_shipping_fee")) {
            shippingFee = args.getString("selected_shipping_fee", "0");
        }

        // Lấy loại voucher
        if (args.containsKey("current_voucher_type")) {
            voucherType = args.getString("current_voucher_type", "");
        }

        // Tính lại discount dựa trên loại voucher và phí vận chuyển
        double shippingFeeValue = parsePrice(shippingFee);
        if (voucherType != null && !voucherType.isEmpty()) {
            String voucherLower = voucherType.toLowerCase();
            if (voucherLower.contains("miễn phí vận chuyển") || voucherLower.contains("mien phi van chuyen")) {
                // Miễn phí vận chuyển = 100% phí vận chuyển
                discount = String.valueOf(shippingFeeValue);
            } else if (voucherLower.contains("giảm 50") || voucherLower.contains("giam 50")) {
                // Giảm 50% phí vận chuyển
                discount = String.valueOf(shippingFeeValue * 0.5);
            } else {
                discount = "0";
            }
        } else {
            // Nếu không có voucher type, thử đọc từ discount đã truyền
            if (args.containsKey("current_voucher_discount")) {
                discount = args.getString("current_voucher_discount", "0");
            } else if (args.containsKey("discount")) {
                discount = args.getString("discount", "0");
            }
        }

        // Lấy địa chỉ (kiểm tra cả 2 key)
        if (args.containsKey("current_address_detail")) {
            selectedAddress = args.getString("current_address_detail", "");
        } else if (args.containsKey("selected_address_detail")) {
            selectedAddress = args.getString("selected_address_detail", "");
        }

        if (args.containsKey("current_address_type")) {
            selectedAddressType = args.getString("current_address_type", "Nhà riêng");
        } else if (args.containsKey("selected_address_type")) {
            selectedAddressType = args.getString("selected_address_type", "Nhà riêng");
        }

        // Lấy phương thức vận chuyển (kiểm tra cả 2 key)
        if (args.containsKey("current_shipping_name")) {
            selectedShippingName = args.getString("current_shipping_name", "Giao hàng nhanh");
        } else if (args.containsKey("selected_shipping_name")) {
            selectedShippingName = args.getString("selected_shipping_name", "Giao hàng nhanh");
        }
        if (args.containsKey("current_shipping_id")) {
            selectedShippingMethodId = args.getInt("current_shipping_id", 0);
            if (selectedShippingMethodId == 0)
                selectedShippingMethodId = null;
        } else if (args.containsKey("selected_shipping_id")) {
            selectedShippingMethodId = args.getInt("selected_shipping_id", 0);
            if (selectedShippingMethodId == 0)
                selectedShippingMethodId = null;
        }

        // Lấy phương thức thanh toán
        if (args.containsKey("selected_payment_method")) {
            selectedPaymentMethod = args.getString("selected_payment_method", "Tiền mặt");
        }
        if (args.containsKey("selected_payment_method_id")) {
            selectedPaymentMethodId = args.getInt("selected_payment_method_id", 1);
        }

        // Lấy voucher
        if (args.containsKey("current_voucher_id")) {
            selectedVoucherId = args.getInt("current_voucher_id", 0);
            if (selectedVoucherId == 0)
                selectedVoucherId = null;
        } else if (args.containsKey("selected_voucher_id")) {
            selectedVoucherId = args.getInt("selected_voucher_id", 0);
            if (selectedVoucherId == 0)
                selectedVoucherId = null;
        }
    }

    private void updateUI() {
        // Hiển thị giá sản phẩm
        double productPriceValue = parsePrice(productPrice);
        double shippingFeeValue = parsePrice(shippingFee);
        double discountValue = parsePrice(discount);
        double totalValue = productPriceValue + shippingFeeValue - discountValue;

        // if (tvPrice != null) {
        // tvPrice.setText(formatPrice(productPriceValue));
        // }
        if (tvSubtotal != null) {
            tvSubtotal.setText(formatPrice(productPriceValue));
        }
        if (tvShipping != null) {
            tvShipping.setText(formatPrice(shippingFeeValue));
        }
        if (tvDiscount != null) {
            if (discountValue > 0) {
                tvDiscount.setText("-" + formatPrice(discountValue));
            } else {
                tvDiscount.setText("0 VNĐ");
            }
        }
        if (tvTotal != null) {
            tvTotal.setText(formatPrice(totalValue));
        }

        // Hiển thị địa chỉ
        if (tvAddressType != null) {
            tvAddressType.setText(selectedAddressType);
        }
        if (tvAddress != null) {
            tvAddress.setText(selectedAddress.isEmpty() ? "Chưa chọn địa chỉ" : selectedAddress);
        }

        // Hiển thị phương thức thanh toán
        if (tvPaymentMethod != null) {
            tvPaymentMethod.setText(selectedPaymentMethod);
        }

        // Hiển thị phương thức vận chuyển
        if (tvShippingMethod != null) {
            tvShippingMethod.setText(selectedShippingName);
        }
        if (tvDeliveryTime != null) {
            // Tính ngày dự kiến nhận hàng
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.DAY_OF_MONTH, 3);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            tvDeliveryTime.setText("Dự kiến: " + sdf.format(cal.getTime()));
        }
    }

    private void setupClickListeners(View view) {
        Button btnComplete = view.findViewById(R.id.btnComplete);
        ImageView btnBack = view.findViewById(R.id.btnBack);

        // BACK → quay lại Payment_Method_Fragment
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // COMPLETE → Tạo đơn hàng và chuyển sang Ordersuccess
        btnComplete.setOnClickListener(v -> {
            createOrderAndNavigate(btnComplete);
        });
    }

    private void createOrderAndNavigate(Button btnComplete) {
        // Disable button để tránh click nhiều lần
        btnComplete.setEnabled(false);
        btnComplete.setText("Đang xử lý...");

        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", 1);

        // Tạo request
        DonHangRequest request = new DonHangRequest();

        // Tên sản phẩm và hình ảnh
        if (cartItems != null && !cartItems.isEmpty()) {
            ChiTietGioHangDTO firstItem = cartItems.get(0);
            request.setTenPhuTung(firstItem.getTenPhuTung());
            request.setHinhAnh(firstItem.getHinhAnh() != null ? firstItem.getHinhAnh() : "default.png");
        } else {
            request.setTenPhuTung("Sản phẩm");
            request.setHinhAnh("default.png");
        }

        // Giá tiền
        double productPriceValue = parsePrice(productPrice);
        double shippingFeeValue = parsePrice(shippingFee);
        request.setTongTien(BigDecimal.valueOf(productPriceValue));
        request.setPhiVanChuyen(BigDecimal.valueOf(shippingFeeValue));

        // Địa chỉ giao
        request.setDiaChiGiao(selectedAddress.isEmpty() ? "Chưa có địa chỉ" : selectedAddress);

        // Mã người dùng
        request.setMaND(userId);

        // Phương thức thanh toán
        request.setMaPTTT(selectedPaymentMethodId);
        request.setPhuongThucThanhToan(selectedPaymentMethod);

        // Voucher và phương thức vận chuyển
        request.setMaVC(selectedVoucherId);
        request.setMaPTVC(selectedShippingMethodId);

        // Gọi API
        ApiService apiService = RetrofitClient.getApiService();
        apiService.createOrder(request).enqueue(new Callback<DonHangResponse>() {
            @Override
            public void onResponse(Call<DonHangResponse> call, Response<DonHangResponse> response) {
                btnComplete.setEnabled(true);
                btnComplete.setText("Hoàn tất thanh toán");

                if (response.isSuccessful() && response.body() != null) {
                    DonHangResponse order = response.body();
                    Toast.makeText(getContext(), "Đặt hàng thành công! Mã: " + order.getMaDH(), Toast.LENGTH_SHORT)
                            .show();

                    // Xóa giỏ hàng sau khi đặt hàng thành công
                    clearCart(apiService);

                    // Chuyển sang Ordersuccess
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Ordersuccess())
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Đặt hàng thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonHangResponse> call, Throwable t) {
                btnComplete.setEnabled(true);
                btnComplete.setText("Hoàn tất thanh toán");
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Xóa tất cả sản phẩm trong giỏ hàng sau khi đặt hàng thành công
     */
    private void clearCart(ApiService apiService) {
        if (cartItems == null || cartItems.isEmpty())
            return;

        for (ChiTietGioHangDTO item : cartItems) {
            if (item.getMaCTGH() != null) {
                apiService.xoaKhoiGioHang(item.getMaCTGH()).enqueue(new Callback<ApiResponse<Void>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                        // Không cần xử lý kết quả, chỉ cần xóa
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                        // Bỏ qua lỗi xóa giỏ hàng
                    }
                });
            }
        }
    }

    private double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty())
            return 0;
        try {
            // Thử parse trực tiếp trước (cho trường hợp số đơn giản như "250000" hoặc
            // "250000.0")
            priceStr = priceStr.trim();

            // Nếu có dấu . và sau dấu . có 1-2 số thì đó là decimal (250000.0 hoặc
            // 250000.50)
            if (priceStr.contains(".") && priceStr.indexOf(".") > 0) {
                String afterDot = priceStr.substring(priceStr.lastIndexOf(".") + 1);
                if (afterDot.length() <= 2 || afterDot.matches("0+")) {
                    // Đây là decimal notation, parse trực tiếp
                    return Double.parseDouble(priceStr.replaceAll("[^0-9.]", ""));
                }
            }

            // Nếu không có dấu . hoặc dấu . là phân cách hàng nghìn (VD: 500.000)
            // Loại bỏ dấu . và , (phân cách hàng nghìn)
            String cleanPrice = priceStr.replaceAll("[^0-9]", "");
            return Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(price) + " VNĐ";
    }
}
