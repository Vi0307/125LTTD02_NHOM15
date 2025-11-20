package com.example.quanlyoto;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainDetailCar extends AppCompatActivity {

    private ImageButton btnExpandHistory;
    private LinearLayout layoutHistoryDetail;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar_main_detail); // ĐỔI THÀNH TÊN XML CỦA BẠN

        // ÁNH XẠ
        btnExpandHistory = findViewById(R.id.btn_expand_history);
        layoutHistoryDetail = findViewById(R.id.layoutHistoryDetail);

        // XỬ LÝ EXPAND/COLLAPSE
        btnExpandHistory.setOnClickListener(v -> {
            if (!isExpanded) {
                // Mở rộng
                layoutHistoryDetail.setVisibility(View.VISIBLE);
                btnExpandHistory.setImageResource(android.R.drawable.ic_delete); // icon "-"
                isExpanded = true;
            } else {
                // Thu gọn
                layoutHistoryDetail.setVisibility(View.GONE);
                btnExpandHistory.setImageResource(android.R.drawable.ic_input_add); // icon "+"
                isExpanded = false;
            }
        });
    }
}
