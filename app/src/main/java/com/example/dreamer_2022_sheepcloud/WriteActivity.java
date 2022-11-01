package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
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
    ImageButton btnList, btnUser;
    Button btnRegist;

    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("MM / dd");
    String formatDate = mFormat.format(mReDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mTitleEditText = findViewById(R.id.write_title);
        mContentEditText = findViewById(R.id.write_contents);

        textView = findViewById(R.id.write_textView);
        Spinner spinner = findViewById(R.id.write_spinner);
        writeDate = findViewById(R.id.write_date);
        writeDate.setText(formatDate);

        btnList = findViewById(R.id.btn_list);
        btnUser = findViewById(R.id.btn_user);
        btnRegist = findViewById(R.id.write_registration);


        Intent intent = getIntent();
        if (intent != null) {
            mMemoId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");

            mTitleEditText.setText(title);
            mContentEditText.setText(content);

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
                onBackPressed();
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
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

        // 저장
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT, contents);

        // 전달
        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();

        if (mMemoId == -1) {
            long newRowID = db.insert(MemoContract.MemoEntry.TABLE_NAME,
                    null,
                    contentValues);
            if (newRowID == -1) {
                Toast.makeText(this, "저장 오류", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        } else {
            int count = db.update(MemoContract.MemoEntry.TABLE_NAME, contentValues, MemoContract.MemoEntry._ID + "=" + mMemoId, null);

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
