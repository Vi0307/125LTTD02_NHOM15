package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ResulttwoActivity extends AppCompatActivity {

    private Button btnReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_percent_ship);

        btnReady = findViewById(R.id.btnReady);

        btnReady.setOnClickListener(v -> {
            Intent intent = new Intent(ResulttwoActivity.this, MainActivity.class);
            intent.putExtra("fragment", "VoucherStillValid"); // gửi thông tin fragment muốn mở
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
