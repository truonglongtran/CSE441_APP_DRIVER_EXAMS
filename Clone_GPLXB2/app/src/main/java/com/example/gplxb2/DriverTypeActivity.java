package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private RecyclerView recyclerView;
    private DriverTypeAdapter adapter;
    private List<DriverType> driverTypeList;
    private ImageButton backButton; // Nút Back

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_type);

        recyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.back_button); // Tham chiếu đến nút Back
        driverTypeList = new ArrayList<>();

        loadDriverTypes();

        adapter = new DriverTypeAdapter(this, driverTypeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Thiết lập sự kiện cho nút Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại MainActivity
                Intent intent = new Intent(DriverTypeActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng DriverTypeActivity để không quay lại khi nhấn nút Back
            }
        });
    }

    private void loadDriverTypes() {
        try {
            InputStream is = getAssets().open("driver_type.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray typesArray = jsonObject.getJSONArray("types");

            for (int i = 0; i < typesArray.length(); i++) {
                JSONObject typeObject = typesArray.getJSONObject(i);

                int id = typeObject.getInt("id");
                String title = typeObject.getString("title");
                int quantity = typeObject.getInt("quantity");
                String description = typeObject.getString("description");

                DriverType driverType = new DriverType(id, title, quantity, description);
                driverTypeList.add(driverType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}
