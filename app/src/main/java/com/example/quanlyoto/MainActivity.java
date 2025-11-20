package com.example.quanlyoto;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlyoto.fragment.AppointmentFixActivity;
import com.example.quanlyoto.fragment.PersonalActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout có container

        // Load fragment đầu tiên nếu chưa có fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PersonalActivity())
                    .commit();
        }
    }
}
