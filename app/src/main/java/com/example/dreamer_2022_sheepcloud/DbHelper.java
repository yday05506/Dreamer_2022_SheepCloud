package com.example.dreamer_2022_sheepcloud;

import static com.example.dreamer_2022_sheepcloud.Table.Entry.COLUMN_NAME_CATE;
import static com.example.dreamer_2022_sheepcloud.Table.Entry.COLUMN_NAME_CONTENT;
import static com.example.dreamer_2022_sheepcloud.Table.Entry.COLUMN_NAME_DATE;
import static com.example.dreamer_2022_sheepcloud.Table.Entry.COLUMN_NAME_TITLE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper sInstance;

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Dreamer_data.db";
    public static final String SQL_CREATE_ENTERS =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                    Table.Entry.TABLE_NAME,
                    Table.Entry._ID,
                    Table.Entry.COLUMN_NAME_TITLE,
                    Table.Entry.COLUMN_NAME_CONTENT,
                    Table.Entry.COLUMN_NAME_CATE,
                    Table.Entry.COLUMN_NAME_DATE);

    public static final String SQL_DELETE_ENTERS =
            "DROP TABLE IF EXISTS " + Table.Entry.TABLE_NAME;


    public static DbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DbHelper(context);
        }
        return sInstance;
    }

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // 최초의 db 생성 부분
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTERS);
        onCreate(db);
    }



}
