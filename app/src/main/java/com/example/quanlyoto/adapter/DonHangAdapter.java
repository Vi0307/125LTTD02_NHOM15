package com.example.quanlyoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.DonHang;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.OrderViewHolder> {

    private Context context;
    private List<DonHang> orderList;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(DonHang donHang);
    }

    public DonHangAdapter(Context context, List<DonHang> orderList, OnOrderClickListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        DonHang donHang = orderList.get(position);

        // Set product name
        holder.tvProductName.setText(donHang.getTenPhuTung());

        // Set order ID
        holder.tvOrderId.setText("Mã đơn: " + donHang.getMaDH());

        // Set quantity (default to 1 for now)
        holder.tvQuantity.setText("Số lượng: x1");

        // Format and set price
        BigDecimal tongTien = donHang.getTongTien();
        if (tongTien != null) {
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String formattedPrice = formatter.format(tongTien) + " VND";
            holder.tvPrice.setText(formattedPrice);
        } else {
            holder.tvPrice.setText("0 VND");
        }

        // Load image
        String hinhAnh = donHang.getHinhAnh();
        if (hinhAnh != null && !hinhAnh.isEmpty()) {
            // Remove file extension if present
            String imageName = hinhAnh.replace(".png", "").replace(".jpg", "").replace(".jpeg", "");
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imgProduct.setImageResource(resId);
            } else {
                holder.imgProduct.setImageResource(R.drawable.onggionaphoi);
            }
        }

        // Set click listener
        holder.cardOrderItem.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderClick(donHang);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public void updateData(List<DonHang> newList) {
        this.orderList = newList;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        CardView cardOrderItem;
        ImageView imgProduct;
        TextView tvProductName;
        TextView tvOrderId;
        TextView tvQuantity;
        TextView tvPrice;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cardOrderItem = itemView.findViewById(R.id.card_order_item);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
