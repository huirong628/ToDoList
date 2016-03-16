package com.techdrive.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddEditNote extends AppCompatActivity {

    // Declare Variables
    private long rowID;
    private EditText title_edit;
    private EditText note_edit;
    private static final String TITLE = "title";
    private static final String NOTE = "note";


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Locate the EditText in add_note.xml

        title_edit = (EditText) findViewById(R.id.titleEdit);
        note_edit = (EditText) findViewById(R.id.noteEdit);

        // Retrieve the Row ID from ViewNote.java
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rowID = extras.getLong("row_id");
            title_edit.setText(extras.getString(TITLE));
            note_edit.setText(extras.getString(NOTE));
        }
    }

    // Create an ActionBar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Save Note")
                .setOnMenuItemClickListener(this.SaveButtonClickListener)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }

    // Capture save menu item click
    OnMenuItemClickListener SaveButtonClickListener = new OnMenuItemClickListener() {
        public boolean onMenuItemClick(MenuItem item) {

            // Passes the data into saveNote() function
            if (title_edit.getText().length() != 0) {
                AsyncTask<Object, Object, Object> saveNoteAsyncTask = new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        saveNote();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        // Close this activity
                        finish();
                    }
                };
                // Execute the saveNoteAsyncTask AsyncTask above
                saveNoteAsyncTask.execute((Object[]) null);
            }

            else {
                // Display a simple alert dialog that forces user to put in a
                // title
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        AddEditNote.this);
                alert.setTitle("Title is required");
                alert.setMessage("Put in a title for this note");
                alert.setPositiveButton("Okay", null);
                alert.show();
            }

            return false;

        }
    };

    // saveNote() function
    private void saveNote() {
        DatabaseConnector dbConnector = new DatabaseConnector(this);

        if (getIntent().getExtras() == null) {
            // Passes the data to InsertNote in DatabaseConnector.java
            dbConnector.insertData(title_edit.getText().toString(), note_edit
                    .getText().toString(), getDateTime());



        } else {
            // Passes the Row ID and data to UpdateNote in
            // DatabaseConnector.java
            dbConnector.updateData(rowID, title_edit.getText().toString(),
                    note_edit.getText().toString(), getDateTime());
        }
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}