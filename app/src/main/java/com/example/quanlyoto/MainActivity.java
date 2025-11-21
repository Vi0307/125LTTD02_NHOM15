package com.example.quanlyoto;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlyoto.fragment.PersonalActivity;
import com.example.quanlyoto.fragment.VoucherStillValid;
import com.example.quanlyoto.ResultActivity;
import com.example.quanlyoto.fragment.Welcome;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout có container

        // Xử lý intent khi activity mới được tạo
        handleIntent(getIntent(), savedInstanceState == null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // cập nhật intent mới
        handleIntent(intent, true);
    }

    private void handleIntent(Intent intent, boolean isFirstLoad) {
        String fragmentName = intent.getStringExtra("fragment");

        if (isFirstLoad) {
            if ("VoucherStillValid".equals(fragmentName)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .commit();
            } else {
                // Mặc định load PersonalActivity (không phải ResultActivity)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new Welcome())
                        .commit();
            }
        } else {
            if ("VoucherStillValid".equals(fragmentName)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VoucherStillValid())
                        .commit();
            }
        }
    }
}