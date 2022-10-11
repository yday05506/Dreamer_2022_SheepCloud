package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class WriteActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private long mMemoId = -1;

    TextView textView;
    DatePickerDialog datePickerDialog;
    String[] cultureKind = {"종류 선택", "뮤지컬", "책", "영화", "드라마", "미술관/박물관", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mTitleEditText = findViewById(R.id.write_title);
        mContentEditText = findViewById(R.id.write_contents);

        Intent intent = getIntent();
        if (intent != null) {
            mMemoId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");

            mTitleEditText.setText(title);
            mContentEditText.setText(content);

        }

        textView = findViewById(R.id.write_textView);
        Spinner spinner = findViewById(R.id.write_spinner);

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
                Toast.makeText(this, "save error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        } else {
            int count = db.update(MemoContract.MemoEntry.TABLE_NAME, contentValues, MemoContract.MemoEntry._ID + "=" + mMemoId, null);

            if (count == 0) {
                Toast.makeText(this, "modification error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "modification success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }
        super.onBackPressed();
    }
}