package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ExamsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exams);

        // Initialize the back button
        Button backButton = findViewById(R.id.back_button);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(ExamsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity if you don't want to go back to it
            }
        });

        // Lấy GridLayout
        GridLayout buttonGrid = findViewById(R.id.button_grid);

        // Thiết lập OnClickListener cho từng nút
        for (int i = 0; i < buttonGrid.getChildCount(); i++) {
            final int examIndex = i; // Lưu giá trị index của nút
            View child = buttonGrid.getChildAt(i);
            if (child instanceof Button) {
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Chuyển đến TestActivity và truyền giá trị examIndex
                        Intent intent = new Intent(ExamsActivity.this, TestActivity.class);
                        intent.putExtra("examsIndex", examIndex); // Truyền examsIndex vào TestActivity
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
