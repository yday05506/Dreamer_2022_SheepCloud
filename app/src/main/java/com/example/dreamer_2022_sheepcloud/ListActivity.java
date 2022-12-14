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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class ListActivity extends AppCompatActivity {
    ImageButton btnWrite, btnUser, btnHome;
    int id;
    public static final int REQUEST_CODE_INSERT = 1000;

    int countList;  // 글 등록할 때마다 개수 세기
    int countList2;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnWrite = findViewById(R.id.btn_plus);
        btnUser = findViewById(R.id.btn_user);
        btnHome = findViewById(R.id.btn_home);

        listView = findViewById(R.id.listv);

        displayList();

//        Cursor cursor = getMemoCursor();
//        MemoAdapter mAdapter = new MemoAdapter(this, cursor);

        ListViewAdapter adapter = new ListViewAdapter();

//        listView.setAdapter(mAdapter);

//        countList = mAdapter.getCount();
//        countList2 = mAdapter.getCount();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long i) {
                PopupMenu popup = new PopupMenu(ListActivity.this, view);
                getMenuInflater().inflate(R.menu.list_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.update:
                                Intent intent = new Intent(ListActivity.this, WriteActivity.class);

                                Cursor cursor = adapter.getItem(position);

                                id = cursor.getInt(cursor.getColumnIndexOrThrow(Table.Entry._ID));
                                String title = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_TITLE));
                                String content = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_CONTENT));
                                String cate = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_CATE));
                                String date = cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_DATE));

                                intent.putExtra("id", id);
                                intent.putExtra("title", title);
                                intent.putExtra("content", content);
                                intent.putExtra("category", cate);
                                intent.putExtra("date", date);

                                startActivityForResult(intent, REQUEST_CODE_INSERT);
                                break;

                            case R.id.delete:

                                final long deleteld = id;
                                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

                                builder.setTitle("data delete");
                                builder.setMessage("are you going to delete the note?");
                                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        SQLiteDatabase db = DbHelper.getInstance(ListActivity.this).getWritableDatabase();
                                        int deletedCount = db.delete(Table.Entry.TABLE_NAME, Table.Entry._ID + "=" + deleteld, null);

                                        if (deletedCount == 0) {
                                            Toast.makeText(ListActivity.this, "delete error", Toast.LENGTH_SHORT).show();
                                        } else {
//                                            mAdapter.swapCursor(getMemoCursor());
                                            Toast.makeText(ListActivity.this, "delete succeess", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                builder.setNegativeButton("cancel", null);
                                builder.show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });



        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent로 UserActivity로 값 전달
                Intent userIntent = new Intent(ListActivity.this, UserActivity.class);
                userIntent.putExtra("userCountList", countList);
                System.out.println("목록 개수 : " + countList);
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

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent로 UserActivity로 값 전달
                Intent mainIntent = new Intent(ListActivity.this, MainActivity.class);
                mainIntent.putExtra("mainCountList", countList2);
                System.out.println("목록 개수 : " + countList2);
                startActivity(mainIntent);
            }
        });

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    void displayList(){
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        //Cursor라는 그릇에 목록을 담아주기
        Cursor cursor = database.rawQuery("SELECT * FROM data",null);

        ListViewAdapter adapter = new ListViewAdapter();

        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while(cursor.moveToNext()){
            //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번
            adapter.addItemToList(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        //리스트뷰의 어댑터 대상을 여태 설계한 adapter로 설정
        listView.setAdapter(adapter);


    }


//    private Cursor getMemoCursor() {
//        DbHelper dbHelper = DbHelper.getInstance(this);
//        return dbHelper.getReadableDatabase().query(Table.Entry.TABLE_NAME, null, null, null, null, null, null);
//    }

//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(resultCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK) {
//            mAdapter.swapCursor(getMemoCursor());
//        }
//    }

//    static class MemoAdapter extends CursorAdapter {
//
//        public MemoAdapter(Context context, Cursor c) {
//            super(context, c);
//        }
//
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
//        }
//
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            TextView titleText = view.findViewById(android.R.id.text1);
//
//            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(Table.Entry.COLUMN_NAME_TITLE)));
//        }
//
//    }

}