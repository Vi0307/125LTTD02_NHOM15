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

import com.example.quanlyoto.R;

public class Agency_Detail_Fragment extends Fragment {

    // Dữ liệu nhận từ Bundle
    private String agencyName;
    private String agencyAddress;
    private String agencyPhone;
    private String agencyHours;
    private String agencyDescription;
    private int agencyId = -1;

    // Views
    private TextView txtAgencyName;
    private TextView txtDiaDiem;
    private TextView txtGio;
    private TextView txtSDT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Nhận dữ liệu từ Bundle
        if (getArguments() != null) {
            agencyName = getArguments().getString("agency_name", "");
            agencyAddress = getArguments().getString("agency_address", "");
            agencyPhone = getArguments().getString("agency_phone", "");
            agencyHours = getArguments().getString("agency_hours", "");
            agencyDescription = getArguments().getString("agency_description", "");
            agencyId = getArguments().getInt("agency_id", -1);
        }

        return inflater.inflate(R.layout.activity_agency_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        displayAgencyData();
        setupBackButton(view);
        setupButtonDatDichVu(view);
    }

    private void initViews(View view) {
        txtAgencyName = view.findViewById(R.id.txtAgencyName);
        txtDiaDiem = view.findViewById(R.id.txtDiaDiem);
        txtGio = view.findViewById(R.id.txtGio);
        txtSDT = view.findViewById(R.id.txtSDT);
    }

    private void displayAgencyData() {
        // Hiển thị tên đại lý
        if (txtAgencyName != null && agencyName != null && !agencyName.isEmpty()) {
            txtAgencyName.setText(agencyName);
        }

        // Hiển thị địa chỉ
        if (txtDiaDiem != null && agencyAddress != null && !agencyAddress.isEmpty()) {
            txtDiaDiem.setText(agencyAddress);
        }

        // Hiển thị giờ làm việc
        if (txtGio != null && agencyHours != null && !agencyHours.isEmpty()) {
            txtGio.setText(agencyHours);
        } else if (txtGio != null) {
            txtGio.setText("07:30 - 18:00"); // Giá trị mặc định
        }

        // Hiển thị số điện thoại
        if (txtSDT != null && agencyPhone != null && !agencyPhone.isEmpty()) {
            txtSDT.setText(agencyPhone);
        }
    }

    private void setupBackButton(View view) {
        ImageView icBack = view.findViewById(R.id.ic_back_agency);
        if (icBack != null) {
            icBack.setOnClickListener(v -> {
                // Quay lại Fragment trước
                requireActivity().getSupportFragmentManager().popBackStack();
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

    private void setupButtonDatDichVu(View view) {
        View btnDatDichVu = view.findViewById(R.id.btnDatDichVu);

        if (btnDatDichVu != null) {
            btnDatDichVu.setOnClickListener(v -> {
                // Truyền thông tin đại lý sang Booking_Fragment
                Booking_Fragment bookingFragment = new Booking_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("agency_name", agencyName);
                bundle.putInt("agency_id", agencyId);
                bookingFragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, bookingFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

    // Getters cho các thông tin đại lý (có thể dùng ở nơi khác nếu cần)
    public String getAgencyName() {
        return agencyName;
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public int getAgencyId() {
        return agencyId;
    }
}

