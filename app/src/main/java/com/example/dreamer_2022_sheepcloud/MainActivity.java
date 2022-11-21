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
    int pillow = R.drawable.pillow;
    int bedding = R.drawable.img;
    int singleBed = R.drawable.singlebed;
    int supersingleBed = R.drawable.supersinglebed;
    int doubleBed = R.drawable.img_2;
    int queenBed = R.drawable.img_3;
    int kingBed = R.drawable.img_1;
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
        Intent ListIntent = getIntent();
        countList = ListIntent.getIntExtra("mainCountList", 0);

        System.out.println("목록 카운트 : " + countList);

        View mImageView = (View) findViewById(R.id.ll);

        if(countList <= 10) mImageView.setBackgroundResource(pillow);
        else if(countList <= 20) mImageView.setBackgroundResource(bedding);
        else if(countList <= 30) mImageView.setBackgroundResource(singleBed);
        else if(countList <= 50) mImageView.setBackgroundResource(supersingleBed);
        else if(countList <= 70) mImageView.setBackgroundResource(doubleBed);
        else if(countList <= 80) mImageView.setBackgroundResource(queenBed);
        else if(countList <= 100) mImageView.setBackgroundResource(kingBed);

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

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
}