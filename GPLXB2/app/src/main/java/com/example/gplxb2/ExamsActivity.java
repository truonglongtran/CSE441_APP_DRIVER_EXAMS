package com.example.gplxb2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ExamsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper; // Declare DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exams);

        databaseHelper = new DatabaseHelper(this); // Initialize DatabaseHelper

        // Initialize the back button
        ImageButton backButton = findViewById(R.id.back_button);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Get GridLayout
        GridLayout buttonGrid = findViewById(R.id.button_grid);

        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
            final int examIndex = i; // Store button index
            View child = buttonGrid.getChildAt(i);
            if (child instanceof Button) {
                Button examButton = (Button) child;

                // Get exam status from the database
                int examStatus = databaseHelper.getExamStatus(examIndex);

                // Change button background and text color based on exam status
                switch (examStatus) {
                    case 1:
                        examButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.white)); // Văn bản trắng
                        break;
                    case -1:
                        examButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red)); // Nền đỏ
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.white)); // Văn bản trắng
                        break;
                    default:
                        examButton.setBackgroundColor(ContextCompat.getColor(this, R.color.MyBlue));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.black)); // Văn bản đen
                        break;
                }


                // Set OnClickListener for the button
                examButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Navigate to TestActivity and pass the exam index
                        Intent intent = new Intent(ExamsActivity.this, TestActivity.class);
                        intent.putExtra("examsIndex", examIndex); // Pass examsIndex to TestActivity
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
