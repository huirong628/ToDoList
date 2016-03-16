package com.techdrive.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by lolipop on 3/14/16.
 */
public class DatabaseConnector {

    private static final String DB_NAME = "MyNotes";
    public static final String TABLE_NAME = "tablenotes";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String NOTE = "note";
    public static final String ID = "_id";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;
    private DatabaseHelper dbOpenHelper;

   public DatabaseConnector(Context context){

       dbOpenHelper = new DatabaseHelper(context , DB_NAME , null , DATABASE_VERSION);
   }

    /// database open

    public void open(){
        database = dbOpenHelper.getWritableDatabase();

    }

    // databse close

    public void close(){

        if (database!=null){
            database.close();
        }
    }



    // insert data into database

    public void  insertData(String title, String note, String date){

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE , title);
        contentValues.put(NOTE , note);
        contentValues.put(DATE , date);

        open();
        database.insert(TABLE_NAME, null, contentValues);
        close();
    }


    public void updateData( long id, String title, String note, String date){

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE , title);
        contentValues.put(NOTE, note);
        contentValues.put(DATE, date);

        open();

        database.update(TABLE_NAME, contentValues, ID + "=" + id, null);

        close();
    }

    public void deletData(long id)throws  SQLException{
        open();
        database.delete(TABLE_NAME, ID + "=" + id, null);
        close();
    }


    // get all notes

    public Cursor listAllNotes(){
        open();
        return database.query(TABLE_NAME, new String[]{ID , TITLE, DATE}, null , null , null , null ,null);
    }




     /// get a single note
    public Cursor getOneNote(long id){
        open();
        return database.query(TABLE_NAME, null, ID+"="+id, null, null,null,null);
    }

}
