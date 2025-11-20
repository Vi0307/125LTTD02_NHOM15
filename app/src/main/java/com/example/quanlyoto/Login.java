package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.bringToFront(); // Đảm bảo nút nằm trên ScrollView

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Getstarted.class);
            startActivity(intent);
            finish();
        });
    }
}