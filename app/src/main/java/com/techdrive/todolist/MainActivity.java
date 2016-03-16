package com.techdrive.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity{


    //// variable deaclear

    public static final String ROW_ID = "row_id";
    public static final String TITLE = "title";

    public static final String DATE = "date";
    private ListView listView;

    private DatabaseConnector databaseConnector;
    private ListView noteListView;
    private CursorAdapter noteAdapter;
    static String[] from = new String[]{TITLE , DATE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         int[] to = new int[] {R.id.ViewTitleNotes , R.id.date_show};
        listView = (ListView) findViewById(R.id.listView);

        databaseConnector = new DatabaseConnector(MainActivity.this);


        Cursor cursor = databaseConnector.listAllNotes();


        new GetNotes().execute(from);



        noteAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.list_note, cursor, from, to);

        noteAdapter.changeCursor(cursor);
        listView.setAdapter(noteAdapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Intent intent = new Intent(MainActivity.this, ViewNote.class);

               intent.putExtra(ROW_ID , id);
               startActivity(intent);
           }
       });
    }


    @Override
    protected void onResume() {
        super.onResume();

        new GetNotes().execute(from);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       switch (id){

           case R.id.add_note:

               Intent intent = new Intent(MainActivity.this , AddEditNote.class);
               startActivity(intent);
               break;

         default:
             break;
       }
        return super.onOptionsItemSelected(item);
    }


    // get note Asyntask

    public class GetNotes extends AsyncTask<Object , Object , Cursor>{
        DatabaseConnector databaseConnector = new DatabaseConnector(MainActivity.this);

        @Override
        protected Cursor doInBackground(Object... params) {



            databaseConnector.open();
            return databaseConnector.listAllNotes();

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            noteAdapter.changeCursor(cursor);
            databaseConnector.close();
        }
    }
}
