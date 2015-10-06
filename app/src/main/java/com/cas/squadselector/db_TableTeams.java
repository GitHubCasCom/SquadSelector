package com.cas.squadselector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clive on 27/08/2015.
 */
public class db_TableTeams {
    private  static final String TAG = "TableTeam";
    public static final String TABLE_NAME      = "Teams";
    public static final String TEAM_ID         = "_id";
    public static final String TEAM_NAME       = "Team_Name";
    public static final String TEAM_MANAGER    = "Team_Manager";
    public static final String TEAM_CLUB       = "Team_Club";
    public static final String TEAM_IMAGENAME  = "Team_ImageName";

    public static final String DEFAULT_NAME      =  "U18 Youth";
    public static final String DEFAULT_MANAGER   =  "Herman Inniss";
    public static final String DEFAULT_CLUB      =  "Barming Youth F.C.";
    public static final String DEFAULT_IMAGENAME =  "teamimage.png";


    public db_TableTeams (){
        Log.d(TAG, "constructor");

    }


    public void createTable(SQLiteDatabase database){

        database.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                        + TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TEAM_NAME + " TEXT, "
                        + TEAM_MANAGER + " TEXT, "
                        + TEAM_IMAGENAME + " TEXT,"
                        + TEAM_CLUB + " TEXT  "
                        + " )"
        );
    }

    public void deleteTable(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

    }


    public void addTeam(SQLiteDatabase database, String club, String Name, String imagename, String manager) {
        Log.d(TAG, "addTeam");
        // SQLiteDatabase db1 = this .getWritableDatabase();
        ContentValues values = new ContentValues();
        // get new team ID
        values.put(TEAM_NAME, Name);
        values.put(TEAM_MANAGER, manager);
        values.put(TEAM_CLUB, club);
        values.put(TEAM_IMAGENAME, imagename);
        database.insert(TABLE_NAME, null, values);
       // database.close();
    }


    public int update_TeamName(int id, String TeamName){
        int result = 0;
        SQLiteDatabase  db = MainActivity.get_dbHandler().getReadableDatabase();
        if (id>0) {
            ContentValues args = new ContentValues();
            args.put(TEAM_NAME, TeamName);
            db.update(TABLE_NAME, args, TEAM_ID+ " = "+ id, null);

                               // UPDATE Teams SET Team_Name = 'CAS' WHERE _id = 2
 //           String updateQuery = "UPDATE Teams SET " + TEAM_NAME + " = '" + TeamName + "' WHERE " + TEAM_ID + " = " + Integer.toString(id) + ";";
 //           db.execSQL(updateQuery);
           result = 1;
        } else
        { result = 0;}

      db.close();
      return result;
    }

    public void add_Defaults(SQLiteDatabase database){
        Log.d(TAG, "add_Defaults");

        addTeam(database, "Barming FC", "U18 Youth", "clubimage.png", "Heramn Innis");
        addTeam(database, "Barming FC", "U18 Blues", "clubimage.png", "Darren Reed");
        addTeam(database, "Larkfield FC", "U7 Greens", "clubimage.png", "Mr Man");
        addTeam(database, "Bearsted Clowns", "U3 Babys", "clubimage.png", "Alan");



    }

    public List<String> getAllTeams(){
        List<String> teams = new ArrayList<String>();
        SQLiteDatabase  db = MainActivity.get_dbHandler().getReadableDatabase();

        String selectQuery = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                teams.add( cursor.getString(1)+" "+cursor.getString(0) );

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return teams;
    }


    public void populateTeamListView(){
// allow activity to maintain lifetime of cursor
        Log.d(TAG, "populateTeamListView");
/*        SQLiteDatabase  db = MainActivity.get_dbHandler().getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME , new String[] {  });
        startManagingCursor(cursor);  // to stop mem leak

        // setup mapping
        String[] theTeamNames = new String[] { TEAM_NAME  };
        int[] toViewIDs = new int[] { R.id.tv_TeamName};

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_team_layout,cursor,theTeamNames,toViewIDs  );

        // set adaptor to listview
        ListView myList = (ListView)findViewById(R.id.lv_Teams);
        myList.setAdapter(myCursorAdapter);
        */
    }


}
