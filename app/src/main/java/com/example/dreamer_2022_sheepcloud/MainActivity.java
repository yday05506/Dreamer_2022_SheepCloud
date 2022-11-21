package com.example.dreamer_2022_sheepcloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {
    ImageButton btnList, btnWrite, btnUser;
    TextView writeDate;

    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("M월 달의");
    String formatDate = mFormat.format(mReDate);

    int countList;    // 글 등록하면 개수 센 값 가져오기

    // img = 이불, img_1 : 킹사이즈, img_3 : 퀸사이즈, img_2 : 더블사이즈
    int[] images = new int[] {R.drawable.pillow, R.drawable.img, R.drawable.singlebed, R.drawable.supersinglebed, R.drawable.img_2, R.drawable.img_3, R.drawable.img_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnList = findViewById(R.id.btn_list);
        btnWrite = findViewById(R.id.btn_plus);
        btnUser = findViewById(R.id.btn_user);

        writeDate = findViewById(R.id.t_textv);
        writeDate.setText(formatDate);

        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setGravity(Gravity.CENTER);
        linear.setBackgroundColor(Color.LTGRAY);

        // ListActivity에 countRegist 값 받아오기
        Intent listIntent = getIntent();
        countList = listIntent.getIntExtra("count", 0);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        View mImageView = (View) findViewById(R.id.ll);

        System.out.println("목록 카운트 : " + countList);

        if(countList <= 10) mImageView.setBackgroundResource(images[0]);
        else if(countList <= 20) mImageView.setBackgroundResource(images[1]);
        else if(countList <= 30) mImageView.setBackgroundResource(images[2]);
        else if(countList <= 50) mImageView.setBackgroundResource(images[3]);
        else if(countList <= 70) mImageView.setBackgroundResource(images[4]);
        else if(countList <= 80) mImageView.setBackgroundResource(images[5]);
        else if(countList <= 100) mImageView.setBackgroundResource(images[6]);

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
}