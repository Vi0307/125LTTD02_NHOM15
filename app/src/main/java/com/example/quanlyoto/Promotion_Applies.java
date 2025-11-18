package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Promotion_Applies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_promotion_applies);

        // Áp dụng insets cho layout gốc
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Click icon back -> quay lại trang chọn phương thức vận chuyển
        ImageView icBack = findViewById(R.id.ic_back);
        if (icBack != null) {
            icBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Promotion_Applies.this, Select_Shipping_Method.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // Click nút "Áp dụng" -> sang trang chi tiết thanh toán hoàn chỉnh
        Button btnComplete = findViewById(R.id.btn_complete_payment_detail);
        if (btnComplete != null) {
            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Promotion_Applies.this, Complete_Detail_Payment.class);
                    startActivity(intent);
                }
            });
        }
    }
}