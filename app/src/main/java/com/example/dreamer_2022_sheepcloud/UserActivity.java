package com.example.dreamer_2022_sheepcloud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UserActivity extends AppCompatActivity {
    private static final Bundle REQUEST_CODE = null;
    ImageButton btnList, btnWrite;
    ImageButton u_img; // 프로필
    String imgName = "user.png";    // 이미지 이름

    int countList;  // 목록 센 값 가져오기
    TextView gradeStep;   // 단계 순서

    int[] countCulture = new int[6];   // 카테고리 선택 센 값 가져오기
    TextView[] gradeCulture = new TextView[6];  // 카테고리 선택 단계

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        u_img = findViewById(R.id.ivProfile); // 프로필사진업로드

        gradeStep = findViewById(R.id.grade_text);
        gradeCulture[0] = findViewById(R.id.grade_musical);
        gradeCulture[1] = findViewById(R.id.grade_book);
        gradeCulture[2] = findViewById(R.id.grade_movie);
        gradeCulture[3] = findViewById(R.id.grade_drama);
        gradeCulture[4] = findViewById(R.id.grade_museum);
        gradeCulture[5] = findViewById(R.id.grade_other);

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnList = findViewById(R.id.btn_list);
        btnWrite = findViewById(R.id.btn_plus);

        // ListActivity에 countRegist 값 받아오기
        Intent ListIntent = getIntent();
        countList = ListIntent.getIntExtra("userCountList", 0);

        if(countList <= 10)
            gradeStep.setText("Step 1");
        else if(countList <= 30)
            gradeStep.setText("Step 2");
        else if(countList <= 50)
            gradeStep.setText("Step 3");
        else if(countList <= 80)
            gradeStep.setText("Step 4");
        else gradeStep.setText("램 수면");

        // WriteActivity에 count 값 받아오기
        Intent writeIntent = getIntent();
        countCulture[0] = writeIntent.getIntExtra("countMusical", 0);
        countCulture[1] = writeIntent.getIntExtra("countBook", 0);
        countCulture[2] = writeIntent.getIntExtra("countMovie", 0);
        countCulture[3] = writeIntent.getIntExtra("countDrama", 0);
        countCulture[4] = writeIntent.getIntExtra("countMuseum", 0);
        countCulture[5] = writeIntent.getIntExtra("countOther", 0);

        System.out.println("뮤지컬 : " + countCulture[0]);
        System.out.println("책 : " + countCulture[1]);
        System.out.println("영화 : " + countCulture[2]);
        System.out.println("드라마 : " + countCulture[3]);
        System.out.println("미술관/박물관 : " + countCulture[4]);
        System.out.println("기타 : " + countCulture[5]);

        for(int i = 0; i < countList; i++) {
            if(countCulture[i] <= 10)
                gradeCulture[i].setText("Step 1");
            else if(countCulture[i] <= 30)
                gradeCulture[i].setText("Step 2");
            else if(countCulture[i] <= 50)
                gradeCulture[i].setText("Step 3");
            else if(countCulture[i] <= 80)
                gradeCulture[i].setText("Step 4");
            else gradeCulture[i].setText("렘 수면");
        }

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

        try {
            String imgpath = getCacheDir() + "/" + imgName;  // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            u_img.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지버튼에 셋
            Toast.makeText(getApplicationContext(), "파일 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void bt1(View view) {    // 이미지 고를 갤러리 오픈
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101) {
            if(resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    u_img.setImageBitmap(imgBitmap);    // 선택한 이미지 셋
                    instream.close();   // 스트림 닫아주기
                    saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveBitmapToJpeg(Bitmap imgBitmap) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getCacheDir(), imgName);   // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일 생성
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림 준비
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);    // compress 함수를 사용해 스트림에 비트맵 넣기
            out.close();
            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }
}