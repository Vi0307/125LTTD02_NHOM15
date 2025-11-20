package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class VoucherStillValid extends Fragment {

    private TextView tabActive, tabUsed;
    private View tabIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_voucher_still_valid, container, false);

        tabActive = view.findViewById(R.id.tab_active);
        tabUsed = view.findViewById(R.id.tab_used);
        tabIndicator = view.findViewById(R.id.tab_indicator);

        // Click tab Used (VoucherExpired)
        tabUsed.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new VoucherExpired())
                    .addToBackStack(null)
                    .commit();
        });

        // Back press
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        getParentFragmentManager().popBackStack();
                    }
                }
        );

        return view;
    }
}
