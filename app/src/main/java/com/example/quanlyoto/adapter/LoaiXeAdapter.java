package com.example.quanlyoto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.LoaiXe;

import java.util.List;

public class LoaiXeAdapter extends RecyclerView.Adapter<LoaiXeAdapter.LoaiXeViewHolder> {

    private List<LoaiXe> loaiXeList;

    public LoaiXeAdapter(List<LoaiXe> loaiXeList) {
        this.loaiXeList = loaiXeList;
    }

    @NonNull
    @Override
    public LoaiXeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_xe, parent, false);
        return new LoaiXeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiXeViewHolder holder, int position) {
        LoaiXe loaiXe = loaiXeList.get(position);
        holder.btnLoaiXe.setText(loaiXe.getTenLoaiXe());
    }

    @Override
    public int getItemCount() {
        return loaiXeList != null ? loaiXeList.size() : 0;
    }

    public static class LoaiXeViewHolder extends RecyclerView.ViewHolder {
        Button btnLoaiXe;

        public LoaiXeViewHolder(@NonNull View itemView) {
            super(itemView);
            btnLoaiXe = itemView.findViewById(R.id.btnLoaiXe);
        }
    }
}
