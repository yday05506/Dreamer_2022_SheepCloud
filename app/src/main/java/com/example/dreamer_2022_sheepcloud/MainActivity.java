package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView n_textv; // (1) n월 달의 꿈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        n_textv = findViewById(R.id.t_textv); // (1)
    }


    @Override
    protected void onStart() {
        getDT(); // (1) n월
        super.onStart();
    }

    // (1) n월
    public void getDT(){
        Calendar cal = Calendar.getInstance();
        int m = 0;
        m = cal.get(Calendar.MONTH) +1;
        n_textv.setText(m+"월 달의");
    }


}