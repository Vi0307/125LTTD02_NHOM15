package com.example.quanlyoto.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.Agency_Detail_Activity;
import com.example.quanlyoto.R;

import java.util.ArrayList;
import java.util.List;

public class Agency_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private AgencyAdapter agencyAdapter;
    private final List<Agency> agencyList = new ArrayList<>();

    public Agency_Fragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_agency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBackButton(view);
        setupRecyclerView(view);
    }

    private void setupBackButton(View view) {
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // Quay lại fragment trước đó
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    getParentFragmentManager().popBackStack();
                } else {
                    // Nếu không có gì trong back stack, về Home
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .commit();
                }
            });
        }
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

        // BOTTOM NAV — MYCAR
        view.findViewById(R.id.navCar).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MyCarFragment())
                    .commit();
        });

        // BOTTOM NAV — VOUCHER
        View navVoucher = view.findViewById(R.id.navVoucher);
        if (navVoucher != null) {
            navVoucher.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .addToBackStack(null)
                        .commit();
            });
        }

        // CHATBOX → ChatFragment
        View chatBtn = view.findViewById(R.id.btnChat);
        if (chatBtn != null) {
            chatBtn.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ChatBox())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rcvAgencies);
        if (recyclerView == null) return;

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

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
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_agency, parent, false);
            return new AgencyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AgencyViewHolder holder, int position) {
            Agency agency = agencies.get(position);

            holder.tvName.setText(agency.name);
            holder.tvRating.setText(agency.rating);
            holder.tvReviews.setText(agency.reviews);
            holder.tvAddress.setText(agency.address);

            holder.iconNext.setOnClickListener(v -> {
                Agency_Detail_Fragment detailFragment = new Agency_Detail_Fragment();

                // Gửi dữ liệu qua fragment
                Bundle bundle = new Bundle();
                bundle.putString("agency_name", agency.name);
                bundle.putString("agency_address", agency.address);
                detailFragment.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        @Override
        public int getItemCount() {
            return agencies.size();
        }

        class AgencyViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvRating, tvReviews, tvAddress;
            ImageView iconNext;

            AgencyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvRating = itemView.findViewById(R.id.tvRating);
                tvReviews = itemView.findViewById(R.id.tvReviews);
                tvAddress = itemView.findViewById(R.id.tvAddress);
                iconNext = itemView.findViewById(R.id.ic_next);
            }
        }
    }

    private static class Agency {
        final String name;
        final String rating;
        final String reviews;
        final String address;

        Agency(String name, String rating, String reviews, String address) {
            this.name = name;
            this.rating = rating;
            this.reviews = reviews;
            this.address = address;
        }
    }
}

