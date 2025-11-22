package com.example.quanlyoto.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.quanlyoto.R;

public class ChatBox extends Fragment {

    private LinearLayout messagesContainer;
    private EditText editMessage;
    private ImageView btnSend, btnBack;
    private NestedScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chatbox, container, false);

        messagesContainer = view.findViewById(R.id.messagesContainer);
        editMessage = view.findViewById(R.id.editMessage);
        btnSend = view.findViewById(R.id.btnSend);
        btnBack = view.findViewById(R.id.btn_back);
        scrollView = view.findViewById(R.id.scrollView);

        // Tin nhắn mẫu giống hệt ảnh bạn chụp
        addReceivedMessage("Xin chào! Rất vui được trò chuyện với bạn");
        addReceivedMessage("Bạn cần mình giúp đỡ gì ? [Smiling Face][Smiling Face]");
        addSentMessage("Chào, tôi cần tư vấn về mua phụ tùng cho xe của tôi");

        // Nút back
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        // Gửi tin nhắn
        btnSend.setOnClickListener(v -> {
            String text = editMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                addSentMessage(text);
                editMessage.setText("");
                scrollToBottom();
            }
        });

        return view;
    }

    private void addReceivedMessage(String text) {
        addMessage(text, false);
    }

    private void addSentMessage(String text) {
        addMessage(text, true);
    }

    private void addMessage(String text, boolean isSent) {
        LinearLayout bubble = new LinearLayout(getContext());
        bubble.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams bubbleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        bubbleParams.gravity = isSent ? Gravity.END : Gravity.START;
        bubbleParams.setMargins(0, 4, 0, 12);
        bubble.setLayoutParams(bubbleParams);

        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(16);
        tv.setTextColor(isSent ? Color.WHITE : Color.BLACK);
        tv.setPadding(40, 28, 40, 28);
        tv.setMaxWidth(800);

        // Tạo background bo góc giống Zalo
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setColor(isSent ? Color.BLACK : Color.WHITE);
        bg.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, isSent ? 20 : 4, isSent ? 4 : 20});
        tv.setBackground(bg);

        TextView time = new TextView(getContext());
        time.setText("09:41");
        time.setTextSize(12);
        time.setTextColor(isSent ? 0xFFCCCCCC : 0xFF888888);
        time.setPadding(8, 4, 8, 0);

        bubble.addView(tv);
        bubble.addView(time);
        messagesContainer.addView(bubble);

        scrollToBottom();
    }

    private void scrollToBottom() {
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}