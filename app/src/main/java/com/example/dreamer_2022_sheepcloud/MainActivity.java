package com.example.dreamer_2022_sheepcloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView textv; // n월 달 text
    final String TAG = this.getClass().getSimpleName();
    LinearLayout container;
    BottomNavigationView bottomNavigationView;
    Fragment fragment_list;
    Fragment fragment_write;
    Fragment fragment_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textv = findViewById(R.id.t_month);

        // 타이틀바 지우는 거임!!!!!!!! 지우지 마셈!!!!!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 메뉴바에서 화면 넘기기
        init(); // 객체 정의
        fragment_list = new List();
        fragment_write = new Write();
        fragment_user = new User();
    }

    private void init() {
        container = findViewById(R.id.container);
        bottomNavigationView = findViewById(R.id.tab_bar);
    }

    private void SettingListener() {
        // 선택 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_list:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new List()).commit();
                    return true;
                case R.id.tab_write:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Write()).commit();
                    return true;
                case R.id.tab_mypage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new User()).commit();
                    return true;
            }
            return false;
        }
    }

    // n월 달 text

    @Override
    protected void onStart() {
        getDT();    // n월 달 실행
        super.onStart();
    }

    public void getDT() {
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MONTH) + 1;
        textv.setText(m+"월 달의");
    }
}