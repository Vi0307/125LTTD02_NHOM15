package com.example.quanlyoto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class MyOrderDelivering extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Use the new content-only layout
        View view = inflater.inflate(R.layout.fragment_myorder_delivering_content, container, false);

        CardView cardOngGio = view.findViewById(R.id.card_product_onggio);
        if (cardOngGio != null) {
            cardOngGio.setOnClickListener(v -> {
                // Chuyển sang fragment OrderDetailFragment
                Order_Detail_Fragment fragment = new Order_Detail_Fragment();
                Bundle args = new Bundle();
                args.putString("orderId", "DH001"); // Sample ID
                fragment.setArguments(args);

                // Navigate using the parent fragment's parent fragment manager
                // to replace the entire MyOrderFragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }
}
