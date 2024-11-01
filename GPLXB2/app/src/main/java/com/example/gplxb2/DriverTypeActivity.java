package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DriverTypeActivity extends AppCompatActivity {
    private RecyclerView recyclerView; // RecyclerView to display the list of driver types
    private DriverTypeAdapter adapter; // Adapter for the RecyclerView
    private List<DriverType> driverTypeList; // List to hold DriverType objects
    private ImageButton backButton; // Back button to navigate to the main activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_type); // Set the content view for this activity

        // Initialize the RecyclerView and the back button
        recyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.back_button);
        driverTypeList = new ArrayList<>(); // Initialize the driver type list

        loadDriverTypes(); // Load driver types from JSON file

        // Set up the adapter for the RecyclerView
        adapter = new DriverTypeAdapter(this, driverTypeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Use a linear layout manager
        recyclerView.setAdapter(adapter); // Set the adapter to the RecyclerView

        // Set up the click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate back to MainActivity
                Intent intent = new Intent(DriverTypeActivity.this, MainActivity.class);
                startActivity(intent); // Start MainActivity
                finish(); // Finish this activity
            }
        });
    }

    // Method to load driver types from the JSON file
    private void loadDriverTypes() {
        try {
            // Open the driver_type.json file from assets
            InputStream is = getAssets().open("driver_type.json");
            byte[] buffer = new byte[is.available()]; // Create a buffer to read the file
            is.read(buffer); // Read the file into the buffer
            is.close(); // Close the input stream

            // Convert the buffer to a string
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(json);
            JSONArray typesArray = jsonObject.getJSONArray("types"); // Get the array of types

            // Loop through the array and create DriverType objects
            for (int i = 0; i < typesArray.length(); i++) {
                JSONObject typeObject = typesArray.getJSONObject(i); // Get each type object

                // Extract the properties of the driver type
                int id = typeObject.getInt("id");
                String title = typeObject.getString("title");
                int quantity = typeObject.getInt("quantity");
                String description = typeObject.getString("description");

                // Create a new DriverType object and add it to the list
                DriverType driverType = new DriverType(id, title, quantity, description);
                driverTypeList.add(driverType);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            Toast.makeText(this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show(); // Show error message
        }
    }
}
