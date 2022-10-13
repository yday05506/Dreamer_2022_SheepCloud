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

import androidx.recyclerview.widget.RecyclerView;

import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ListActivity extends AppCompatActivity {
    ImageButton btnWrite, btnUser;

    public static final int REQUEST_CODE_INSERT = 1000;
    private MemoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnWrite = findViewById(R.id.btn_plus);
        btnUser = findViewById(R.id.btn_user);

        FloatingActionButton fab = findViewById(R.id.btn_memoplus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ListActivity.this, WriteActivity.class), REQUEST_CODE_INSERT);
            }
        });

        ListView listView = findViewById(R.id.listv);

        Cursor cursor = getMemoCursor();
        mAdapter = new MemoAdapter(this, cursor);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(ListActivity.this, WriteActivity.class);

                Cursor cursor = (Cursor) mAdapter.getItem(position);

                String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_CONTENT));

                intent.putExtra("id", id);
                intent.putExtra("id", title);
                intent.putExtra("id", content);

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
                        SQLiteDatabase db = MemoDbHelper.getInstance(ListActivity.this).getWritableDatabase();
                        int deletedCount = db.delete(MemoContract.MemoEntry.TABLE_NAME, MemoContract.MemoEntry._ID + "=" + deleteld, null);

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
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
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

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    private Cursor getMemoCursor() {
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(MemoContract.MemoEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    protected void onActivityResult(int requsetCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(resultCode, resultCode, data);
        if (requsetCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK) {
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

            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
        }
    }

}