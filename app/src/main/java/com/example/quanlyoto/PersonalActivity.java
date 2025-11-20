package com.example.quanlyoto;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.activity.OnBackPressedCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PersonalActivity extends AppCompatActivity {

    private ImageView btnBack;
    private ImageView btnEdit;
    private LinearLayout btnLogout;
    private LinearLayout itemVoucher;
    private LinearLayout itemOrder;
    private FrameLayout logoutOverlay;
    private CardView btnCancel;
    private CardView btnConfirmLogout;
    private FloatingActionButton fabChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        // Lấy reference các view
        btnBack = findViewById(R.id.btn_back);
        btnEdit = findViewById(R.id.btn_edit);
        btnLogout = findViewById(R.id.btn_logout);
        itemVoucher = findViewById(R.id.item_voucher);
        itemOrder = findViewById(R.id.item_order);
        logoutOverlay = findViewById(R.id.logout_overlay);
        btnCancel = findViewById(R.id.btn_cancel);
        btnConfirmLogout = findViewById(R.id.btn_confirm_logout);
        fabChat = findViewById(R.id.btn_logout_confirm);

        // Set sự kiện click cho nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set sự kiện click cho nút edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalActivity.this, "Chỉnh sửa thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        // Set sự kiện click cho Voucher
        itemVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalActivity.this, "Mở voucher của tôi", Toast.LENGTH_SHORT).show();
            }
        });

        // Set sự kiện click cho Đơn hàng
        itemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalActivity.this, "Mở đơn hàng của tôi", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút Đăng xuất - Hiện dialog
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutOverlay.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý nút Đóng - Đóng dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutOverlay.setVisibility(View.GONE);
            }
        });

        btnConfirmLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, AppointmentCheckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });




        // Click vào overlay cũng đóng dialog
        logoutOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutOverlay.setVisibility(View.GONE);
            }
        });

        // Xử lý FAB Chat
        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalActivity.this, "Mở chat", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút Back của hệ thống
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (logoutOverlay != null && logoutOverlay.getVisibility() == View.VISIBLE) {
                    logoutOverlay.setVisibility(View.GONE);
                } else {
                    finish();
                }
            }
        });
    }
}