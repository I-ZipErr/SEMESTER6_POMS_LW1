package com.uni.lw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Notes_DB";

    public static final String NOTE_TABLE = "note_table";
    public static final String NOTE_ID_COLUMN = "id"; //primary key
    public static final String NOTE_NAME_COLUMN = "name"; //null
    public static final String NOTE_TEXT_COLUMN = "note_text"; //null
    public static final String NOTE_GROUP_COLUMN = "note_group"; //null, secondary
    public static final String NOTE_FAVORITE_COLUMN = "is_favorite"; //bool, not null
    public static final String NOTE_CREATION_COLUMN = "creation_date"; //sql date
    public static final String NOTE_MODIFICATION_COLUMN = "last_modification_date"; //sql date

    public static final String GROUP_TABLE = "group_table";
    public static final String GROUP_NAME_COLUMN = "name";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + GROUP_TABLE + " ("
                + GROUP_NAME_COLUMN + "TEXT PRIMARY KEY NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOTE_TABLE + " ("
                + NOTE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0 NOT NULL,"
                + NOTE_NAME_COLUMN + " TEXT, "
                + NOTE_TEXT_COLUMN + " TEXT, "
                + NOTE_GROUP_COLUMN + " TEXT, "
                + NOTE_FAVORITE_COLUMN + "BOOLEAN NOT NULL DEFAULT 0, "
                + NOTE_CREATION_COLUMN + "DATE NOT NULL DEFAULT CURRENT_DATE, "
                + NOTE_MODIFICATION_COLUMN + "DATE NOT NULL DEFAULT CURRENT_DATE,"
                + "FOREIGN KEY (" + NOTE_GROUP_COLUMN + ") REFERENCES " + GROUP_TABLE + "(" + GROUP_NAME_COLUMN + "));" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ NOTE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ GROUP_TABLE);
        onCreate(db);
    }

//    public void setNoteModificationColumn(SQLiteDatabase db, int note_id){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(NOTE_MODIFICATION_COLUMN, db.rawQuery("SELECT DATE('now')", null).moveToFirst();
//        db.update(NOTE_TABLE, contentValues, NOTE_ID_COLUMN + "=" + note_id, null);
//    }
}
