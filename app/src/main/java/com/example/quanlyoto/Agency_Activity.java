package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Agency_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AgencyAdapter agencyAdapter;
    private final List<Agency> agencyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency);

        setupBackButton();
        setupRecyclerView();
    }

    private void setupBackButton() {
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Agency_Activity.this, Maintenance_Detail.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.rcvAgencies);
        if (recyclerView == null) return;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        populateDummyAgencies();
        agencyAdapter = new AgencyAdapter(agencyList);
        recyclerView.setAdapter(agencyAdapter);
    }

    private void populateDummyAgencies() {
        agencyList.clear();
        agencyList.add(new Agency("DATRACO 1", "4.9", "3.1k đánh giá", "18-20 Hoàng Hoa Thám, Thanh Khê, Đà Nẵng"));
        agencyList.add(new Agency("HEAD Hùng Phát", "4.8", "2.2k đánh giá", "52 Nguyễn Văn Linh, Hải Châu, Đà Nẵng"));
        agencyList.add(new Agency("Honda Quốc Tiến", "4.7", "1.5k đánh giá", "145 Điện Biên Phủ, Thanh Khê, Đà Nẵng"));
        agencyList.add(new Agency("HEAD Trung Nam", "4.9", "980 đánh giá", "305 CMT8, Cẩm Lệ, Đà Nẵng"));
        agencyList.add(new Agency("Honda Tiến Thu", "4.6", "1.2k đánh giá", "09 Quang Trung, Hải Châu, Đà Nẵng"));
    }

    private class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.AgencyViewHolder> {

        private final List<Agency> agencies;

        AgencyAdapter(List<Agency> agencies) {
            this.agencies = agencies;
        }

        @NonNull
        @Override
        public AgencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agency, parent, false);
            return new AgencyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AgencyViewHolder holder, int position) {
            Agency agency = agencies.get(position);
            holder.tvName.setText(agency.name);
            holder.tvAddress.setText(agency.address);

            holder.iconNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Agency_Activity.this, Agency_Detail_Activity.class);
                    intent.putExtra("agency_name", agency.name);
                    intent.putExtra("agency_address", agency.address);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return agencies.size();
        }

        class AgencyViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvAddress;
            ImageView iconNext;

            AgencyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvAddress = itemView.findViewById(R.id.tvAddress);
                iconNext = itemView.findViewById(R.id.ic_next);
            }
        }
    }

    private static class Agency {
        final String name;
        final String address;

        Agency(String name, String rating, String reviews, String address) {
            this.name = name;
            this.address = address;
        }
    }
}
