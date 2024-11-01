package com.example.gplxb2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// Activity to display the result of an exam
public class ResultActivity extends AppCompatActivity {

    // UI components
    private TextView resultText; // TextView to display the user's score
    private TextView totalQuestionsText; // TextView to display the total number of questions
    private TextView resultMessage; // TextView to display the result message
    private Button backButton; // Button to navigate back to the main screen
    private Toolbar toolbar; // Toolbar for the activity
    private TextView toolbarTitle; // TextView for the toolbar title
    private DatabaseHelper databaseHelper; // DatabaseHelper for managing exam results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result); // Set the content view to the result layout

        // Initialize UI components
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        resultText = findViewById(R.id.result_text);
        totalQuestionsText = findViewById(R.id.total_questions_text);
        resultMessage = findViewById(R.id.result_message);
        backButton = findViewById(R.id.back_btn);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Retrieve data from the intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0); // Get the user's score
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0); // Get the total number of questions
        int incorrectCriticalCount = intent.getIntExtra("INCORRECT_CRITICAL_COUNT", 0); // Get the count of incorrect critical answers
        int examIndex = getIntent().getIntExtra("EXAMS_INDEX", -1); // Get the exam index
        String examType = intent.getStringExtra("EXAM_TYPE"); // Get the exam type
        String examTitle = intent.getStringExtra("TITLE"); // Get the exam title

        // Set the toolbar title based on the exam title
        if (examTitle != null) {
            toolbarTitle.setText(examTitle);
        } else {
            toolbarTitle.setText("Kết quả thi"); // Default title if none provided
        }

        // Display the user's score and total questions
        resultText.setText("Your Score: " + score);
        totalQuestionsText.setText("Total Questions: " + totalQuestions);

        // Determine the result message based on the score and incorrect critical answers
        if (incorrectCriticalCount > 0) {
            resultMessage.setText("Bạn đã thi trượt vì làm sai câu điểm liệt"); // Failed due to critical mistakes
            resultMessage.setTextColor(Color.RED); // Set text color to red
            databaseHelper.insertOrUpdateExamResult(examIndex, -1); // Update exam result in the database with failure status
        } else if (score < 32) {
            resultMessage.setText("Bạn đã thi trượt do không đủ số câu đúng tối thiểu"); // Failed due to insufficient correct answers
            resultMessage.setTextColor(Color.RED); // Set text color to red
            databaseHelper.insertOrUpdateExamResult(examIndex, -1); // Update exam result in the database with failure status
        } else {
            resultMessage.setText("Chúc mừng bạn đã thi đạt"); // Congratulatory message for passing
            resultMessage.setTextColor(Color.GREEN); // Set text color to green
            databaseHelper.insertOrUpdateExamResult(examIndex, 1); // Update exam result in the database with success status
        }

        // Configure the back button
        backButton.setText("Quay về trang chủ"); // Set button text
        backButton.setBackgroundColor(Color.parseColor("#329BF1")); // Set button background color
        backButton.setOnClickListener(v -> goToMain()); // Set click listener to navigate back to the main activity
    }

    // Method to navigate back to the main activity
    public void goToMain() {
        Intent intent = new Intent(this, MainActivity.class); // Create an intent to launch MainActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear activity stack and create a new task
        startActivity(intent); // Start the MainActivity
        finish(); // Finish the current activity
    }
}
