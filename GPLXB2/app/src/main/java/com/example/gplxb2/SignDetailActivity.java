package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SignDetailActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail);

        nameTextView = findViewById(R.id.text_name);
        descriptionTextView = findViewById(R.id.text_description);
        imageView = findViewById(R.id.image_view);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String imagePath = intent.getStringExtra("imagePath");

        nameTextView.setText(name);
        descriptionTextView.setText(description);
        Glide.with(this)
                .load(imagePath)
                .placeholder(R.drawable.bienbao)
                .error(R.drawable.bienbao)
                .into(imageView);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); 
            }
        });

        TextView toolbarTitle = findViewById(R.id.txt_detail_sign);
        toolbarTitle.setText(name); // Cập nhật tên biển báo vào TextView
    }
}
