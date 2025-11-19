package com.example.quanlyoto;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentFixActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_fix); // layout màn 1

        Button btnNext = findViewById(R.id.button);
        btnNext.setOnClickListener(v -> {
            // Chuyển sang màn AppointmentDesActivity
            Intent intent = new Intent(AppointmentFixActivity.this, AppointmentDesActivity.class);
            startActivity(intent);
        });
    }
}