package com.example.quanlyoto;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Maintenance_History_Screen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Maintenance_History_Adapter adapter;
    private List<Maintenance_Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_history);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách dữ liệu mẫu
        recordList = new ArrayList<>();
        recordList.add(new Maintenance_Record("TƯỜNG PHÁT 1", "02/09/2025", "6,256"));
        recordList.add(new Maintenance_Record("TƯỜNG PHÁT 1", "16/02/2025", "3,986"));
        recordList.add(new Maintenance_Record("TƯỜNG PHÁT 1", "07/09/2024", "2,504"));
        recordList.add(new Maintenance_Record("THẢO ÁI", "30/09/2023", "307"));

        // Gắn adapter vào RecyclerView
        adapter = new Maintenance_History_Adapter(recordList);
        recyclerView.setAdapter(adapter);
    }
}


