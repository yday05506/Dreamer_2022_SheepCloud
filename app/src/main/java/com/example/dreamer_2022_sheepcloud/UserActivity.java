package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UserActivity extends AppCompatActivity {
    ImageButton btnList, btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnList = findViewById(R.id.btn_list);
        btnWrite = findViewById(R.id.btn_plus);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
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
    }
}