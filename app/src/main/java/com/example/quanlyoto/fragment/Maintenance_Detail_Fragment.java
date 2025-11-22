package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Maintenance_Detail_Fragment extends Fragment {

    public Maintenance_Detail_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_maintenance_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //==================== XỬ LÝ NÚT BACK ====================//
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                // Quay về fragment Maintenance History
                Fragment backFragment = new Maintenance_History_Fragment();

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, backFragment)
                        .addToBackStack(null)
                        .commit();
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
        //==================== agency detail ====================//
        View imgArrow = view.findViewById(R.id.imgArrow);
        if (imgArrow != null) {
            imgArrow.setOnClickListener(v -> {
                Fragment agencyDetailFragment = new Agency_Detail_Fragment(); // fragment của bạn

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, agencyDetailFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        //==================== NHẬN DỮ LIỆU TỪ FRAGMENT TRƯỚC ====================//
        if (getArguments() != null) {
            String dealer = getArguments().getString("dealer");
            String date = getArguments().getString("date");
            String km = getArguments().getString("km");

            // TODO: Gán vào các TextView trong layout
        }
    }
}

