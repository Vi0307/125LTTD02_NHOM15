package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // Bắt sự kiện click toàn màn hình để chuyển sang GetStarted
        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Getstarted.class);
                startActivity(intent);
            }
        });
    }
}
