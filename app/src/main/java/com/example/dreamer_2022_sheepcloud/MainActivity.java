package com.example.dreamer_2022_sheepcloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView n_texv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        View homeView = View.inflate(this, R.layout.fragment_home, null);
        // 홈 텍스트
        n_texv = homeView.findViewById(R.id.t_textv);

        // 화면 전환
        bottomNavigationView = findViewById(R.id.tab_bar);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new Home()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new List()).commit();
                        return true;
                    case R.id.tab_write:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Write()).commit();
                        return true;
                    case R.id.tab_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new User()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    protected void onStart() {

        super.onStart();
        getDT();
    }

    public void getDT() {
        long now = System.currentTimeMillis();
//        Date date = new Date(now);
        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");  // 형식 지정
        String getTime3 = simpleDateFormat.format(now);*/
        Calendar calendar = Calendar.getInstance();
        int getTime3 = calendar.get(Calendar.MONTH)+1;
        n_texv.setText(getTime3 + "월 달의");
        Log.d("변경날짜", getTime3+"월");
    }
}