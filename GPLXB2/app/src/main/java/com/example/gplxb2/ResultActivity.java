package com.example.gplxb2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResultActivity extends AppCompatActivity {

    private TextView resultText;
    private TextView totalQuestionsText;
    private TextView resultMessage;
    private Button backButton;
    private Toolbar toolbar;
    private TextView toolbarTitle; // TextView cho tiêu đề trên toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title); // Khởi tạo TextView tiêu đề
        resultText = findViewById(R.id.result_text);
        totalQuestionsText = findViewById(R.id.total_questions_text);
        resultMessage = findViewById(R.id.result_message);
        backButton = findViewById(R.id.back_btn);

        // Receive data from Intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);
        int incorrectCriticalCount = intent.getIntExtra("INCORRECT_CRITICAL_COUNT", 0);
        String examType = intent.getStringExtra("EXAM_TYPE"); // Nhận loại bộ đề
        String examTitle = intent.getStringExtra("TITLE"); // Nhận tiêu đề bộ đề

        // Set toolbar title
        if (examTitle != null) {
            toolbarTitle.setText(examTitle); // Thiết lập tiêu đề lên toolbar
        } else {
            toolbarTitle.setText("Kết quả thi"); // Tiêu đề mặc định nếu không có
        }

        // Display the score and total questions
        resultText.setText("Your Score: " + score);
        totalQuestionsText.setText("Total Questions: " + totalQuestions);

        // Determine the result message based on the score and incorrectCriticalCount
        if (incorrectCriticalCount > 0) {
            resultMessage.setText("Bạn đã thi trượt vì làm sai câu điểm liệt");
            resultMessage.setTextColor(Color.RED);
        } else if (score < 32) {
            resultMessage.setText("Bạn đã thi trượt do không đủ số câu đúng tối thiểu");
            resultMessage.setTextColor(Color.RED);
        } else {
            resultMessage.setText("Chúc mừng bạn đã thi đạt");
            resultMessage.setTextColor(Color.GREEN);
        }

        // Set up the back button event
        backButton.setText("Quay về trang chủ");
        backButton.setBackgroundColor(Color.parseColor("#329BF1")); // Đặt màu cho nút
        backButton.setOnClickListener(v -> goToMain());
    }

    // Method to return to the main page
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
