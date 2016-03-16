package com.techdrive.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lolipop on 3/13/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "MyNotes";
    public static final String TABLE_NAME = "tablenotes";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String NOTE = "note";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DB_NAME , factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "CREATE TABLE " + TABLE_NAME
                + " (_id integer primary key autoincrement," + TITLE + ", " + DATE + ","
                + NOTE + ");";
        db.execSQL(createQuery);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(""+TABLE_NAME);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL(""+TABLE_NAME);
    }
}
