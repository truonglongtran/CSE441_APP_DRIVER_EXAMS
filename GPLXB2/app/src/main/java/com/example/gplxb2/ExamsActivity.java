package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ExamsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper; // Instance of DatabaseHelper to access exam data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enables edge-to-edge layout
        setContentView(R.layout.activity_exams); // Set the content view to activity_exams layout

        databaseHelper = new DatabaseHelper(this); // Initialize the database helper

        // Initialize the back button and set its click listener
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate back to MainActivity
                Intent intent = new Intent(ExamsActivity.this, MainActivity.class);
                startActivity(intent); // Start MainActivity
                finish(); // Close the current activity
            }
        });

        // Get the GridLayout containing exam buttons
        GridLayout buttonGrid = findViewById(R.id.button_grid);

        // Loop through each child in the GridLayout
        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
            final int examIndex = i; // Store the index of the exam
            View child = buttonGrid.getChildAt(i); // Get the child view at the current index

            // Check if the child view is a Button
            if (child instanceof Button) {
                Button examButton = (Button) child; // Cast the child to a Button

                // Get the exam status from the database
                int examStatus = databaseHelper.getExamStatus(examIndex);

                // Set button appearance based on the exam status
                switch (examStatus) {
                    case 1: // Exam is completed
                        examButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.white));
                        break;
                    case -1: // Exam is failed
                        examButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.white));
                        break;
                    default: // Exam is not taken yet
                        examButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.MyBlue));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.black));
                        break;
                }

                // Set click listener for the exam button
                examButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Navigate to TestActivity and pass the exam index
                        Intent intent = new Intent(ExamsActivity.this, TestActivity.class);
                        intent.putExtra("examsIndex", examIndex); // Pass the exam index
                        startActivity(intent); // Start TestActivity
                    }
                });
            }
        }
    }
}
