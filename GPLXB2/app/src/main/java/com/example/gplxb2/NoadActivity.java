package com.example.gplxb2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge rendering for this activity
        setContentView(R.layout.activity_noad); // Set the layout for the activity

        // Check and apply WindowInsets if a Toolbar or root view is found
        View rootView = findViewById(R.id.top_toolbar); // Find the root view or Toolbar
        if (rootView != null) {
            // Set a listener to apply window insets to the root view
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Get insets for system bars
                // Set padding to the root view to accommodate the system bars
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets; // Return the insets to continue processing
            });
        }

        // Back button to return to MainActivity
        ImageButton backButton = findViewById(R.id.back_button); // Find the back button
        backButton.setOnClickListener(view -> finish()); // Close this activity and return to MainActivity
    }
}
