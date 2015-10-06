package com.cas.squadselector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PickerActivity extends AppCompatActivity {
    private  static final String TAG = "TableTeam";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        Log.d(TAG, "onCreate");

       //  MainActivity.get_dbHandler().teams.populateTeamListView();
        db_TableTeams myteams = new db_TableTeams();
        myteams.populateTeamListView();

        // allow activity to maintain lifetime of cursor
        Log.d(TAG, "populateTeamListView");
        SQLiteDatabase db = MainActivity.get_dbHandler().getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Teams" , new String[] {  });
        startManagingCursor(cursor);  // to stop mem leak

        // setup mapping
        String[] theTeamNames = new String[] { "Team_Name"  };
        int[] toViewIDs = new int[] { R.id.tv_TeamName};

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_team_layout,cursor,theTeamNames,toViewIDs  );
        // set adaptor to listview
        ListView myList = (ListView)findViewById(R.id.lv_Teams);
        myList.setAdapter(myCursorAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String item =  "Position= "+Integer.toString( position) +"  Primary Key= "+ Long.toString( id )  ;  //((ListView)view).getText().toString();
                int result = 0;

                // id is the primary key
                if (id == 3){
                  result =  MainActivity.get_dbHandler().teams.update_TeamName((int)id, "Kim" );

                }

                Toast.makeText(getBaseContext(), item +" result= "+ Integer.toString(result) , Toast.LENGTH_LONG).show();
            }
        });


    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_newteam) {
            Toast.makeText(getApplicationContext(), "Create new Team", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_newmatch) {
           Toast.makeText(getApplicationContext(),"Create new Match",Toast.LENGTH_LONG).show();
           return true;
    }



            return super.onOptionsItemSelected(item);
    }
}
