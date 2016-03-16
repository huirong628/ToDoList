package com.techdrive.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import android.os.AsyncTask;
public class ViewNote extends Activity {


    private long rowID;
    private TextView TitleTv;
    private TextView NoteTv;

    public static final String TITLE = "title";
    public static final String NOTE = "note";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        TitleTv = (TextView) findViewById(R.id.TitleText);
        NoteTv = (TextView) findViewById(R.id.NoteText);
        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong(MainActivity.ROW_ID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadNotes().execute(rowID);
    }

    private class LoadNotes extends AsyncTask<Long , Object , Cursor>{


        DatabaseConnector databaseConnector = new DatabaseConnector(ViewNote.this);


        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.open();
            return databaseConnector.getOneNote(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            super.onPostExecute(result);

            result.moveToLast();
            int TITLE_INDEX = result.getColumnIndex(TITLE);
            int NOTE_INDEX = result.getColumnIndex(NOTE);


            TitleTv.setText(result.getString(TITLE_INDEX));
            NoteTv.setText(result.getString(NOTE_INDEX));

            result.close();
            databaseConnector.close();
        }
    }

}
