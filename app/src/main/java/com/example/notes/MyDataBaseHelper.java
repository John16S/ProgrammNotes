package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public Context context;
    public static final String DATABASE_NAME = "MyNewSQLiteDB.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "MyHowdyNotes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "Note_title";
    public static final String COLUMN_TEXT = "Note_TEXT";

    MyDataBaseHelper(@Nullable Context context) {    //Constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* query - just for creating table for first time */
        String query = "CREATE TABLE "+ TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_TEXT + " TEXT);";
        db.execSQL(query);

        /*------------------First Note as Tutorial-----------------*/
        String textInception = "Title - is your note's header\n" +
                "Note Text - here your input your text\n" +
                "Add - button saves your note in the DataBase\n" +
                "Update - button allows you to rewrite your Title or Text\n" +
                "Delete - button removes current note\n" +
                "Trash - button allows you to delete all the Notes";
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, "Tutorial");    //putting values
        cv.put(COLUMN_TEXT, textInception);
        db.insert(TABLE_NAME, null, cv);    //Adding values to the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);     //Deletes table
        onCreate(db);                                         //Creates new(updated) table
    }

    void addNote(String title, String note_text){
        SQLiteDatabase db = this.getWritableDatabase(); //For writing to db
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);    //putting values
        cv.put(COLUMN_TEXT, note_text);

        db.insert(TABLE_NAME, null, cv);    //Adding values to the table
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase(); //For reading db

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);  //returning values from table
        }
        return cursor;
    }

    void updateData(String row_id, String title, String note_text){
        SQLiteDatabase db = this.getWritableDatabase(); //For writing to db
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);    //putting values
        cv.put(COLUMN_TEXT, note_text);

        db.update(TABLE_NAME, cv, "_id=?", new String[] {row_id});
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[] {row_id});
    }

    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}



/*
    String titleIncep = "Here is your Title of note";
    String textIncep = "Here your write text of note";

    String addInception = "INSERT INTO " + TABLE_NAME + " (" +
            COLUMN_TITLE + ", " + COLUMN_TEXT + ") VALUES " +
            "(" + titleIncep + ", " + textIncep + ");";
        db.execSQL(addInception);*/
