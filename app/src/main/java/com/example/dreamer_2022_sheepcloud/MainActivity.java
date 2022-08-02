package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar; // 액션바
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.DefaultTaskExecutor;

import android.view.View;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 상단 액션바(타이틀바) 숨김
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        textv = findViewById(R.id.t_month);

    }

    @Override
    protected void onStart() {
        getDT();
        super.onStart();
    }

    public void getDT(){
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MONTH) +1;

        textv.setText(m+"월 달의");
    }


}