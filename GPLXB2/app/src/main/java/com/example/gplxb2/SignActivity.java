package com.example.gplxb2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Activity class to display traffic signs
public class SignActivity extends AppCompatActivity {
    private RecyclerView recyclerView; // RecyclerView to display the list of signs
    private SignAdapter adapter; // Adapter to bind data to the RecyclerView
    private List<Sign> signList; // List to hold sign data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign); // Set the layout for this activity

        // Initialize RecyclerView and the sign list
        recyclerView = findViewById(R.id.recyclerView);
        signList = new ArrayList<>();

        loadSignData(); // Load sign data from the JSON file

        // Set up the RecyclerView with the SignAdapter
        adapter = new SignAdapter(this, signList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager for the RecyclerView
        recyclerView.setAdapter(adapter); // Set the adapter for the RecyclerView

        // Initialize the back button and set its click listener
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the activity and return to the previous one
            }
        });
    }

    // Method to load sign data from the JSON file
    private void loadSignData() {
        try {
            // Open the JSON file from assets
            InputStream is = getAssets().open("BienBao.json");
            byte[] buffer = new byte[is.available()]; // Create a buffer to read the file
            is.read(buffer); // Read the file into the buffer
            is.close(); // Close the InputStream

            // Convert the buffer to a String
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(json);
            JSONArray names = jsonObject.names(); // Get the names of the keys in the JSON object

            // Check if names is not null and iterate over the keys
            if (names != null) {
                for (int i = 0; i < names.length(); i++) {
                    String key = names.getString(i); // Get the current key
                    JSONArray signArray = jsonObject.getJSONArray(key); // Get the corresponding array of signs

                    // Add a title sign to the list
                    signList.add(new Sign(key, "", "", i));

                    // Iterate over the sign array and create Sign objects
                    for (int j = 0; j < signArray.length(); j++) {
                        JSONObject signObject = signArray.getJSONObject(j); // Get the sign object
                        String name = signObject.getString("name"); // Get the sign name
                        String des = signObject.optString("des", ""); // Get the sign description (optional)

                        // Determine the image path
                        String imagePath;
                        if (signObject.has("image") && !signObject.isNull("image")) {
                            imagePath = signObject.getString("image"); // Get image path if it exists
                        } else {
                            imagePath = "bienbao.png"; // Use a default image if not found
                        }

                        // Create a new Sign object and add it to the sign list
                        Sign sign = new Sign(name, des, imagePath);
                        signList.add(sign);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            Toast.makeText(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show(); // Show an error message
        }
    }
}
