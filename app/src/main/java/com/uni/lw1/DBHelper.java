package com.uni.lw1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Notes_DB";

    public static final String NOTE_TABLE = "note_table";
    public static final String NOTE_ID_COLUMN = "id"; //primary key
    public static final String NOTE_NAME_COLUMN = "name"; //null
    public static final String NOTE_TEXT_COLUMN = "text"; //null
    public static final String NOTE_GROUP_COLUMN = "group"; //null
    public static final String NOTE_FAVORITE_COLUMN = "is_favorite"; //bool, not null
    public static final String NOTE_CREATION_COLUMN = "creation_date"; //sql date
    public static final String NOTE_MODIFICATION_COLUMN = "last_modification_date"; //sql date

    public static final String GROUP_TABLE = "group_table";
    public static final String GROUP_NAME_COLUMN = "name";
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
