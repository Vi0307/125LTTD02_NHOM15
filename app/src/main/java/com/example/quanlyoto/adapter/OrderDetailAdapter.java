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
import com.example.quanlyoto.model.ChiTietDonHang;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private Context context;
    private List<ChiTietDonHang> list;

    public OrderDetailAdapter(Context context, List<ChiTietDonHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietDonHang item = list.get(position);
        if (item == null)
            return;

        holder.tvProductName.setText(item.getTenPhuTung() != null ? item.getTenPhuTung() : item.getMaPhuTung());
        holder.tvQuantity.setText("x" + item.getSoLuong());

        DecimalFormat df = new DecimalFormat("###,###,###");
        if (item.getGiaTien() != null) {
            holder.tvPrice.setText(df.format(item.getGiaTien()) + " VND");
        } else {
            holder.tvPrice.setText("0 VND");
        }

        // Load image resource logic
        // This is a simplified logic similar to other parts of the app mentioned in
        // history
        // Ideally we use Glide/Picasso, but existing app seems to use manual resource
        // mapping
        String hinhAnh = item.getHinhAnh();
        if (hinhAnh != null) {
            // Remove extension if present
            if (hinhAnh.contains(".")) {
                hinhAnh = hinhAnh.substring(0, hinhAnh.lastIndexOf("."));
            }
            int resId = context.getResources().getIdentifier(hinhAnh, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imgProduct.setImageResource(resId);
            } else {
                holder.imgProduct.setImageResource(R.drawable.onggionaphoi); // Default
            }
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvQuantity, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
