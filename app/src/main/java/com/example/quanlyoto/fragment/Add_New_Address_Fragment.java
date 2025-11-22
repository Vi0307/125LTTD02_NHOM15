package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class Add_New_Address_Fragment extends Fragment {

    LinearLayout optionHome, optionOffice;
    ImageView radioHome, radioOffice;
    int selected = 0; // 1 = nhà riêng, 2 = văn phòng

    public Add_New_Address_Fragment() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_new_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ivBack = view.findViewById(R.id.iv_back);
        EditText etAddress = view.findViewById(R.id.et_address);

        optionHome = view.findViewById(R.id.option_home);
        optionOffice = view.findViewById(R.id.option_office);
        radioHome = view.findViewById(R.id.radio_home);
        radioOffice = view.findViewById(R.id.radio_office);

        Button btnApply = view.findViewById(R.id.btn_apply);

        // Xử lý radio
        optionHome.setOnClickListener(v -> selectHome());
        optionOffice.setOnClickListener(v -> selectOffice());
        radioHome.setOnClickListener(v -> selectHome());
        radioOffice.setOnClickListener(v -> selectOffice());

        // BACK
        ivBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .popBackStack()
        );

        // ÁP DỤNG
        btnApply.setOnClickListener(v -> {

            if (etAddress.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selected == 0) {
                Toast.makeText(getContext(), "Vui lòng chọn loại địa chỉ", Toast.LENGTH_SHORT).show();
                return;
            }

            // chuyển sang trang phương thức vận chuyển
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Select_Shipping_Method_Fragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void selectHome() {
        selected = 1;
        radioHome.setImageResource(R.drawable.ic_radio_button);
        radioOffice.setImageResource(R.drawable.ic_radio_button_unchecked);
    }

    private void selectOffice() {
        selected = 2;
        radioHome.setImageResource(R.drawable.ic_radio_button_unchecked);
        radioOffice.setImageResource(R.drawable.ic_radio_button);
    }
}
