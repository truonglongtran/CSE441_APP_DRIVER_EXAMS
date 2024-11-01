package com.example.gplxb2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge rendering for this activity
        setContentView(R.layout.activity_main); // Set the layout for the main activity

        // Set up a button to start a random test
        ImageButton btnRandom = findViewById(R.id.btnrandom);
        btnRandom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra("examsIndex", -1); // Send an index of -1 for a random test
            startActivity(intent); // Start the TestActivity
        });

        // Set up a button to start the exact exam activity
        ImageButton btnExact = findViewById(R.id.btnexact);
        btnExact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExamsActivity.class);
            startActivity(intent); // Start the ExamsActivity
        });

        // Set up a button to start a specific test (index 60)
        ImageButton btnLiet = findViewById(R.id.btnliet);
        btnLiet.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra("examsIndex", 60); // Send an index of 60 for a specific test
            startActivity(intent); // Start the TestActivity
        });

        // Set up a button to start another specific test (index 50)
        ImageButton btntopfalseds = findViewById(R.id.btntopfalseds);
        btntopfalseds.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra("examsIndex", 50); // Send an index of 50 for another specific test
            startActivity(intent); // Start the TestActivity
        });

        // Set up a button to check for incorrect answers and start the test if there are any
        ImageButton btnfalseds = findViewById(R.id.btnfalseds);
        btnfalseds.setOnClickListener(v -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this); // Create a DatabaseHelper instance
            ArrayList<Integer> incorrectAnswers = databaseHelper.getIncorrectAnswers(); // Retrieve incorrect answers

            if (incorrectAnswers.isEmpty()) {
                // Show an alert dialog if there are no incorrect answers
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Notification") // Set dialog title
                        .setMessage("You have no incorrect answers to correct.") // Set dialog message
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss()) // Dismiss dialog on positive button click
                        .show();
            } else {
                // Start the TestActivity if there are incorrect answers
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("examsIndex", 40); // Send an index of 40 for this test
                startActivity(intent); // Start the TestActivity
            }
        });

        // Set up a button to start the driver type activity
        ImageButton btntrain = findViewById(R.id.btntrain);
        btntrain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DriverTypeActivity.class);
            intent.putExtra("examsIndex", 40); // Send an index of 40
            startActivity(intent); // Start the DriverTypeActivity
        });

        // Set up a button to open the sign activity
        ImageButton btnBienBao = findViewById(R.id.btnbienbao);
        btnBienBao.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignActivity.class);
            startActivity(intent); // Start the SignActivity
        });

        // Set up a button to show tips
        ImageButton btnRemind = findViewById(R.id.btnremind);
        btnRemind.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TipsActivity.class);
            startActivity(intent); // Start the TipsActivity
        });

        // Set up a button to open a link to the app on Google Play
        ImageButton btnmophong = findViewById(R.id.btnmophong);
        btnmophong.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.AnG.Pro.Mo.Phong&hl=vi"));
            startActivity(intent); // Open the app link in a browser
        });

        // Set up a button to start the NoadActivity
        ImageButton btnnoad = findViewById(R.id.btnnoad);
        btnnoad.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NoadActivity.class);
            startActivity(intent); // Start the NoadActivity
        });

        // Set up a button to delete all data with a confirmation dialog
        Button btnDeleteData = findViewById(R.id.btnDeleteData);
        btnDeleteData.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirm Data Deletion") // Set dialog title
                    .setMessage("Are you sure you want to delete all data?") // Set dialog message
                    .setPositiveButton("Yes", (dialog, which) -> {
                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this); // Create a DatabaseHelper instance
                        databaseHelper.resetData(); // Reset all data in the database
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the activity stack
                        startActivity(intent); // Restart MainActivity
                        finish(); // Finish the current activity
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss(); // Dismiss the dialog on cancel
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the activity stack
                        startActivity(intent); // Restart MainActivity
                        finish(); // Finish the current activity
                    })
                    .show(); // Show the dialog
        });
    }
}
