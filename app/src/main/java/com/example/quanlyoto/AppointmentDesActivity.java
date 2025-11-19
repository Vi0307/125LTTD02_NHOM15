package com.example.quanlyoto;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;

public class AppointmentDesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_des); // layout màn 2
        // Lấy reference nút back
        ImageView btnBack = findViewById(R.id.btn_back);

        // Set sự kiện click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kết thúc Activity hiện tại, quay về Activity trước đó
                finish();
            }
        });
        // Nút Tiếp chuyển sang màn 3: AppointmentPeriodActivity
        Button btnNext = findViewById(R.id.button);
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(AppointmentDesActivity.this, AppointmentPeriodActivity.class);
            startActivity(intent);
        });
    }
}