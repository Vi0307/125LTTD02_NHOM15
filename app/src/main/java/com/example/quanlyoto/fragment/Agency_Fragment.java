package com.example.quanlyoto.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.viewmodel.DaiLyViewModel;

import java.util.ArrayList;
import java.util.List;

public class Agency_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private AgencyAdapter agencyAdapter;
    private DaiLyViewModel daiLyViewModel;
    private final List<DaiLy> daiLyList = new ArrayList<>();

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
        setupViewModel();
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
        View chatBtn = view.findViewById(R.id.fabChatbox);
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
        agencyAdapter = new AgencyAdapter(daiLyList);
        recyclerView.setAdapter(agencyAdapter);
    }

    private void setupViewModel() {
        daiLyViewModel = new ViewModelProvider(this).get(DaiLyViewModel.class);

        // Observe danh sách đại lý từ API
        daiLyViewModel.getDaiLyList().observe(getViewLifecycleOwner(), daiLyListData -> {
            if (daiLyListData != null) {
                daiLyList.clear();
                daiLyList.addAll(daiLyListData);
                agencyAdapter.notifyDataSetChanged();
            }
        });

        // Observe lỗi
        daiLyViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        // Observe trạng thái loading
        daiLyViewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Có thể thêm ProgressBar nếu cần
        });

        // Gọi API để load dữ liệu
        daiLyViewModel.loadDaiLy();
    }

    private class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.AgencyViewHolder> {

        private final List<DaiLy> agencies;

        AgencyAdapter(List<DaiLy> agencies) {
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
            DaiLy daiLy = agencies.get(position);

            holder.tvName.setText(daiLy.getTenDaiLy());
            holder.tvAddress.setText(daiLy.getDiaChi());

            holder.iconNext.setOnClickListener(v -> {
                Agency_Detail_Fragment detailFragment = new Agency_Detail_Fragment();

                // Gửi dữ liệu qua fragment
                Bundle bundle = new Bundle();
                bundle.putString("agency_name", daiLy.getTenDaiLy());
                bundle.putString("agency_address", daiLy.getDiaChi());
                bundle.putString("agency_phone", daiLy.getSoDienThoai());
                bundle.putString("agency_hours", daiLy.getGioLamViec());
                bundle.putString("agency_description", daiLy.getMoTa());
                if (daiLy.getMaDaiLy() != null) {
                    bundle.putInt("agency_id", daiLy.getMaDaiLy());
                }
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
}

