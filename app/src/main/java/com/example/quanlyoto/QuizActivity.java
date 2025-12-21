package com.example.quanlyoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyoto.model.RewardRequest;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuestion, txtProgress;
    Button btnA, btnB, btnC, btnD;

    int currentIndex = 0;
    int score = 0; // Added score tracking

    // Colors
    String DEFAULT_COLOR = "#C4B5DEC2"; // màu mặc định
    String CORRECT_COLOR = "#18DAE2"; // màu đúng
    String WRONG_COLOR = "#F4C7CD"; // màu sai

    List<QuestionModel> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_layout_quiz);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtProgress = findViewById(R.id.txtProgress);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        loadQuestions();
        updateQuestion();

        btnA.setOnClickListener(v -> checkAnswer("A", btnA));
        btnB.setOnClickListener(v -> checkAnswer("B", btnB));
        btnC.setOnClickListener(v -> checkAnswer("C", btnC));
        btnD.setOnClickListener(v -> checkAnswer("D", btnD));
    }

    // --------------------------
    // LOAD 10 QUESTIONS
    // --------------------------
    private void loadQuestions() {
        questionList = new ArrayList<>();

        questionList.add(new QuestionModel(
                "Xe ô tô chạy bằng xăng và xe chạy bằng điện khác nhau chủ yếu ở điểm nào?",
                "Hình dáng bên ngoài", "Loại nhiên liệu sử dụng", "Màu sơn của xe", "Số chỗ ngồi", "B"));

        questionList.add(new QuestionModel(
                "Khi nào cần thay dầu cho xe máy hoặc ô tô?",
                "Khi xe bẩn", "Khi nghe tiếng động lạ", "Sau mỗi 1.000–5.000 km", "Khi xe không nổ máy", "C"));

        questionList.add(new QuestionModel(
                "Nguyên nhân nào sau đây khiến xe hao xăng hơn bình thường?",
                "Lốp xe non hơi", "Đi đúng tốc độ", "Bảo dưỡng định kỳ", "Đổ xăng đúng loại", "A"));

        questionList.add(new QuestionModel(
                "Cách lái xe nào giúp tiết kiệm nhiên liệu và an toàn hơn?",
                "Phóng nhanh, thắng gấp", "Chạy đều ga", "Tăng tốc đột ngột để vượt xe", "Để xe nổ máy lâu khi dừng",
                "B"));

        questionList.add(new QuestionModel(
                "Ắc quy xe có tác dụng chính là gì?",
                "Làm mát động cơ", "Cung cấp điện cho xe khởi động", "Giảm tiếng ồn khi chạy", "Giúp xe chạy nhanh hơn",
                "B"));

        questionList.add(new QuestionModel(
                "Khi xe bị hết xăng giữa đường, bạn nên làm gì?",
                "Gọi cứu hộ", "Đề máy lại nhiều lần", "Đổ nước thay xăng tạm", "Bỏ xe lại", "A"));

        questionList.add(new QuestionModel(
                "Muốn rẽ trái khi lái xe, bạn phải làm gì trước?",
                "Rẽ ngay", "Bấm còi", "Bật đèn xi-nhan trái", "Dừng xe", "C"));

        questionList.add(new QuestionModel(
                "Khi trời mưa to, bạn nên bật đèn gì?",
                "Đèn pha", "Đèn sương mù hoặc đèn cốt", "Đèn xi-nhan", "Đèn khẩn cấp", "B"));

        questionList.add(new QuestionModel(
                "Mức nước làm mát động cơ thấp sẽ gây ra điều gì?",
                "Động cơ nóng nhanh", "Xe chạy êm hơn", "Hao xăng", "Giảm tốc độ", "A"));

        questionList.add(new QuestionModel(
                "Khi dừng đèn đỏ, bánh xe nên dừng ở đâu?",
                "Trước vạch dừng", "Giữa ngã tư", "Sau vạch dừng", "Trên vạch dành cho người đi bộ", "A"));
    }

    // --------------------------
    // UPDATE UI TO NEW QUESTION
    // --------------------------
    private void updateQuestion() {

        // Update progress
        txtProgress.setText("Câu " + (currentIndex + 1) + "/10");

        // Get current question
        QuestionModel q = questionList.get(currentIndex);

        txtQuestion.setText(q.getQuestion());
        btnA.setText("A.\n" + q.getA());
        btnB.setText("B.\n" + q.getB());
        btnC.setText("C.\n" + q.getC());
        btnD.setText("D.\n" + q.getD());

        resetButtonColor();
        enableButtons(true);
    }

    // --------------------------
    // CHECK ANSWER
    // --------------------------
    private void checkAnswer(String userAnswer, Button clickedBtn) {

        String correct = questionList.get(currentIndex).getCorrect();

        if (userAnswer.equals(correct)) {
            clickedBtn.setBackgroundColor(Color.parseColor(CORRECT_COLOR));
            score++; // Increment score
        } else {
            clickedBtn.setBackgroundColor(Color.parseColor(WRONG_COLOR));
            highlightCorrect(correct);
        }

        enableButtons(false);

        new Handler().postDelayed(() -> {
            currentIndex++;

            if (currentIndex < questionList.size()) {
                updateQuestion();
            } else {
                // CHUYỂN TRANG SAU KHI HOÀN THÀNH CÂU 10
                // Call API before navigating
                sendRewardRequest();
            }

        }, 900);
    }

    private void sendRewardRequest() {
        RewardRequest request = new RewardRequest(1, score); // Hardcoded maND=1 for now
        RetrofitClient.getApiService().rewardVoucher(request)
                .enqueue(new Callback<com.example.quanlyoto.model.ApiResponse<String>>() {
                    @Override
                    public void onResponse(Call<com.example.quanlyoto.model.ApiResponse<String>> call,
                            Response<com.example.quanlyoto.model.ApiResponse<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String msg = response.body().getMessage(); // ApiResponse wrapper
                            Toast.makeText(QuizActivity.this, "Đúng " + score + "/10 câu. " + msg, Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(QuizActivity.this, "Đúng " + score + "/10 câu.", Toast.LENGTH_SHORT).show();
                        }
                        navigateToResult();
                    }

                    @Override
                    public void onFailure(Call<com.example.quanlyoto.model.ApiResponse<String>> call, Throwable t) {
                        // Toast.makeText(QuizActivity.this, "Lỗi kết nối: " + t.getMessage(),
                        // Toast.LENGTH_SHORT).show();
                        navigateToResult();
                    }
                });
    }

    private void navigateToResult() {
        Intent intent;
        if (score == 10) {
            intent = new Intent(QuizActivity.this, ResultActivity.class);
        } else if (score == 7) {
            intent = new Intent(QuizActivity.this, ResulttwoActivity.class);
        } else {
            // Quay về MainActivity, fragment VoucherStillValid
            intent = new Intent(QuizActivity.this, MainActivity.class);
            intent.putExtra("fragment", "VoucherStillValid");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

        startActivity(intent);
        finish();
    }

    private void highlightCorrect(String correct) {
        if (correct.equals("A"))
            btnA.setBackgroundColor(Color.parseColor(CORRECT_COLOR));
        if (correct.equals("B"))
            btnB.setBackgroundColor(Color.parseColor(CORRECT_COLOR));
        if (correct.equals("C"))
            btnC.setBackgroundColor(Color.parseColor(CORRECT_COLOR));
        if (correct.equals("D"))
            btnD.setBackgroundColor(Color.parseColor(CORRECT_COLOR));
    }

    private void resetButtonColor() {
        btnA.setBackgroundColor(Color.parseColor(DEFAULT_COLOR));
        btnB.setBackgroundColor(Color.parseColor(DEFAULT_COLOR));
        btnC.setBackgroundColor(Color.parseColor(DEFAULT_COLOR));
        btnD.setBackgroundColor(Color.parseColor(DEFAULT_COLOR));
    }

    private void enableButtons(boolean enable) {
        btnA.setEnabled(enable);
        btnB.setEnabled(enable);
        btnC.setEnabled(enable);
        btnD.setEnabled(enable);
    }
}
