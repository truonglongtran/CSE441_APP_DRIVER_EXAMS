package com.example.gplxb2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("examsIndex", -1); // Truyền giá trị -1 cho btnRandom
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện cho nút chính xác
        ImageButton btnExact = findViewById(R.id.btnexact);
        btnExact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExamsActivity.class);
                startActivity(intent);
            }
        });

        //btn liet
        ImageButton btnLiet = findViewById(R.id.btnliet);
        btnLiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("examsIndex", 60);
                startActivity(intent);
            }
        });

        //btn btntopfalseds
        ImageButton btntopfalseds = findViewById(R.id.btntopfalseds);
        btntopfalseds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("examsIndex", 50); // Truyền giá trị -1 cho btnRandom
                startActivity(intent);
            }
        });

        //btn btntopfalseds
        ImageButton btnfalseds = findViewById(R.id.btnfalseds);
        btnfalseds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                // Lấy danh sách các câu trả lời sai
                ArrayList<Integer> incorrectAnswers = databaseHelper.getIncorrectAnswers();

                if (incorrectAnswers.isEmpty()) {
                    // Nếu không có câu sai nào, hiển thị hộp thoại thông báo
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Thông báo")
                            .setMessage("Bạn không có câu sai nào chưa được sửa.")
                            .setPositiveButton("Đồng ý", (dialog, which) -> {
                                // Trở lại MainActivity
                                dialog.dismiss();
                            })
                            .show();
                } else {
                    // Nếu có câu sai, chuyển đến TestActivity
                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.putExtra("examsIndex", 40); // Truyền giá trị examsIndex = 40
                    startActivity(intent);
                }
            }
        });


        //btn btntrain
        ImageButton btntrain = findViewById(R.id.btntrain);
        btntrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverTypeActivity.class);
                intent.putExtra("examsIndex", 40); // Truyền giá trị -1 cho btnRandom
                startActivity(intent);
            }
        });

        //btn btnbienbao
        ImageButton btnBienBao = findViewById(R.id.btnbienbao);
        btnBienBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
        //btn btnremind
        ImageButton btnRemind = findViewById(R.id.btnremind);
        btnRemind.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, TipsActivity.class);
            startActivity(intent);
        });
        //btn btnmophong
        ImageButton btnmophong = findViewById(R.id.btnmophong);
        btnmophong.setOnClickListener(v -> {
            // Tạo Intent với hành động ACTION_VIEW và URL của Facebook
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.AnG.Pro.Mo.Phong&hl=vi"));
            startActivity(intent); // Mở trình duyệt với URL đã cung cấp
        });
        //btn btndeleteapp
        ImageButton btndeleteapp = findViewById(R.id.btndeleteapp);
        btndeleteapp.setOnClickListener(v -> {
            // Tạo Intent với hành động ACTION_VIEW và URL của Facebook
            Intent intent = new Intent(MainActivity.this, TipsActivity.class);
            startActivity(intent);
        });

        Button btnDeleteData = findViewById(R.id.btnDeleteData);
        btnDeleteData.setOnClickListener(v -> {
            // Tạo hộp thoại xác nhận
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Xác nhận xóa dữ liệu")
                    .setMessage("Bạn có chắc chắn muốn xóa tất cả dữ liệu không?")
                    .setPositiveButton("Đồng ý", (dialog, which) -> {
                        // Người dùng chọn "Đồng ý" => Xóa dữ liệu
                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                        databaseHelper.resetData(); // Gọi phương thức để reset dữ liệu

                        // Tạo Intent trở lại MainActivity
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Xóa các Activity trước đó
                        startActivity(intent);
                        finish(); // Kết thúc Activity hiện tại để tránh quay lại
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        // Người dùng chọn "Hủy" => Đóng hộp thoại và trở về MainActivity
                        dialog.dismiss(); // Đóng hộp thoại
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .show();
        });


    }
}
