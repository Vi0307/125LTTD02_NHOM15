package com.example.quanlyoto.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.DichVuDTO;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentSee_Fragment extends Fragment {

    private RecyclerView rcvServices;
    private TextView tvEmpty;
    private ServiceAdapter adapter;
    private List<DichVuDTO> serviceList = new ArrayList<>();
    private ImageView btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_see_appointment, container, false);

        // Init views
        rcvServices = view.findViewById(R.id.rcvServices);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btnBack = view.findViewById(R.id.btn_back);

        // Setup RecyclerView
        rcvServices.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ServiceAdapter(getContext(), serviceList);
        rcvServices.setAdapter(adapter);

        // Back button
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Fetch data
        loadServices();

        return view;
    }

    private void loadServices() {
        // Get user ID (Assuming stored in SharedPreferences as per PersonalActivity)
        android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("UserPrefs",
                android.content.Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getDichVuByNguoiDung(userId).enqueue(new Callback<ApiResponse<List<DichVuDTO>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DichVuDTO>>> call,
                    Response<ApiResponse<List<DichVuDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DichVuDTO> data = response.body().getData();
                    if (data != null && !data.isEmpty()) {
                        serviceList.clear();
                        serviceList.addAll(data);
                        adapter.notifyDataSetChanged();
                        rcvServices.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        showEmpty();
                    }
                } else {
                    showEmpty();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DichVuDTO>>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                showEmpty();
            }
        });
    }

    private void showEmpty() {
        rcvServices.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    // Inner Adapter Class
    public static class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

        private Context context;
        private List<DichVuDTO> list;

        public ServiceAdapter(Context context, List<DichVuDTO> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Using a simple card layout programmatically or reusing existing simple item
            // layout if available
            // Creating a simple item layout programmatically for simplicity if needed,
            // but for better practice let's assume we can create one or use a generic card.
            // Let's inflate a custom layout for the item. Since I didn't create a layout
            // file for the item,
            // I'll create a simple CardView layout programmatically or use a simple
            // existing one.
            // Actually, I can define the item layout inside onCreateViewHolder dynamically
            // to avoid creating another file if user didn't ask,
            // or better yet, create a layout file for item_service.xml properly.
            // For now, I'll inflate a simple layout defined here (creating a file is
            // safer).
            View view = LayoutInflater.from(context).inflate(R.layout.item_service_history, parent, false);
            return new ServiceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
            DichVuDTO item = list.get(position);
            holder.tvType.setText(item.getLoaiDichVu());
            holder.tvDetail.setText(item.getMoTa());
            holder.tvDate.setText(formatDate(item.getNgayTao()));

            // Status color based on trangThai if available, else default
            String status = "Đang xử lý"; // Default
            if (item.getTrangThai() != null) {
                status = item.getTrangThai();
            }
            holder.tvStatus.setText(status);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private String formatDate(String isoDate) {
            if (isoDate == null)
                return "";
            try {
                // Parse ISO 8601
                if (isoDate.contains("T")) {
                    return isoDate.replace("T", " ");
                }
                return isoDate;
            } catch (Exception e) {
                return isoDate;
            }
        }

        public static class ServiceViewHolder extends RecyclerView.ViewHolder {
            TextView tvType, tvDetail, tvDate, tvStatus;

            public ServiceViewHolder(@NonNull View itemView) {
                super(itemView);
                tvType = itemView.findViewById(R.id.tvServiceType);
                tvDetail = itemView.findViewById(R.id.tvServiceDetail);
                tvDate = itemView.findViewById(R.id.tvServiceDate);
                tvStatus = itemView.findViewById(R.id.tvServiceStatus);
            }
        }
    }
}
