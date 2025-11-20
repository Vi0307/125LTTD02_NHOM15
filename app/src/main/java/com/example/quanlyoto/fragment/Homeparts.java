package com.example.quanlyoto.fragment;

import com.example.quanlyoto.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.fragment.Bodyparts;

public class Homeparts extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_homeparts_screen, container, false);

        // Nút "Phụ tùng thân vỏ"
        Button btnBodyParts = view.findViewById(R.id.btnBodyParts);

        btnBodyParts.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Bodyparts())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}