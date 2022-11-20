package com.example.dreamer_2022_sheepcloud;

import android.provider.BaseColumns;

public class Table {

    private Table(){}

    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "data";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_CATE = "category";
    }
}