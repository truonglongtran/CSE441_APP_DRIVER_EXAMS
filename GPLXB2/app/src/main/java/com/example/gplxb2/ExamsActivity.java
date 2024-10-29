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

        ImageButton backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        GridLayout buttonGrid = findViewById(R.id.button_grid);

        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
            final int examIndex = i; // Store button index
            View child = buttonGrid.getChildAt(i);
            if (child instanceof Button) {
                Button examButton = (Button) child;

                int examStatus = databaseHelper.getExamStatus(examIndex);

                switch (examStatus) {
                    case 1:
                        examButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.white));
                        break;
                    case -1:
                        examButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.white));
                        break;
                    default:
                        examButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.MyBlue));
                        examButton.setTextColor(ContextCompat.getColor(ExamsActivity.this, android.R.color.black));
                        break;
                }

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
