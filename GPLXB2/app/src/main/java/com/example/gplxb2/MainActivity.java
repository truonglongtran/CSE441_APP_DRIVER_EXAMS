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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageButton btnRandom = findViewById(R.id.btnrandom);
        btnRandom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra("examsIndex", -1);
            startActivity(intent);
        });

        ImageButton btnExact = findViewById(R.id.btnexact);
        btnExact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExamsActivity.class);
            startActivity(intent);
        });

        ImageButton btnLiet = findViewById(R.id.btnliet);
        btnLiet.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra("examsIndex", 60);
            startActivity(intent);
        });

        ImageButton btntopfalseds = findViewById(R.id.btntopfalseds);
        btntopfalseds.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra("examsIndex", 50);
            startActivity(intent);
        });

        ImageButton btnfalseds = findViewById(R.id.btnfalseds);
        btnfalseds.setOnClickListener(v -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            ArrayList<Integer> incorrectAnswers = databaseHelper.getIncorrectAnswers();

            if (incorrectAnswers.isEmpty()) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn không có câu sai nào chưa được sửa.")
                        .setPositiveButton("Đồng ý", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("examsIndex", 40);
                startActivity(intent);
            }
        });

        ImageButton btntrain = findViewById(R.id.btntrain);
        btntrain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DriverTypeActivity.class);
            intent.putExtra("examsIndex", 40);
            startActivity(intent);
        });

        ImageButton btnBienBao = findViewById(R.id.btnbienbao);
        btnBienBao.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignActivity.class);
            startActivity(intent);
        });

        ImageButton btnRemind = findViewById(R.id.btnremind);
        btnRemind.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TipsActivity.class);
            startActivity(intent);
        });

        ImageButton btnmophong = findViewById(R.id.btnmophong);
        btnmophong.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.AnG.Pro.Mo.Phong&hl=vi"));
            startActivity(intent);
        });

        ImageButton btnnoad = findViewById(R.id.btnnoad);
        btnnoad.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NoadActivity.class);
            startActivity(intent);
        });

        Button btnDeleteData = findViewById(R.id.btnDeleteData);
        btnDeleteData.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Xác nhận xóa dữ liệu")
                    .setMessage("Bạn có chắc chắn muốn xóa tất cả dữ liệu không?")
                    .setPositiveButton("Đồng ý", (dialog, which) -> {
                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                        databaseHelper.resetData();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .show();
        });
    }
}
