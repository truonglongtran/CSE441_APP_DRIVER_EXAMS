package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SignDetailActivity extends AppCompatActivity {
    // Declare TextView and ImageView for displaying name, description, and image
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail); // Set the layout for this activity

        // Initialize views by finding them in the layout
        nameTextView = findViewById(R.id.text_name);
        descriptionTextView = findViewById(R.id.text_description);
        imageView = findViewById(R.id.image_view);

        // Get intent passed from the previous activity
        Intent intent = getIntent();
        String name = intent.getStringExtra("name"); // Get name from intent
        String description = intent.getStringExtra("description"); // Get description from intent
        String imagePath = intent.getStringExtra("imagePath"); // Get image path from intent

        // Set the text of the TextViews with the received data
        nameTextView.setText(name);
        descriptionTextView.setText(description);

        // Use Glide to load the image into the ImageView with placeholder and error images
        Glide.with(this)
                .load(imagePath) // Load image from URL or path
                .placeholder(R.drawable.bienbao) // Placeholder image while loading
                .error(R.drawable.bienbao) // Error image if loading fails
                .into(imageView);

        // Set a click listener on the back button to finish the activity and go back
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Ends the current activity, returning to the previous one
            }
        });

        // Set the toolbar title with the sign's name
        TextView toolbarTitle = findViewById(R.id.txt_detail_sign);
        toolbarTitle.setText(name); // Set title as the sign's name
    }
}
