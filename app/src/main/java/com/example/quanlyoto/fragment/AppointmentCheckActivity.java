package com.example.quanlyoto.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.MainActivity;
import com.example.quanlyoto.R;

public class AppointmentCheckActivity extends Fragment {

    private Button btnComplete;
    private FrameLayout dialogOverlay;
    private Button btnGoHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_check, container, false);

        // Nút back
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Dialog
        btnComplete = view.findViewById(R.id.button);
        dialogOverlay = view.findViewById(R.id.dialogOverlay);
        btnGoHome = view.findViewById(R.id.btnGoHome);

        btnComplete.setOnClickListener(v -> dialogOverlay.setVisibility(View.VISIBLE));
        btnGoHome.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit(); // không cần thêm addToBackStack nếu muốn không quay lại fragment cũ
            }
        });


        TextView btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialogOverlay.setVisibility(View.GONE));

        // Back button xử lý khi dialog mở
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (dialogOverlay != null && dialogOverlay.getVisibility() == View.VISIBLE) {
                            dialogOverlay.setVisibility(View.GONE);
                        } else {
                            getParentFragmentManager().popBackStack();
                        }
                    }
                });

        return view;
    }
}
