package com.example.gplxb2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SignActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SignAdapter adapter;
    private List<Sign> signList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        recyclerView = findViewById(R.id.recyclerView);
        signList = new ArrayList<>();

        loadSignData();

        adapter = new SignAdapter(this, signList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadSignData() {
        try {
            InputStream is = getAssets().open("BienBao.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray names = jsonObject.names();

            if (names != null) {
                for (int i = 0; i < names.length(); i++) {
                    String key = names.getString(i);
                    JSONArray signArray = jsonObject.getJSONArray(key);

                    signList.add(new Sign(key, "", "", i));

                    for (int j = 0; j < signArray.length(); j++) {
                        JSONObject signObject = signArray.getJSONObject(j);
                        String name = signObject.getString("name");
                        String des = signObject.optString("des", "");

                        String imagePath;
                        if (signObject.has("image") && !signObject.isNull("image")) {
                            imagePath = signObject.getString("image");
                        } else {
                            imagePath = "bienbao.png";
                        }

                        Sign sign = new Sign(name, des, imagePath);
                        signList.add(sign);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
