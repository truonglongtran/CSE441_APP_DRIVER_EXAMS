package com.example.gplxb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("examsIndex", 40); // Truyền giá trị -1 cho btnRandom
                startActivity(intent);
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
    }
}
