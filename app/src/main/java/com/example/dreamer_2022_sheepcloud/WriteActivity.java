package com.example.dreamer_2022_sheepcloud;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    // db
    private EditText mTitleEditText;
    private EditText mContenEditText;
    private long mMemold = -1;


    TextView textView, writeDate;
    String[] cultureKind = {"종류 선택", "뮤지컬", "책", "영화", "드라마", "미술관/박물관", "기타"};
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

        // db
        mTitleEditText = findViewById(R.id.write_title);
        mContenEditText = findViewById(R.id.write_contents);

        textView = findViewById(R.id.write_textView);
        Spinner spinner = findViewById(R.id.write_spinner);
        writeDate = findViewById(R.id.write_date);
        writeDate.setText(formatDate);

        btnList = findViewById(R.id.btn_list);
        btnUser = findViewById(R.id.btn_user);
        btnRegist = findViewById(R.id.write_registration);

        // db
        Intent intent = getIntent();
        if (intent != null) {
           mMemold = intent.getLongExtra("id", -1);
           String title = intent.getStringExtra("title");
           String content = intent.getStringExtra("content");

           mTitleEditText.setText(title);
           mContenEditText.setText(content);

        }

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "글을 등록하였습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

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

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
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

    // db

    @Override
    public void onBackPressed() {
        String title = mTitleEditText.getText().toString();
        String contents = mContenEditText.getText().toString();

        // sql save
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT, contents);

        // db
        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();

        if (mMemold == -1) {
            long newRowID = db.insert(MemoContract.MemoEntry.TABLE_NAME, null, contentValues);


            if (newRowID == -1) {
                Toast.makeText(this, "save error", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            } else {
                int count = db.update(MemoContract.MemoEntry.TABLE_NAME, contentValues,
                        MemoContract.MemoEntry._ID + "=" + mMemold, null);
            }

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