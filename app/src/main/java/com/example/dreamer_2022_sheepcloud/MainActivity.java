package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView btnPlus = findViewById(R.id.btn_plus);
        btnPlus.bringToFront();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}