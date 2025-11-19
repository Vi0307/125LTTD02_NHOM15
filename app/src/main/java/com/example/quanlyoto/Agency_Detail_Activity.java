package com.example.quanlyoto;

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

        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}


