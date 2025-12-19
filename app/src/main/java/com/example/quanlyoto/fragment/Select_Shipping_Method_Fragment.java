package com.example.quanlyoto.fragment;

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
import com.example.quanlyoto.model.PhuongThucVanChuyen;
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

public class Select_Shipping_Method_Fragment extends Fragment {

    private RecyclerView rcvShippingMethods;
    private TextView txtNoShipping;
    private ShippingAdapter shippingAdapter;
    private List<PhuongThucVanChuyen> shippingList = new ArrayList<>();
    private PhuongThucVanChuyen selectedShipping = null;
    private int selectedPosition = -1;

    private ApiService apiService;

    public Select_Shipping_Method_Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_select_shipping_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = RetrofitClient.getApiService();

        initViews(view);
        setupRecyclerView();
        setupClickListeners(view);
        loadShippingMethods();
    }

    private void initViews(View view) {
        rcvShippingMethods = view.findViewById(R.id.rcvShippingMethods);
        txtNoShipping = view.findViewById(R.id.txtNoShipping);
    }

    private void setupRecyclerView() {
        rcvShippingMethods.setLayoutManager(new LinearLayoutManager(requireContext()));
        shippingAdapter = new ShippingAdapter();
        rcvShippingMethods.setAdapter(shippingAdapter);
    }

    private void setupClickListeners(View view) {
        // Quay lại trang trước
        ImageView ivBack = view.findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }

        // Chuyển sang màn Detail Payment với shipping đã chọn
        Button btnApply = view.findViewById(R.id.iv_Promotion_applies);
        if (btnApply != null) {
            btnApply.setOnClickListener(v -> {
                if (selectedShipping == null) {
                    Toast.makeText(getContext(), "Vui lòng chọn phương thức vận chuyển", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Truyền shipping đã chọn về Detail_Payment_Fragment
                Detail_Payment_Fragment detailFragment = new Detail_Payment_Fragment();
                Bundle bundle = new Bundle();
                
                // Shipping mới chọn
                bundle.putString("selected_shipping_name", selectedShipping.getTenPTVC());
                if (selectedShipping.getGiaVanChuyen() != null) {
                    bundle.putString("selected_shipping_fee", selectedShipping.getGiaVanChuyen().toString());
                }
                if (selectedShipping.getSoNgayDuKien() != null) {
                    bundle.putInt("selected_shipping_days", selectedShipping.getSoNgayDuKien());
                }
                if (selectedShipping.getMaPTVC() != null) {
                    bundle.putInt("selected_shipping_id", selectedShipping.getMaPTVC());
                }
                
                // Giữ nguyên địa chỉ và voucher từ trước (nếu có)
                if (getArguments() != null) {
                    if (getArguments().containsKey("current_address_type")) {
                        bundle.putString("selected_address_type", getArguments().getString("current_address_type"));
                        bundle.putString("selected_address_detail", getArguments().getString("current_address_detail"));
                        bundle.putString("selected_address_receiver", getArguments().getString("current_address_receiver"));
                        bundle.putString("selected_address_phone", getArguments().getString("current_address_phone"));
                    }
                    if (getArguments().containsKey("current_voucher_type")) {
                        bundle.putString("selected_voucher_type", getArguments().getString("current_voucher_type"));
                        bundle.putString("selected_voucher_discount", getArguments().getString("current_voucher_discount"));
                    }
                    if (getArguments().containsKey("product_price")) {
                        bundle.putString("product_price", getArguments().getString("product_price"));
                    }
                }
                
                detailFragment.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .commit();
            });
        }
    }

    private void loadShippingMethods() {
        apiService.getAllShipping().enqueue(new Callback<List<PhuongThucVanChuyen>>() {
            @Override
            public void onResponse(Call<List<PhuongThucVanChuyen>> call, Response<List<PhuongThucVanChuyen>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    shippingList.clear();
                    shippingList.addAll(response.body());
                    shippingAdapter.notifyDataSetChanged();

                    if (shippingList.isEmpty()) {
                        txtNoShipping.setVisibility(View.VISIBLE);
                        rcvShippingMethods.setVisibility(View.GONE);
                    } else {
                        txtNoShipping.setVisibility(View.GONE);
                        rcvShippingMethods.setVisibility(View.VISIBLE);
                    }
                } else {
                    txtNoShipping.setVisibility(View.VISIBLE);
                    rcvShippingMethods.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PhuongThucVanChuyen>> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                txtNoShipping.setVisibility(View.VISIBLE);
                rcvShippingMethods.setVisibility(View.GONE);
            }
        });
    }

    // ==================== ADAPTER ====================
    private class ShippingAdapter extends RecyclerView.Adapter<ShippingAdapter.ShippingViewHolder> {

        private final NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        @NonNull
        @Override
        public ShippingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_shipping_method, parent, false);
            return new ShippingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShippingViewHolder holder, int position) {
            PhuongThucVanChuyen shipping = shippingList.get(position);

            // Hiển thị tên phương thức
            holder.txtShippingName.setText(shipping.getTenPTVC() != null ? shipping.getTenPTVC() : "");

            // Hiển thị số ngày dự kiến
            if (shipping.getSoNgayDuKien() != null) {
                holder.txtShippingDays.setText("Dự kiến: " + shipping.getSoNgayDuKien() + " ngày");
            } else {
                holder.txtShippingDays.setText("");
            }

            // Hiển thị giá
            if (shipping.getGiaVanChuyen() != null) {
                holder.txtShippingPrice.setText(formatter.format(shipping.getGiaVanChuyen()) + " VND");
            } else {
                holder.txtShippingPrice.setText("Miễn phí");
            }

            // Hiển thị radio button
            if (selectedPosition == position) {
                holder.radioButton.setImageResource(R.drawable.ic_radio_button);
            } else {
                holder.radioButton.setImageResource(R.drawable.ic_radio_button_unchecked);
            }

            // Click listener
            holder.itemView.setOnClickListener(v -> {
                int oldPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                selectedShipping = shippingList.get(selectedPosition);

                if (oldPosition != -1) {
                    notifyItemChanged(oldPosition);
                }
                notifyItemChanged(selectedPosition);
            });
        }

        @Override
        public int getItemCount() {
            return shippingList.size();
        }

        class ShippingViewHolder extends RecyclerView.ViewHolder {
            TextView txtShippingName, txtShippingDays, txtShippingPrice;
            ImageView radioButton;

            ShippingViewHolder(@NonNull View itemView) {
                super(itemView);
                txtShippingName = itemView.findViewById(R.id.txtShippingName);
                txtShippingDays = itemView.findViewById(R.id.txtShippingDays);
                txtShippingPrice = itemView.findViewById(R.id.txtShippingPrice);
                radioButton = itemView.findViewById(R.id.radioButton);
            }
        }
    }
}

