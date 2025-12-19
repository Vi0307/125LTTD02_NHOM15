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
import com.example.quanlyoto.model.Voucher;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Promotion_Applies_Fragment extends Fragment {

    private RecyclerView rcvVouchers;
    private TextView txtNoVoucher;
    private VoucherAdapter voucherAdapter;
    private List<Voucher> voucherList = new ArrayList<>();
    private Voucher selectedVoucher = null;
    private int selectedPosition = -1;

    private ApiService apiService;
    private Integer currentUserId = 1; // Mặc định, cần thay đổi theo logic đăng nhập

    public Promotion_Applies_Fragment() {
        // Public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_promotion_applies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = RetrofitClient.getApiService();

        initViews(view);
        setupRecyclerView();
        setupClickListeners(view);
        loadVouchers();
    }

    private void initViews(View view) {
        rcvVouchers = view.findViewById(R.id.rcvVouchers);
        txtNoVoucher = view.findViewById(R.id.txtNoVoucher);
    }

    private void setupRecyclerView() {
        rcvVouchers.setLayoutManager(new LinearLayoutManager(requireContext()));
        voucherAdapter = new VoucherAdapter();
        rcvVouchers.setAdapter(voucherAdapter);
    }

    private void setupClickListeners(View view) {
        //==================== NÚT BACK ====================//
        ImageView icBack = view.findViewById(R.id.ic_back);
        if (icBack != null) {
            icBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }

        //==================== NÚT ÁP DỤNG ====================//
        Button btnComplete = view.findViewById(R.id.btn_complete_payment_detail);
        if (btnComplete != null) {
            btnComplete.setOnClickListener(v -> {
                if (selectedVoucher == null) {
                    Toast.makeText(requireContext(), "Vui lòng chọn mã khuyến mãi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Truyền voucher đã chọn về Detail_Payment_Fragment
                Detail_Payment_Fragment detailFragment = new Detail_Payment_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("selected_voucher_type", selectedVoucher.getLoaiVoucher());
                bundle.putString("selected_voucher_expiry", selectedVoucher.getHanSuDung());
                if (selectedVoucher.getMaVC() != null) {
                    bundle.putInt("selected_voucher_id", selectedVoucher.getMaVC());
                }
                // Giá trị discount mặc định (có thể cần thêm trường này trong API)
                bundle.putString("selected_voucher_discount", "0");
                detailFragment.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .commit();
            });
        }
    }

    private void loadVouchers() {
        // Gọi API lấy voucher theo user
        apiService.getVoucherByUser(currentUserId).enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    voucherList.clear();
                    voucherList.addAll(response.body());
                    voucherAdapter.notifyDataSetChanged();

                    if (voucherList.isEmpty()) {
                        txtNoVoucher.setVisibility(View.VISIBLE);
                        rcvVouchers.setVisibility(View.GONE);
                    } else {
                        txtNoVoucher.setVisibility(View.GONE);
                        rcvVouchers.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Nếu không có voucher theo user, thử lấy tất cả voucher
                    loadAllVouchers();
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                txtNoVoucher.setVisibility(View.VISIBLE);
                rcvVouchers.setVisibility(View.GONE);
            }
        });
    }

    private void loadAllVouchers() {
        apiService.getAllVouchers().enqueue(new Callback<List<Voucher>>() {
            @Override
            public void onResponse(Call<List<Voucher>> call, Response<List<Voucher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    voucherList.clear();
                    voucherList.addAll(response.body());
                    voucherAdapter.notifyDataSetChanged();

                    if (voucherList.isEmpty()) {
                        txtNoVoucher.setVisibility(View.VISIBLE);
                        rcvVouchers.setVisibility(View.GONE);
                    } else {
                        txtNoVoucher.setVisibility(View.GONE);
                        rcvVouchers.setVisibility(View.VISIBLE);
                    }
                } else {
                    txtNoVoucher.setVisibility(View.VISIBLE);
                    rcvVouchers.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Voucher>> call, Throwable t) {
                txtNoVoucher.setVisibility(View.VISIBLE);
                rcvVouchers.setVisibility(View.GONE);
            }
        });
    }

    // ==================== ADAPTER ====================
    private class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

        @NonNull
        @Override
        public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_voucher, parent, false);
            return new VoucherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
            Voucher voucher = voucherList.get(position);

            // Hiển thị loại voucher
            holder.txtVoucherType.setText(voucher.getLoaiVoucher() != null ? voucher.getLoaiVoucher() : "Voucher " + (position + 1));

            // Hiển thị hạn sử dụng
            if (voucher.getHanSuDung() != null) {
                holder.txtVoucherExpiry.setText("HSD: " + voucher.getHanSuDung());
            } else {
                holder.txtVoucherExpiry.setText("Không giới hạn");
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
                selectedVoucher = voucherList.get(selectedPosition);

                if (oldPosition != -1) {
                    notifyItemChanged(oldPosition);
                }
                notifyItemChanged(selectedPosition);
            });
        }

        @Override
        public int getItemCount() {
            return voucherList.size();
        }

        class VoucherViewHolder extends RecyclerView.ViewHolder {
            TextView txtVoucherType, txtVoucherExpiry;
            ImageView radioButton;

            VoucherViewHolder(@NonNull View itemView) {
                super(itemView);
                txtVoucherType = itemView.findViewById(R.id.txtVoucherType);
                txtVoucherExpiry = itemView.findViewById(R.id.txtVoucherExpiry);
                radioButton = itemView.findViewById(R.id.radioButton);
            }
        }
    }
}

