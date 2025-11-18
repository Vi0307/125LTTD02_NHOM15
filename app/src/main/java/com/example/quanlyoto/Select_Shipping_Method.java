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

public class Select_Shipping_Method extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_shipping_method);

        // Áp dụng insets cho layout gốc
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Click icon back -> quay về màn chọn địa chỉ thanh toán
        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Select_Shipping_Method.this, Select_Billing_Address.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // Click nút "Áp dụng" ở cuối màn -> sang màn áp dụng khuyến mãi
        Button btnPromotion = findViewById(R.id.iv_Promotion_applies);
        if (btnPromotion != null) {
            btnPromotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Select_Shipping_Method.this, Promotion_Applies.class);
                    startActivity(intent);
                }
            });
        }
    }
}