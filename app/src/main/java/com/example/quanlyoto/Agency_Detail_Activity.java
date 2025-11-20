package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Agency_Detail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_detail);

        String name = getIntent().getStringExtra("agency_name");
        String address = getIntent().getStringExtra("agency_address");

        TextView txtAgencyName = findViewById(R.id.txtAgencyName);
        TextView txtDiaDiem = findViewById(R.id.txtDiaDiem);

        if (txtAgencyName != null && name != null) {
            txtAgencyName.setText(name);
        }
        if (txtDiaDiem != null && address != null) {
            txtDiaDiem.setText(address);
        }

        // Click nút back -> quay về màn danh sách đại lý
        ImageView btnBack = findViewById(R.id.ic_back_agency);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Agency_Detail_Activity.this, Agency_Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // Click navAgency -> mở màn đặt lịch hẹn
        View navAgency = findViewById(R.id.btnDatDichVu);
        if (navAgency != null) {
            navAgency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Agency_Detail_Activity.this, Booking.class);
                    startActivity(intent);
                }
            });
        }
    }
}


