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

public class Engineparts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_engineparts_screen, container, false);

        // ⭐ Icon quay lại
        ImageView btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .popBackStack(); // quay lại Homeparts
        });

        return view;
    }
}
