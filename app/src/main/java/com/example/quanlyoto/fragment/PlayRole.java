package com.example.quanlyoto.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.QuizActivity;
import com.example.quanlyoto.R;

public class PlayRole extends Fragment {

    private ImageView btnBack;
    private Button btnHelp, btnReady;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_play_role, container, false);

        btnBack = view.findViewById(R.id.btn_back);
        btnHelp = view.findViewById(R.id.btnHelp2);
        btnReady = view.findViewById(R.id.btnReady);

        // --- NÚT BACK ---
        btnBack.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        // --- NÚT SẴN SÀNG ---
        btnReady.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
