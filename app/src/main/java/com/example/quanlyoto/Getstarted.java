package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Getstarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted_screen);

        // Nút back quay về Welcome
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Getstarted.this, Welcome.class);
            startActivity(intent);
            finish();
        });

        // Nút đăng nhập → chuyển sang Login
        Button btnLogin = findViewById(R.id.btnGetStarted); // ID của nút trong XML
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Getstarted.this, Login.class);
            startActivity(intent);
        });
    }
}