package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Booking_Fragment extends Fragment {

    public Booking_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_booking, container, false);
    }

    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    public void onViewCreated(@NonNull View view,
            @Nullable Bundle savedInstanceState) {

        // Initialize Default Date
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        selectedDate = sdf.format(new java.util.Date());

        setCalendarView(view);
        setupTimeButtons(view);

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
        view.findViewById(R.id.btnNext).setOnClickListener(v -> {
            AppointmentFixActivity nextFragment = new AppointmentFixActivity();
            Bundle args = new Bundle();
            args.putString("selectedDate", selectedDate);
            args.putString("selectedTime", selectedTime);

            // Pass agency_id if available
            int agencyId = -1;
            if (getArguments() != null) {
                agencyId = getArguments().getInt("agency_id", -1);
            }
            args.putInt("agency_id", agencyId);

            nextFragment.setArguments(args);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, nextFragment)
                    .addToBackStack(null)
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
        super.onViewCreated(view, savedInstanceState);

        setupBackButton(view);
    }

    private void setupBackButton(View view) {
        ImageView btnBack = view.findViewById(R.id.btnBack_agency_detail);
        TextView txtCancel = view.findViewById(R.id.txtCancel);

        View.OnClickListener goBackListener = v -> requireActivity()
                .getSupportFragmentManager()
                .popBackStack();

        if (btnBack != null) {
            btnBack.setOnClickListener(goBackListener);
        }

        if (txtCancel != null) {
            txtCancel.setOnClickListener(goBackListener);
        }
    }

    private void setCalendarView(View view) {
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setMinDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd",
                    java.util.Locale.getDefault());
            selectedDate = sdf.format(calendar.getTime());
        });
    }

    private android.widget.Button selectedButton = null;

    private void setupTimeButtons(View view) {
        android.widget.GridLayout gridTime = view.findViewById(R.id.gridTime);
        for (int i = 0; i < gridTime.getChildCount(); i++) {
            View child = gridTime.getChildAt(i);
            if (child instanceof android.widget.Button) {
                child.setOnClickListener(v -> {
                    android.widget.Button btn = (android.widget.Button) v;

                    // Reset previous selection
                    if (selectedButton != null) {
                        selectedButton.setBackgroundResource(R.drawable.bg_button_black);
                    }

                    // Set new selection
                    selectedButton = btn;
                    selectedButton.setBackgroundResource(R.drawable.bg_button_red);

                    selectedTime = btn.getText().toString();
                });
            }
        }
    }

}
