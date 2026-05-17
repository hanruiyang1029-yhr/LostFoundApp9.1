package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnShow, btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btnCreate);
        btnShow = findViewById(R.id.btnShow);
        btnMap = findViewById(R.id.btnMap);

        btnCreate.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddItemActivity.class
            );

            startActivity(intent);
        });

        btnShow.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ItemListActivity.class
            );

            startActivity(intent);
        });

        btnMap.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    MapActivity.class
            );

            startActivity(intent);
        });
    }
}