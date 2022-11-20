package com.example.dreamer_2022_sheepcloud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListActivity extends AppCompatActivity {
    ImageButton btnWrite, btnUser;

    public static final int REQUEST_CODE_INSERT = 1000;
    private MemoAdapter mAdapter;

    int countList;  // 글 등록할 때마다 개수 세기
    int countMusical;   // 뮤지컬 선택
    int countBook;  // 책 선택
    int countMovie; // 영화 선택
    int countDrama; // 드라마 선택
    int countMuseum;    // 미술관/박물관 선택
    int countOther; // 기타 선택

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnWrite = findViewById(R.id.btn_plus);
        btnUser = findViewById(R.id.btn_user);

        ListView listView = findViewById(R.id.listv);

        Cursor cursor = getMemoCursor();
        mAdapter = new MemoAdapter(this, cursor);
        listView.setAdapter(mAdapter);

        countList = mAdapter.getCount();

        // intent로 MainActivity로 값 전달
        Intent mainIntent = new Intent(ListActivity.this, MainActivity.class);
        mainIntent.putExtra("mainCountList", countList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(ListActivity.this, WriteActivity.class);

                Cursor cursor = (Cursor) mAdapter.getItem(position);

                String title = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_CONTENT));
                String cate = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_CATE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_DATE));

                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("cate", cate);
                intent.putExtra("date", date);

                startActivityForResult(intent, REQUEST_CODE_INSERT);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final long deleteld = id;

                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

                builder.setTitle("memo delete");
                builder.setMessage("are you going to delete the note?");
                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        SQLiteDatabase db = DbHelper.getInstance(ListActivity.this).getWritableDatabase();
                        int deletedCount = db.delete(Table.Entry.TABLE_NAME, Table.Entry._ID + "=" + deleteld, null);

                        if (deletedCount == 0) {
                            Toast.makeText(ListActivity.this, "delete error", Toast.LENGTH_SHORT).show();
                        } else {
                            mAdapter.swapCursor(getMemoCursor());
                            Toast.makeText(ListActivity.this, "delete succeess", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("cancel", null);
                builder.show();
            }

        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent로 UserActivity로 값 전달
                Intent userIntent = new Intent(ListActivity.this, UserActivity.class);
                userIntent.putExtra("userCountList", countList);
                startActivity(userIntent);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    private Cursor getMemoCursor() {
        DbHelper dbHelper = DbHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(Table.Entry.TABLE_NAME, null, null, null, null, null, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(resultCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK) {
            mAdapter.swapCursor(getMemoCursor());
        }
    }

    private static class MemoAdapter extends CursorAdapter {

        public MemoAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);

            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_TITLE)));
        }
    }

}
