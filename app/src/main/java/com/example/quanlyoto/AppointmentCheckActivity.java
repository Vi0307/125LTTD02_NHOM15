package com.example.quanlyoto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.activity.OnBackPressedCallback;

public class AppointmentCheckActivity extends AppCompatActivity {

    private Button btnComplete;
    private FrameLayout dialogOverlay;
    private Button btnGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_check);

        // Lấy reference nút back
        ImageView btnBack = findViewById(R.id.btn_back);

        // Khởi tạo các view cho dialog
        btnComplete = findViewById(R.id.button);
        dialogOverlay = findViewById(R.id.dialogOverlay);
        btnGoHome = findViewById(R.id.btnGoHome);

        // Set sự kiện click cho nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kết thúc Activity hiện tại, quay về Activity trước đó
                finish();
            }
        });

        // Xử lý nút Hoàn thành - Hiện dialog
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOverlay.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý nút Trang chủ trong dialog
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(AppointmentCheckActivity.this, AppointmentPeriodActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
                 finish();
            }
        });

        // Xử lý nút Huỷ (nếu cần)
        TextView btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialogOverlay.setVisibility(View.GONE));
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (dialogOverlay != null && dialogOverlay.getVisibility() == View.VISIBLE) {
                    dialogOverlay.setVisibility(View.GONE);
                } else {
                    // Không dùng super.onBackPressed(), dùng finish()
                    finish();
                }
            }
        });

    }

}