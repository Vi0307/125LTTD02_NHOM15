package com.example.quanlyoto.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.example.quanlyoto.adapter.OrderProductAdapter;
import com.example.quanlyoto.model.ChiTietGioHangDTO;
import com.example.quanlyoto.model.DiaChi;
import com.example.quanlyoto.model.PhuongThucVanChuyen;
import com.example.quanlyoto.model.Voucher;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Payment_Fragment extends Fragment {

    // Views
    private TextView txtAddressType;
    private TextView txtAddressDetail;
    private TextView txtShippingMethod;
    private TextView txtVoucherCode;
    private TextView txtPrice;
    private TextView txtShippingFee;
    private TextView txtDiscount;
    private TextView txtTotal;
    private RecyclerView rvOrderProducts;
    private OrderProductAdapter orderProductAdapter;

    // Data
    private DiaChi selectedAddress;
    private PhuongThucVanChuyen selectedShipping;
    private Voucher selectedVoucher;
    private BigDecimal productPrice = BigDecimal.ZERO;
    private BigDecimal shippingFee = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;
    private List<ChiTietGioHangDTO> cartItems = new ArrayList<>();

    // ID người dùng (cần lấy từ session/shared preferences)
    private Integer currentUserId = 1; // Mặc định, cần thay đổi theo logic đăng nhập

    private ApiService apiService;

    public Detail_Payment_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // Nhận dữ liệu từ Bundle (address, shipping, voucher được chọn từ các fragment
        // khác)
        if (getArguments() != null) {
            // Nhận danh sách sản phẩm từ giỏ hàng
            if (getArguments().containsKey("cart_items")) {
                try {
                    @SuppressWarnings("unchecked")
                    List<ChiTietGioHangDTO> items = (List<ChiTietGioHangDTO>) getArguments()
                            .getSerializable("cart_items");
                    if (items != null) {
                        cartItems.clear();
                        cartItems.addAll(items);
                    }
                } catch (Exception e) {
                    android.util.Log.e("Detail_Payment", "Error loading cart items: " + e.getMessage());
                }
            }

            // Nhận giá sản phẩm
            String priceStr = getArguments().getString("product_price", "0");
            try {
                productPrice = new BigDecimal(priceStr.replaceAll("[^\\d.]", ""));
            } catch (Exception e) {
                productPrice = BigDecimal.ZERO;
            }

            // Nhận địa chỉ đã chọn
            if (getArguments().containsKey("selected_address_type")) {
                selectedAddress = new DiaChi();
                selectedAddress.setLoaiDiaChi(getArguments().getString("selected_address_type"));
                selectedAddress.setDiaChiChiTiet(getArguments().getString("selected_address_detail", ""));
                selectedAddress.setHoTenNguoiNhan(getArguments().getString("selected_address_receiver"));
                selectedAddress.setSoDienThoai(getArguments().getString("selected_address_phone"));
            }

            // Nhận shipping đã chọn
            if (getArguments().containsKey("selected_shipping_name")) {
                selectedShipping = new PhuongThucVanChuyen();
                selectedShipping.setTenPTVC(getArguments().getString("selected_shipping_name"));
                String feeStr = getArguments().getString("selected_shipping_fee", "0");
                try {
                    selectedShipping.setGiaVanChuyen(new BigDecimal(feeStr.replaceAll("[^\\d.]", "")));
                    shippingFee = selectedShipping.getGiaVanChuyen();
                } catch (Exception e) {
                    selectedShipping.setGiaVanChuyen(BigDecimal.ZERO);
                }
                // Lấy ID phương thức vận chuyển
                if (getArguments().containsKey("selected_shipping_id")) {
                    selectedShipping.setMaPTVC(getArguments().getInt("selected_shipping_id"));
                }
            }

            // Nhận voucher đã chọn
            if (getArguments().containsKey("selected_voucher_type")) {
                selectedVoucher = new Voucher();
                selectedVoucher.setLoaiVoucher(getArguments().getString("selected_voucher_type"));
                // Lấy ID voucher
                if (getArguments().containsKey("selected_voucher_id")) {
                    selectedVoucher.setMaVC(getArguments().getInt("selected_voucher_id"));
                }
                // Discount sẽ được tính trong updateUI() dựa trên loại voucher và phí vận
                // chuyển
            }
        }

        return inflater.inflate(R.layout.activity_detail_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = RetrofitClient.getApiService();

        initViews(view);
        setupClickListeners(view);
        setupOrderProductsRecyclerView(view);

        // Chỉ load từ API nếu chưa có địa chỉ được chọn từ Bundle
        if (selectedAddress != null) {
            displayAddress();
        } else {
            loadDefaultAddress();
        }

        updateUI();
    }

    private void initViews(View view) {
        txtAddressType = view.findViewById(R.id.txtAddressType);
        txtAddressDetail = view.findViewById(R.id.txtAddressDetail);
        txtShippingMethod = view.findViewById(R.id.txtShippingMethod);
        txtVoucherCode = view.findViewById(R.id.txtVoucherCode);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtShippingFee = view.findViewById(R.id.txtShippingFee);
        txtDiscount = view.findViewById(R.id.txtDiscount);
        txtTotal = view.findViewById(R.id.txtTotal);
        rvOrderProducts = view.findViewById(R.id.rvOrderProducts);
    }

    /**
     * Setup RecyclerView để hiển thị danh sách sản phẩm từ giỏ hàng
     */
    private void setupOrderProductsRecyclerView(View view) {
        rvOrderProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOrderProducts.setNestedScrollingEnabled(false);
        orderProductAdapter = new OrderProductAdapter(getContext(), cartItems);
        rvOrderProducts.setAdapter(orderProductAdapter);
    }

    private void setupClickListeners(View view) {
        // ==================== NÚT BACK ==================== //
        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Cart())
                    .addToBackStack(null)
                    .commit();
        });

        // ==================== NÚT CHỈNH SỬA ĐỊA CHỈ ==================== //
        ImageView icPen = view.findViewById(R.id.ic_pen);
        icPen.setOnClickListener(v -> {
            Select_Billing_Address_Fragment fragment = new Select_Billing_Address_Fragment();
            fragment.setArguments(createCurrentStateBundle());
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Chuyển sang phương thức vận chuyển
        view.findViewById(R.id.ic_shipping_method).setOnClickListener(v -> {
            Select_Shipping_Method_Fragment fragment = new Select_Shipping_Method_Fragment();
            fragment.setArguments(createCurrentStateBundle());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Chuyển sang áp dụng khuyến mãi
        view.findViewById(R.id.ic_payment_method).setOnClickListener(v -> {
            Promotion_Applies_Fragment fragment = new Promotion_Applies_Fragment();
            fragment.setArguments(createCurrentStateBundle());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Chuyển sang trang phương thức thanh toán
        view.findViewById(R.id.btn_billing_address).setOnClickListener(v -> {
            Payment_Method_Fragment fragment = new Payment_Method_Fragment();
            fragment.setArguments(createCurrentStateBundle());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    /**
     * Tạo Bundle chứa trạng thái hiện tại để truyền sang fragment khác
     */
    private Bundle createCurrentStateBundle() {
        Bundle bundle = new Bundle();

        // Địa chỉ hiện tại
        if (selectedAddress != null) {
            bundle.putString("current_address_type", selectedAddress.getLoaiDiaChi());
            bundle.putString("current_address_detail", selectedAddress.getDiaChiChiTiet());
            bundle.putString("current_address_receiver", selectedAddress.getHoTenNguoiNhan());
            bundle.putString("current_address_phone", selectedAddress.getSoDienThoai());
        }

        // Shipping hiện tại
        if (selectedShipping != null) {
            bundle.putString("current_shipping_name", selectedShipping.getTenPTVC());
            if (selectedShipping.getGiaVanChuyen() != null) {
                bundle.putString("current_shipping_fee", selectedShipping.getGiaVanChuyen().toString());
            }
            if (selectedShipping.getMaPTVC() != null) {
                bundle.putInt("current_shipping_id", selectedShipping.getMaPTVC());
            }
        }

        // Voucher hiện tại
        if (selectedVoucher != null) {
            bundle.putString("current_voucher_type", selectedVoucher.getLoaiVoucher());
            bundle.putString("current_voucher_discount", discount.toString());
            if (selectedVoucher.getMaVC() != null) {
                bundle.putInt("current_voucher_id", selectedVoucher.getMaVC());
            }
        }

        // Giá sản phẩm
        bundle.putString("product_price", productPrice.toString());

        // Danh sách sản phẩm
        bundle.putSerializable("cart_items", new ArrayList<>(cartItems));

        return bundle;
    }

    private void loadDefaultAddress() {
        apiService.getDiaChiMacDinh(currentUserId).enqueue(new Callback<DiaChi>() {
            @Override
            public void onResponse(Call<DiaChi> call, Response<DiaChi> response) {
                if (response.isSuccessful() && response.body() != null) {
                    selectedAddress = response.body();
                    displayAddress();
                } else {
                    txtAddressDetail.setText("Chưa có địa chỉ mặc định");
                }
            }

            @Override
            public void onFailure(Call<DiaChi> call, Throwable t) {
                txtAddressDetail.setText("Lỗi tải địa chỉ");
                Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayAddress() {
        if (selectedAddress != null) {
            // Hiển thị loại địa chỉ
            if (selectedAddress.getLoaiDiaChi() != null && !selectedAddress.getLoaiDiaChi().isEmpty()) {
                txtAddressType.setText(selectedAddress.getLoaiDiaChi());
            } else {
                txtAddressType.setText("Địa chỉ");
            }

            // Hiển thị địa chỉ chi tiết
            // Ưu tiên diaChiChiTiet (từ Bundle đã là địa chỉ đầy đủ)
            String addressToShow = selectedAddress.getDiaChiChiTiet();
            if (addressToShow == null || addressToShow.isEmpty()) {
                // Nếu không có, thử build từ getFullAddress (từ API)
                addressToShow = selectedAddress.getFullAddress();
            }

            if (addressToShow != null && !addressToShow.isEmpty()) {
                txtAddressDetail.setText(addressToShow);
            } else {
                txtAddressDetail.setText("Chưa có địa chỉ");
            }
        }
    }

    private void updateUI() {
        // Hiển thị phương thức vận chuyển đã chọn
        if (selectedShipping != null && selectedShipping.getTenPTVC() != null) {
            txtShippingMethod.setText(selectedShipping.getTenPTVC());
        } else {
            txtShippingMethod.setText("Chọn phương thức vận chuyển");
        }

        // Hiển thị voucher đã chọn và tính khuyến mãi
        if (selectedVoucher != null && selectedVoucher.getLoaiVoucher() != null) {
            txtVoucherCode.setText(selectedVoucher.getLoaiVoucher());

            // Tính discount dựa trên loại voucher
            String voucherType = selectedVoucher.getLoaiVoucher().toLowerCase();
            if (voucherType.contains("miễn phí vận chuyển") || voucherType.contains("mien phi van chuyen")) {
                // Miễn phí vận chuyển = giảm 100% phí vận chuyển
                discount = shippingFee;
            } else if (voucherType.contains("giảm 50") || voucherType.contains("giam 50")) {
                // Giảm 50% phí vận chuyển
                discount = shippingFee.multiply(new BigDecimal("0.5"));
            } else {
                // Các loại voucher khác - giữ mặc định
                discount = BigDecimal.ZERO;
            }
        } else {
            txtVoucherCode.setText("Chọn mã khuyến mãi");
            discount = BigDecimal.ZERO;
        }

        // Hiển thị giá
        updatePriceDisplay();
    }

    private void updatePriceDisplay() {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        // Giá sản phẩm
        txtPrice.setText(formatter.format(productPrice) + " VND");

        // Phí vận chuyển
        if (shippingFee.compareTo(BigDecimal.ZERO) > 0) {
            txtShippingFee.setText(formatter.format(shippingFee) + " VND");
        } else {
            txtShippingFee.setText("-");
        }

        // Khuyến mãi
        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            txtDiscount.setText("-" + formatter.format(discount) + " VND");
        } else {
            txtDiscount.setText("-");
        }

        // Tổng cộng
        BigDecimal total = productPrice.add(shippingFee).subtract(discount);
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }
        txtTotal.setText(formatter.format(total) + " VND");
    }
}