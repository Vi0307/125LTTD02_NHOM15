package com.example.quanlyoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyoto.R;
import com.example.quanlyoto.model.PhuongThucThanhToan;

import java.util.List;

/**
 * Adapter cho RecyclerView hiển thị danh sách phương thức thanh toán
 */
public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder> {

    private final Context context;
    private final List<PhuongThucThanhToan> paymentMethods;
    private int selectedPosition = 0; // Mặc định chọn item đầu tiên
    private OnPaymentMethodSelectedListener listener;

    public interface OnPaymentMethodSelectedListener {
        void onPaymentMethodSelected(PhuongThucThanhToan method, int position);
    }

    public PaymentMethodAdapter(Context context, List<PhuongThucThanhToan> paymentMethods) {
        this.context = context;
        this.paymentMethods = paymentMethods;
    }

    public void setOnPaymentMethodSelectedListener(OnPaymentMethodSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment_method, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhuongThucThanhToan method = paymentMethods.get(position);

        // Set tên phương thức thanh toán
        holder.txtName.setText(method.getTenPTTT());

        // Set mô tả nếu có
        if (method.getMoTa() != null && !method.getMoTa().isEmpty()) {
            holder.txtDescription.setText(method.getMoTa());
            holder.txtDescription.setVisibility(View.VISIBLE);
        } else {
            holder.txtDescription.setVisibility(View.GONE);
        }

        // Set icon dựa trên tên icon từ database
        int iconResId = getIconResource(method.getIcon());
        if (iconResId != 0) {
            holder.imgIcon.setImageResource(iconResId);
        }

        // Set radio button trạng thái
        if (position == selectedPosition) {
            holder.radioButton.setImageResource(R.drawable.ic_radio_button);
        } else {
            holder.radioButton.setImageResource(R.drawable.ic_radio_button_unchecked);
        }

        // Handle click
        holder.layoutItem.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            // Update UI
            notifyItemChanged(previousSelected);
            notifyItemChanged(selectedPosition);

            // Callback
            if (listener != null) {
                listener.onPaymentMethodSelected(method, selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentMethods != null ? paymentMethods.size() : 0;
    }

    /**
     * Lấy resource icon dựa trên tên icon từ database
     */
    private int getIconResource(String iconName) {
        if (iconName == null)
            return R.drawable.today_24dp_icon;

        switch (iconName) {
            case "ic_cash":
            case "today_24dp_icon":
                return R.drawable.today_24dp_icon;
            case "ic_apple_pay":
            case "ic_apple":
                return R.drawable.ic_apple;
            case "ic_bank":
            case "ic_bank_payment":
                return R.drawable.ic_bank_payment;
            default:
                return R.drawable.today_24dp_icon;
        }
    }

    /**
     * Lấy phương thức thanh toán đã chọn
     */
    public PhuongThucThanhToan getSelectedMethod() {
        if (paymentMethods != null && selectedPosition >= 0 && selectedPosition < paymentMethods.size()) {
            return paymentMethods.get(selectedPosition);
        }
        return null;
    }

    /**
     * Set vị trí đã chọn (ví dụ khi load mặc định)
     */
    public void setSelectedPosition(int position) {
        int previousSelected = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousSelected);
        notifyItemChanged(selectedPosition);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem;
        ImageView imgIcon;
        TextView txtName;
        TextView txtDescription;
        ImageView radioButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_payment_item);
            imgIcon = itemView.findViewById(R.id.img_icon);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_description);
            radioButton = itemView.findViewById(R.id.radio_button);
        }
    }
}
