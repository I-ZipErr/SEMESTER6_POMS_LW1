package com.uni.lw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 6;
    public static final String DB_NAME = "Notes_DB";

    public static final String NOTE_TABLE = "note_table";
    public static final String NOTE_ID_COLUMN = "id"; //primary key
    public static final String NOTE_NAME_COLUMN = "name"; //null
    public static final String NOTE_TEXT_COLUMN = "note_text"; //null
    public static final String NOTE_GROUP_COLUMN = "note_group"; //null, secondary
    public static final String NOTE_FAVORITE_COLUMN = "is_favorite"; //bool, not null
    public static final String NOTE_CREATION_COLUMN = "creation_date";
    public static final String NOTE_MODIFICATION_COLUMN = "last_modification_date";

    private static final String GROUP_TABLE = "group_table";
    private static final String GROUP_NAME_COLUMN = "name";

    private static DBOpenHelper dbOpenHelper;
    private static SQLiteDatabase db;
    private static ContentValues contentValues;
    private static Cursor cursor;


    public DBOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        dbOpenHelper = this;
        db = dbOpenHelper.getReadableDatabase();
        contentValues = new ContentValues();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + GROUP_TABLE + " ("
                + GROUP_NAME_COLUMN + " TEXT PRIMARY KEY NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NOTE_TABLE + " ("
                + NOTE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0 NOT NULL,"
                + NOTE_NAME_COLUMN + " TEXT DEFAULT NULL, "
                + NOTE_TEXT_COLUMN + " TEXT DEFAULT NULL, "
                + NOTE_GROUP_COLUMN + " TEXT DEFAULT NULL, "
                + NOTE_FAVORITE_COLUMN + " BOOLEAN NOT NULL DEFAULT 0, "
                + NOTE_CREATION_COLUMN + " TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                + NOTE_MODIFICATION_COLUMN + " TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (" + NOTE_GROUP_COLUMN + ") REFERENCES " + GROUP_TABLE + "(" + GROUP_NAME_COLUMN + "));" );
        db.execSQL("INSERT INTO " + NOTE_TABLE + " (" + NOTE_NAME_COLUMN + " , "
                + NOTE_TEXT_COLUMN + ")" +
                "VALUES('Abobo', 'ALASODALSDASDASDASDASDASaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ NOTE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ GROUP_TABLE);
        onCreate(db);
    }

    public static void setIs_favorite(Note note){
        db = dbOpenHelper.getWritableDatabase();
        contentValues.put(DBOpenHelper.NOTE_FAVORITE_COLUMN, note.getIs_favoriteInt());
        db.update(DBOpenHelper.NOTE_TABLE, contentValues, DBOpenHelper.NOTE_ID_COLUMN + " = " + note.getId(), null);
        contentValues.clear();
        db = dbOpenHelper.getReadableDatabase();
    }
    public static void setCurrentMod_date(Note note){ //"room" -- library for DB, 1 newer exists
        db = dbOpenHelper.getWritableDatabase();
        contentValues.put(DBOpenHelper.NOTE_MODIFICATION_COLUMN, note.getModification_date());
        db.update(DBOpenHelper.NOTE_TABLE, contentValues, DBOpenHelper.NOTE_ID_COLUMN + " = " + note.getId(), null);
        contentValues.clear();
        db = dbOpenHelper.getReadableDatabase();
    }
    private boolean SQLToBool(String sqlBool){
        if (sqlBool.equals("1"))
            return true;
        return false;
    }

    public static void fill_list(){
        db = dbOpenHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.NOTE_TABLE, null);
        if(cursor.moveToFirst()) {
            do {
                RecyclerAdapter.addNote(new Note(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_NAME_COLUMN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_TEXT_COLUMN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_GROUP_COLUMN)),
                        dbOpenHelper.SQLToBool(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_FAVORITE_COLUMN))),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_CREATION_COLUMN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_MODIFICATION_COLUMN))
                ));
            }
            while (cursor.moveToNext());
        }
    }
}
