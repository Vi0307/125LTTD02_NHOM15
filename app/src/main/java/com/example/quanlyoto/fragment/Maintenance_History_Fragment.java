package com.example.quanlyoto.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlyoto.Maintenance_Record;
import com.example.quanlyoto.R;

import java.util.ArrayList;
import java.util.List;

public class Maintenance_History_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Maintenance_Record> recordList;

    public Maintenance_History_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_maintenance_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu
        recordList = new ArrayList<>();
        recordList.add(new Maintenance_Record("TƯỜNG PHÁT 1", "02/09/2025", "6,256"));
        recordList.add(new Maintenance_Record("TƯỜNG PHÁT 1", "16/02/2025", "3,986"));
        recordList.add(new Maintenance_Record("TƯỜNG PHÁT 1", "07/09/2024", "2,504"));
        recordList.add(new Maintenance_Record("THẢO ÁI", "30/09/2023", "307"));

        // Gắn adapter
        MaintenanceHistoryAdapter adapter = new MaintenanceHistoryAdapter(recordList, this);
        recyclerView.setAdapter(adapter);

        // BOTTOM NAV — HOME
        view.findViewById(R.id.navHome).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // BOTTOM NAV — PARTS
        view.findViewById(R.id.navParts).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homeparts())
                    .commit();
        });

        // BOTTOM NAV — VOUCHER
        view.findViewById(R.id.navVoucher).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new VoucherStillValid())
                    .commit();
        });

        // BOTTOM NAV — AGENCY
        view.findViewById(R.id.navAgency).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Agency_Fragment())
                    .addToBackStack(null)
                    .commit();
        });

        //==================== XỬ LÝ NÚT BACK ====================//
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }

        return view;
    }

    //================= ADAPTER ĐƯỢC GỘP TRONG FRAGMENT =================//
    public static class MaintenanceHistoryAdapter extends RecyclerView.Adapter<MaintenanceHistoryAdapter.ViewHolder> {

        private List<Maintenance_Record> recordList;
        private Fragment fragment;

        public MaintenanceHistoryAdapter(List<Maintenance_Record> recordList, Fragment fragment) {
            this.recordList = recordList;
            this.fragment = fragment;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_maintenance_history, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Maintenance_Record record = recordList.get(position);

            holder.tvDealer.setText("Đại lý: " + record.getDealer());
            holder.tvDate.setText(record.getDate());
            holder.tvKm.setText(record.getKm());

            // Xử lý chuyển qua fragment chi tiết
            holder.arrow.setOnClickListener(v -> {

                Maintenance_Detail_Fragment detailFragment = new Maintenance_Detail_Fragment();

                Bundle bundle = new Bundle();
                bundle.putString("dealer", record.getDealer());
                bundle.putString("date", record.getDate());
                bundle.putString("km", record.getKm());
                detailFragment.setArguments(bundle);

                fragment.getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        @Override
        public int getItemCount() {
            return recordList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvDealer, tvDate, tvKm;
            ImageView arrow;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDealer = itemView.findViewById(R.id.tvDealer);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvKm = itemView.findViewById(R.id.tvKm);
                arrow = itemView.findViewById(R.id.manitenace_history_detail);
            }
        }
    }
}

