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
import com.example.quanlyoto.model.DiaChi;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Select_Billing_Address_Fragment extends Fragment {

    private RecyclerView rcvAddresses;
    private TextView txtNoAddress;
    private AddressAdapter addressAdapter;
    private List<DiaChi> addressList = new ArrayList<>();
    private DiaChi selectedAddress = null;
    private int selectedPosition = -1;

    private ApiService apiService;
    private Integer currentUserId;

    public Select_Billing_Address_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_select_billing_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = RetrofitClient.getApiService();
        
        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        currentUserId = prefs.getInt("userId", 1);

        initViews(view);
        setupRecyclerView();
        setupClickListeners(view);
        loadAddresses();
    }

    private void initViews(View view) {
        rcvAddresses = view.findViewById(R.id.rcvAddresses);
        txtNoAddress = view.findViewById(R.id.txtNoAddress);
    }

    private void setupRecyclerView() {
        rcvAddresses.setLayoutManager(new LinearLayoutManager(requireContext()));
        addressAdapter = new AddressAdapter();
        rcvAddresses.setAdapter(addressAdapter);
    }

    private void setupClickListeners(View view) {
        //======================== NÚT BACK ========================//
        ImageView ivBack = view.findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }

        //======================== NÚT ÁP DỤNG ========================//
        Button btnApply = view.findViewById(R.id.btn_apply_address);
        if (btnApply != null) {
            btnApply.setOnClickListener(v -> {
                if (selectedAddress != null) {
                    // Truyền địa chỉ đã chọn về Detail_Payment_Fragment
                    Detail_Payment_Fragment detailFragment = new Detail_Payment_Fragment();
                    Bundle bundle = new Bundle();
                    
                    // Địa chỉ mới chọn
                    bundle.putString("selected_address_type", selectedAddress.getLoaiDiaChi());
                    bundle.putString("selected_address_detail", selectedAddress.getFullAddress());
                    bundle.putString("selected_address_receiver", selectedAddress.getHoTenNguoiNhan());
                    bundle.putString("selected_address_phone", selectedAddress.getSoDienThoai());
                    if (selectedAddress.getMaDiaChi() != null) {
                        bundle.putInt("selected_address_id", selectedAddress.getMaDiaChi());
                    }
                    
                    // Giữ nguyên shipping và voucher từ trước (nếu có)
                    if (getArguments() != null) {
                        if (getArguments().containsKey("current_shipping_name")) {
                            bundle.putString("selected_shipping_name", getArguments().getString("current_shipping_name"));
                            bundle.putString("selected_shipping_fee", getArguments().getString("current_shipping_fee"));
                        }
                        if (getArguments().containsKey("current_voucher_type")) {
                            bundle.putString("selected_voucher_type", getArguments().getString("current_voucher_type"));
                            bundle.putString("selected_voucher_discount", getArguments().getString("current_voucher_discount"));
                        }
                        if (getArguments().containsKey("product_price")) {
                            bundle.putString("product_price", getArguments().getString("product_price"));
                        }
                        // Giữ nguyên danh sách sản phẩm từ giỏ hàng
                        if (getArguments().containsKey("cart_items")) {
                            bundle.putSerializable("cart_items", getArguments().getSerializable("cart_items"));
                        }
                    }
                    
                    detailFragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, detailFragment)
                            .commit();
                } else {
                    Toast.makeText(requireContext(), "Vui lòng chọn một địa chỉ", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //======================== NÚT Thêm địa chỉ ========================//
        Button btnThemDiaChi = view.findViewById(R.id.btn_themDiaChi);
        if (btnThemDiaChi != null) {
            btnThemDiaChi.setOnClickListener(v -> {
                Fragment addNewAddressFragment = new Add_New_Address_Fragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, addNewAddressFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

    private void loadAddresses() {
        apiService.getDiaChiByUser(currentUserId).enqueue(new Callback<List<DiaChi>>() {
            @Override
            public void onResponse(Call<List<DiaChi>> call, Response<List<DiaChi>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressList.clear();
                    addressList.addAll(response.body());
                    addressAdapter.notifyDataSetChanged();

                    if (addressList.isEmpty()) {
                        txtNoAddress.setVisibility(View.VISIBLE);
                        rcvAddresses.setVisibility(View.GONE);
                    } else {
                        txtNoAddress.setVisibility(View.GONE);
                        rcvAddresses.setVisibility(View.VISIBLE);

                        // Tự động chọn địa chỉ mặc định
                        for (int i = 0; i < addressList.size(); i++) {
                            if (Boolean.TRUE.equals(addressList.get(i).getMacDinh())) {
                                selectedPosition = i;
                                selectedAddress = addressList.get(i);
                                addressAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                } else {
                    txtNoAddress.setVisibility(View.VISIBLE);
                    rcvAddresses.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<DiaChi>> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                txtNoAddress.setVisibility(View.VISIBLE);
                rcvAddresses.setVisibility(View.GONE);
            }
        });
    }

    // ==================== ADAPTER ====================
    private class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

        @NonNull
        @Override
        public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_address, parent, false);
            return new AddressViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
            DiaChi address = addressList.get(position);

            // Hiển thị loại địa chỉ
            String addressType = address.getLoaiDiaChi();
            if (addressType != null && !addressType.isEmpty()) {
                holder.txtAddressType.setText(addressType);
            } else {
                holder.txtAddressType.setText("Địa chỉ " + (position + 1));
            }

            // Hiển thị địa chỉ chi tiết
            holder.txtAddressDetail.setText(address.getFullAddress());

            // Hiển thị thông tin người nhận
            String receiverInfo = "";
            if (address.getHoTenNguoiNhan() != null) {
                receiverInfo = address.getHoTenNguoiNhan();
            }
            if (address.getSoDienThoai() != null) {
                if (!receiverInfo.isEmpty()) receiverInfo += " - ";
                receiverInfo += address.getSoDienThoai();
            }
            holder.txtReceiverInfo.setText(receiverInfo);

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
                selectedAddress = addressList.get(selectedPosition);

                if (oldPosition != -1) {
                    notifyItemChanged(oldPosition);
                }
                notifyItemChanged(selectedPosition);
            });
        }

        @Override
        public int getItemCount() {
            return addressList.size();
        }

        class AddressViewHolder extends RecyclerView.ViewHolder {
            TextView txtAddressType, txtAddressDetail, txtReceiverInfo;
            ImageView radioButton;

            AddressViewHolder(@NonNull View itemView) {
                super(itemView);
                txtAddressType = itemView.findViewById(R.id.txtAddressType);
                txtAddressDetail = itemView.findViewById(R.id.txtAddressDetail);
                txtReceiverInfo = itemView.findViewById(R.id.txtReceiverInfo);
                radioButton = itemView.findViewById(R.id.radioButton);
            }
        }
    }
}

