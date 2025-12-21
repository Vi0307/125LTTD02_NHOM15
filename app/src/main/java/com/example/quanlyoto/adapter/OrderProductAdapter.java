package com.example.quanlyoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.ChiTietGioHangDTO;

import java.text.DecimalFormat;
import java.util.List;


public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {

    private Context context;
    private List<ChiTietGioHangDTO> productList;

    public OrderProductAdapter(Context context, List<ChiTietGioHangDTO> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_product, parent, false);
        return new OrderProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        ChiTietGioHangDTO item = productList.get(position);

        // Hiển thị tên sản phẩm
        holder.tvProductName.setText(item.getTenPhuTung());

        // Hiển thị số lượng
        holder.tvQuantity.setText("Số lượng: x" + item.getSoLuong());

        // Hiển thị giá
        DecimalFormat formatter = new DecimalFormat("#,### VND");
        if (item.getDonGia() != null) {
            holder.tvPrice.setText(formatter.format(item.getDonGia()));
        }

        // Load hình ảnh
        loadProductImage(holder.imgProduct, item.getHinhAnh());
    }

    private void loadProductImage(ImageView imageView, String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            // Xóa phần mở rộng file nếu có
            if (imageName.contains(".")) {
                imageName = imageName.substring(0, imageName.lastIndexOf('.'));
            }
            
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            
            // Thử với tên không có underscore
            if (resId == 0) {
                String cleanName = imageName.replace("_", "");
                resId = context.getResources().getIdentifier(cleanName, "drawable", context.getPackageName());
            }

            // Mapping thủ công cho các trường hợp đặc biệt
            if (resId == 0) {
                switch (imageName) {
                    case "bom_nuoc_dft":
                        resId = context.getResources().getIdentifier("dongcobomnuocdft", "drawable", context.getPackageName());
                        break;
                    case "dongcobnbon":
                        resId = context.getResources().getIdentifier("dongcobomnuocdft", "drawable", context.getPackageName());
                        break;
                    case "giam_xoc_sau":
                        resId = context.getResources().getIdentifier("giamxocsauxe", "drawable", context.getPackageName());
                        break;
                    case "giamxoctruc":
                        resId = context.getResources().getIdentifier("giamxoctruoc", "drawable", context.getPackageName());
                        break;
                    case "thuoc_lai_mercedes":
                        resId = context.getResources().getIdentifier("thuoclai", "drawable", context.getPackageName());
                        break;
                }
            }

            if (resId != 0) {
                imageView.setImageResource(resId);
            } else {
                imageView.setImageResource(R.drawable.onggionaphoi);
            }
        } else {
            imageView.setImageResource(R.drawable.onggionaphoi);
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class OrderProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvQuantity, tvPrice;

        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
