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

public class Select_Billing_Address extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_billing_address);

        // Áp dụng insets cho layout gốc
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý click icon back -> quay lại trang chi tiết thanh toán
        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Select_Billing_Address.this, Detail_Payment.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // Xử lý click nút "Áp dụng" -> sang trang chọn phương thức vận chuyển
        Button btnApply = findViewById(R.id.btn_apply_address);
        if (btnApply != null) {
            btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Select_Billing_Address.this, Select_Shipping_Method.class);
                    startActivity(intent);
                }
            });
        }
    }
}