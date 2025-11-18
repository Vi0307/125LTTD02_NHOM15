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

public class Complete_Detail_Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_complete_detail_payment);

        // Áp dụng insets cho layout gốc
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Click icon back -> quay lại trang áp dụng khuyến mãi
        ImageView icBack = findViewById(R.id.ic_back_promotion_applies);
        if (icBack != null) {
            icBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Complete_Detail_Payment.this, Promotion_Applies.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        // Click nút "Tiếp tục thanh toán" -> sang trang chọn phương thức thanh toán
        Button btnPaymentMethod = findViewById(R.id.btn_payment_method);
        if (btnPaymentMethod != null) {
            btnPaymentMethod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Complete_Detail_Payment.this, Payment_Method.class);
                    startActivity(intent);
                }
            });
        }
    }
}

