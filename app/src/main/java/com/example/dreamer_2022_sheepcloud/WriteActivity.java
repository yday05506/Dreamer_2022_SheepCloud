package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mContentEditText;

    private long mMemoId = -1;

    TextView textView, writeDate;
    String[] cultureKind = {"    종류 선택", "      뮤지컬", "          책", "        영화", "      드라마", "미술관/박물관", "        기타"};
    ImageButton btnList, btnUser, btnHome;
    Button btnRegist;
    Spinner spinner;

    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("MM / dd");
    String formatDate = mFormat.format(mReDate);

//    int[] countCulture = new int[6];   // 카테고리 선택
    int countMusical;
    int countBook;
    int countMovie;
    int countDrama;
    int countMuseum;
    int countOther;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mTitleEditText = findViewById(R.id.write_title);
        mContentEditText = findViewById(R.id.write_contents);
        textView = findViewById(R.id.write_textView);

        writeDate = findViewById(R.id.write_date);
        writeDate.setText(formatDate);
        spinner = findViewById(R.id.write_spinner);

        btnList = findViewById(R.id.btn_list);
        btnUser = findViewById(R.id.btn_user);
        btnRegist = findViewById(R.id.write_registration);
        btnHome = findViewById(R.id.btn_home);

        Intent intent = getIntent();
        if (intent != null) {
            mMemoId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            String category = intent.getStringExtra("category");

            mTitleEditText.setText(title);
            mContentEditText.setText(content);
            textView.setText(category);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cultureKind);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                textView.setText(cultureKind[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");
            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "글을 등록하였습니다.", Toast.LENGTH_SHORT);
                toast.show();
                if (cultureKind[1].equals(spinner.getSelectedItem())) {
                    countMusical++;
                    System.out.println(countMusical);
                }
                else if(spinner.getSelectedItem() == cultureKind[2]) // cultureKind의 값이 '책'이라면
                    countBook++;    // 책 글 개수 증가
                else if(spinner.getSelectedItem() == cultureKind[3]) // cultureKind의 값이 '영화'라면
                    countMovie++;   // 영화 글 개수 증가
                else if(spinner.getSelectedItem() == cultureKind[4]) // cultureKind의 값이 '드라마'라면
                    countDrama++;   // 드라마 글 개수 증가
                else if(spinner.getSelectedItem() == cultureKind[5]) // cultureKind의 값이 '미술관/박물관'이라면
                    countMuseum++;  // 미술관/박물관 글 개수 증가
                else if(spinner.getSelectedItem() == cultureKind[6]) // cultureKind의 값이 '기타'라면
                    countOther++;   // 기타 글 개수 증가
                System.out.println(countMusical);
                onBackPressed();
            }
        });


        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent로 UserActivity로 값 전달
                Intent userIntent = new Intent(WriteActivity.this, UserActivity.class);
                userIntent.putExtra("userMusicalCount", countMusical);
                userIntent.putExtra("userBookCount", countBook);
                userIntent.putExtra("userMovieCount", countMovie);
                userIntent.putExtra("userDramaCount", countDrama);
                userIntent.putExtra("userMuseumCount", countMuseum);
                userIntent.putExtra("userOtherCount", countOther);
                startActivity(userIntent);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onBackPressed() {
        String title = mTitleEditText.getText().toString();
        String contents = mContentEditText.getText().toString();
        String category = spinner.getSelectedItem().toString();
        String date = writeDate.getText().toString();

        // 저장
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.Entry.COLUMN_NAME_TITLE, title);
        contentValues.put(Table.Entry.COLUMN_NAME_CONTENT, contents);
        contentValues.put(Table.Entry.COLUMN_NAME_CATE, category);
        contentValues.put(Table.Entry.COLUMN_NAME_DATE, date);


        // 전달
        SQLiteDatabase db = DbHelper.getInstance(this).getWritableDatabase();

        if (mMemoId == -1) {
            long newRowID = db.insert(Table.Entry.TABLE_NAME,
                    null, contentValues);
            if (newRowID == -1) {
                Toast.makeText(this, "저장 오류", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        } else {
            int count = db.update(Table.Entry.TABLE_NAME, contentValues, Table.Entry._ID + "=" + mMemoId, null);

            if (count == 0) {
                Toast.makeText(this, "modification 오류", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "modification 완료", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }



        super.onBackPressed();
    }
}
