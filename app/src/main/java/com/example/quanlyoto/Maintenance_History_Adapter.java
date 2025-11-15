package com.example.quanlyoto;


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

    public Maintenance_History_Adapter(List<Maintenance_Record> recordList) {
        this.recordList = recordList;
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
//            arrow = itemView.findViewById(R.id.arrow_icon);
        }
    }
}

