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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_noad);

        // Kiểm tra và áp dụng WindowInsets nếu tìm thấy Toolbar hoặc root view
        View rootView = findViewById(R.id.top_toolbar); // Thay thế `R.id.top_toolbar` nếu cần
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Nút back để quay lại MainActivity
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish());  // Đóng Activity này, quay lại MainActivity
    }
}
