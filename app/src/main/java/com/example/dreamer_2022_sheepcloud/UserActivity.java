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
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        u_img = findViewById(R.id.ivProfile); // 프로필사진업로드

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