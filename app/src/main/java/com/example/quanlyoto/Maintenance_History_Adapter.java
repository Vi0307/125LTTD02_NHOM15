package com.example.quanlyoto;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Maintenance_History_Adapter extends RecyclerView.Adapter<Maintenance_History_Adapter.ViewHolder> {

    private List<Maintenance_Record> recordList;
    private Context context;

    public Maintenance_History_Adapter(List<Maintenance_Record> recordList, Context context) {
        this.recordList = recordList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_maintenance_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Maintenance_Record record = recordList.get(position);
        holder.tvDealer.setText("Đại lý: " + record.getDealer());
        holder.tvDate.setText(record.getDate());
        holder.tvKm.setText(record.getKm());

        // Xử lý click vào icon arrow để chuyển sang trang chi tiết
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Maintenance_Detail.class);
                // Có thể truyền dữ liệu nếu cần
                intent.putExtra("dealer", record.getDealer());
                intent.putExtra("date", record.getDate());
                intent.putExtra("km", record.getKm());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDealer, tvDate, tvKm;
        ImageView arrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDealer = itemView.findViewById(R.id.tvDealer);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvKm = itemView.findViewById(R.id.tvKm);
            arrow = itemView.findViewById(R.id.manitenace_history_detail);
        }
    }
}

