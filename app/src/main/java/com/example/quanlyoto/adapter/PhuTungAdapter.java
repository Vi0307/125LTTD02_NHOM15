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
import com.example.quanlyoto.model.PhuTung;

import java.text.DecimalFormat;
import java.util.List;

public class PhuTungAdapter extends RecyclerView.Adapter<PhuTungAdapter.PhuTungViewHolder> {

    private Context context;
    private List<PhuTung> phuTungList;
    private OnPhuTungActionListener listener;

    public interface OnPhuTungActionListener {
        void onItemClick(PhuTung phuTung);

        void onAddToCartClick(PhuTung phuTung);
    }

    public PhuTungAdapter(Context context, List<PhuTung> phuTungList, OnPhuTungActionListener listener) {
        this.context = context;
        this.phuTungList = phuTungList;
        this.listener = listener;
    }

    public void setPhuTungList(List<PhuTung> phuTungList) {
        this.phuTungList = phuTungList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhuTungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phutung, parent, false);
        return new PhuTungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhuTungViewHolder holder, int position) {
        PhuTung phuTung = phuTungList.get(position);
        if (phuTung == null)
            return;

        holder.tvTenPhuTung.setText(phuTung.getTenPhuTung());

        // Format price
        DecimalFormat formatter = new DecimalFormat("#,###");
        if (phuTung.getGiaBan() != null) {
            String gia = formatter.format(phuTung.getGiaBan()) + " VND";
            holder.tvGiaPhuTung.setText(gia);
        } else {
            holder.tvGiaPhuTung.setText("Liên hệ");
        }

        // Load image from resources
        String imageName = phuTung.getHinhAnh();
        if (imageName != null && !imageName.isEmpty()) {
            if (imageName.contains(".")) {
                imageName = imageName.substring(0, imageName.lastIndexOf('.'));
            }
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imgPhuTung.setImageResource(resId);
            } else {
                // Fallback image if resource not found
                holder.imgPhuTung.setImageResource(R.drawable.onggionaphoi);
            }
        } else {
            holder.imgPhuTung.setImageResource(R.drawable.onggionaphoi);
        }

        // Events
        holder.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onItemClick(phuTung);
        });

        holder.imgPhuTung.setOnClickListener(v -> {
            if (listener != null)
                listener.onItemClick(phuTung);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            if (listener != null)
                listener.onAddToCartClick(phuTung);
        });
    }

    @Override
    public int getItemCount() {
        return phuTungList != null ? phuTungList.size() : 0;
    }

    public static class PhuTungViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhuTung;
        TextView tvTenPhuTung;
        TextView tvGiaPhuTung;
        Button btnAddToCart;

        public PhuTungViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhuTung = itemView.findViewById(R.id.imgPhuTung);
            tvTenPhuTung = itemView.findViewById(R.id.tvTenPhuTung);
            tvGiaPhuTung = itemView.findViewById(R.id.tvGiaPhuTung);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
