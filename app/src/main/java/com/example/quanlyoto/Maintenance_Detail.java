package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Maintenance_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_detail);

        // Click nút back -> quay về màn lịch sử bảo dưỡng
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Maintenance_Detail.this, Maintenance_History_Screen.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // Click navAgency -> mở màn danh sách đại lý
        View navAgency = findViewById(R.id.navAgency);
        if (navAgency != null) {
            navAgency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Maintenance_Detail.this, AgencyActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
