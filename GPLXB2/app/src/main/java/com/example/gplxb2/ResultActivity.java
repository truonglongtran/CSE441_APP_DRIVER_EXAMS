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
    private TextView toolbarTitle;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        resultText = findViewById(R.id.result_text);
        totalQuestionsText = findViewById(R.id.total_questions_text);
        resultMessage = findViewById(R.id.result_message);
        backButton = findViewById(R.id.back_btn);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);
        int incorrectCriticalCount = intent.getIntExtra("INCORRECT_CRITICAL_COUNT", 0);
        int examIndex = getIntent().getIntExtra("EXAMS_INDEX", -1);
        String examType = intent.getStringExtra("EXAM_TYPE");
        String examTitle = intent.getStringExtra("TITLE");

        if (examTitle != null) {
            toolbarTitle.setText(examTitle);
        } else {
            toolbarTitle.setText("Kết quả thi");
        }

        resultText.setText("Your Score: " + score);
        totalQuestionsText.setText("Total Questions: " + totalQuestions);

        if (incorrectCriticalCount > 0) {
            resultMessage.setText("Bạn đã thi trượt vì làm sai câu điểm liệt");
            resultMessage.setTextColor(Color.RED);
            databaseHelper.insertOrUpdateExamResult(examIndex, -1);
        } else if (score < 32) {
            resultMessage.setText("Bạn đã thi trượt do không đủ số câu đúng tối thiểu");
            resultMessage.setTextColor(Color.RED);
            databaseHelper.insertOrUpdateExamResult(examIndex, -1);
        } else {
            resultMessage.setText("Chúc mừng bạn đã thi đạt");
            resultMessage.setTextColor(Color.GREEN);
            databaseHelper.insertOrUpdateExamResult(examIndex, 1);
        }

        backButton.setText("Quay về trang chủ");
        backButton.setBackgroundColor(Color.parseColor("#329BF1"));
        backButton.setOnClickListener(v -> goToMain());
    }

    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
