package com.example.quanlyoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ChiTietGioHangDTO;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<ChiTietGioHangDTO> cartItems;
    private OnCartItemInteractionListener listener;

    public interface OnCartItemInteractionListener {
        void onDeleteClick(int position);

        void onIncreaseClick(int position);

        void onDecreaseClick(int position);
    }

    public CartAdapter(Context context, List<ChiTietGioHangDTO> cartItems, OnCartItemInteractionListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ChiTietGioHangDTO item = cartItems.get(position);

        holder.tvProductName.setText(item.getTenPhuTung());
        holder.tvQuantity.setText(String.valueOf(item.getSoLuong()));

        DecimalFormat formatter = new DecimalFormat("#,### VND");
        if (item.getDonGia() != null) {
            holder.tvPrice.setText(formatter.format(item.getDonGia()));
        }

        // Image loading logic similar to Details.java
        String imageName = item.getHinhAnh();
        if (imageName != null && !imageName.isEmpty()) {
            if (imageName.contains(".")) {
                imageName = imageName.substring(0, imageName.lastIndexOf('.'));
            }
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId == 0) {
                String cleanName = imageName.replace("_", "");
                resId = context.getResources().getIdentifier(cleanName, "drawable", context.getPackageName());
            }

            // Manual mapping for specific cases (from Details.java)
            if (resId == 0) {
                switch (imageName) {
                    case "bom_nuoc_dft":
                        resId = context.getResources().getIdentifier("dongcobomnuocdft", "drawable",
                                context.getPackageName());
                        break;
                    case "dongcobnbon":
                        resId = context.getResources().getIdentifier("dongcobomnuocdft", "drawable",
                                context.getPackageName());
                        break;
                    case "giam_xoc_sau":
                        resId = context.getResources().getIdentifier("giamxocsauxe", "drawable",
                                context.getPackageName());
                        break;
                    case "giamxoctruc":
                        resId = context.getResources().getIdentifier("giamxoctruoc", "drawable",
                                context.getPackageName());
                        break;
                    case "thuoc_lai_mercedes":
                        resId = context.getResources().getIdentifier("thuoclai", "drawable", context.getPackageName());
                        break;
                }
            }

            if (resId != 0) {
                holder.imgProduct.setImageResource(resId);
            } else {
                holder.imgProduct.setImageResource(R.drawable.onggionaphoi);
            }
        } else {
            holder.imgProduct.setImageResource(R.drawable.onggionaphoi);
        }

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null)
                listener.onDeleteClick(position);
        });

        holder.btnPlus.setOnClickListener(v -> {
            if (listener != null)
                listener.onIncreaseClick(position);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (listener != null)
                listener.onDecreaseClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvPrice, tvQuantity;
        Button btnMinus, btnPlus, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
