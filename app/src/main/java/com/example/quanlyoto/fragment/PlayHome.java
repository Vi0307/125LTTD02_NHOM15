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

public class PlayHome extends Fragment {

    private ImageView btnBack;
    private Button btnHelp, btnReady;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_play_home, container, false);

        // Ánh xạ view
        btnBack = view.findViewById(R.id.btn_back);
        btnHelp = view.findViewById(R.id.btnHelp);
        btnReady = view.findViewById(R.id.btnReady);

        // Nút BACK
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        // Nút ? (help)
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PlayRole())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Nút SẴN SÀNG
        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}