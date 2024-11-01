package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Calls the superclass's onCreate method and sets up the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips); // Sets the layout for this activity

        // Finds the back button in the layout by its ID
        ImageButton backButton = findViewById(R.id.back_button);

        // Sets an onClickListener to handle the back button click event
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an intent to navigate back to the MainActivity
                Intent intent = new Intent(TipsActivity.this, MainActivity.class);
                startActivity(intent); // Starts the MainActivity
                finish(); // Finishes the current activity to prevent returning to it
            }
        });
    }
}
