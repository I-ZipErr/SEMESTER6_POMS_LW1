package com.uni.lw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
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
                "VALUES('Abobo', 'ALASODALSDASDASDASDASDASaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');");
        db.execSQL("INSERT INTO " + GROUP_TABLE + " (" + GROUP_NAME_COLUMN +  ")" +
                "VALUES('Работа'),('Дом'),('Семья'),('Повседневность'),('Хобби'),('Здоровье');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ NOTE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ GROUP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ NOTE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ GROUP_TABLE);
        onCreate(db);
    }

    private boolean SQLToBool(String sqlBool){
        if (sqlBool.equals("1"))
            return true;
        return false;
    }

    public static void fill_list(){
        NoteRecyclerAdapter.clearList();
        db = dbOpenHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.NOTE_TABLE, null);
        if(cursor.moveToFirst()) {
            do {
                NoteRecyclerAdapter.addNote(new Note(
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

    public static Note addNote(){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("INSERT INTO " + NOTE_TABLE + " (" + NOTE_NAME_COLUMN + " , "
                + NOTE_TEXT_COLUMN + ")" +
                "VALUES(null, null);");
        db = dbOpenHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE + " WHERE " + NOTE_ID_COLUMN +
                "=(SELECT max(" + NOTE_ID_COLUMN + ") FROM " + NOTE_TABLE + ")", null);
        cursor.moveToFirst();
        return new Note(
                cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_ID_COLUMN)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_NAME_COLUMN)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_TEXT_COLUMN)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_GROUP_COLUMN)),
                dbOpenHelper.SQLToBool(cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_FAVORITE_COLUMN))),
                cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_CREATION_COLUMN)),
                cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.NOTE_MODIFICATION_COLUMN)));
    }

    public static void updateNote(Note note){
        db = dbOpenHelper.getWritableDatabase();
        contentValues.put(DBOpenHelper.NOTE_NAME_COLUMN, note.getName());
        contentValues.put(DBOpenHelper.NOTE_TEXT_COLUMN, note.getText());
        contentValues.put(DBOpenHelper.NOTE_GROUP_COLUMN, note.getGroup());
        contentValues.put(DBOpenHelper.NOTE_FAVORITE_COLUMN, note.getIs_favoriteInt());
        contentValues.put(DBOpenHelper.NOTE_MODIFICATION_COLUMN, note.getModification_date());
        //creation date and ID will never change
        db.update(DBOpenHelper.NOTE_TABLE, contentValues, DBOpenHelper.NOTE_ID_COLUMN + " = " + note.getId(), null);
        contentValues.clear();
        db = dbOpenHelper.getReadableDatabase();
    }
    public static void deleteNote(Note note){
        db = dbOpenHelper.getWritableDatabase();
        db.delete(DBOpenHelper.NOTE_TABLE, DBOpenHelper.NOTE_ID_COLUMN + " = " + note.getId(), null);
        db = dbOpenHelper.getReadableDatabase();
    }
}
