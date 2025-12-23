package com.example.quanlyoto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.Voucher;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    private Context context;
    private List<Voucher> voucherList;
    private boolean isExpiredList;

    public VoucherAdapter(Context context, List<Voucher> voucherList, boolean isExpiredList) {
        this.context = context;
        this.voucherList = voucherList;
        this.isExpiredList = isExpiredList;
    }

    public void setData(List<Voucher> list) {
        this.voucherList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_voucher_card, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);

        if (voucher == null)
            return;

        holder.tvName.setText(voucher.getLoaiVoucher());
        holder.tvDate.setText("HSD: " + voucher.getHanSuDung());

        if (isExpiredList) {
            // Gray out for expired/used items
            holder.tvName.setTextColor(Color.GRAY);
            holder.tvDate.setTextColor(Color.GRAY);
            holder.imgIcon.setColorFilter(Color.GRAY);

            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(
                    "Trạng thái: " + (voucher.getTrangThai() != null ? voucher.getTrangThai() : "Không xác định"));
        } else {
            // Normal style for active items
            holder.tvName.setTextColor(Color.BLACK);
            holder.tvDate.setTextColor(Color.parseColor("#666666"));
            holder.imgIcon.setColorFilter(Color.BLACK);
            holder.tvStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return voucherList != null ? voucherList.size() : 0;
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvName, tvDate, tvStatus;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_voucher_icon);
            tvName = itemView.findViewById(R.id.tv_voucher_name);
            tvDate = itemView.findViewById(R.id.tv_voucher_date);
            tvStatus = itemView.findViewById(R.id.tv_voucher_status);
        }
    }
}
