package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class AppointmentFixActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_fix, container, false);

        Button btnNext = view.findViewById(R.id.button);
        btnNext.setOnClickListener(v -> {
            // Chuyển sang fragment kế tiếp
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AppointmentDesActivity())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
