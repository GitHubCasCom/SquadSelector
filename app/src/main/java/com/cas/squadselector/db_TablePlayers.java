package com.cas.squadselector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Database TablePlayers
 *
 */
public class db_TablePlayers  {
    private  static final String TAG = "TablePlayers";
    // Table columns Definitions
    public static final String TABLE_NAME = "Players";

    public static final String PLAYER_ID = "_id";         // PLAYER_PK

    public static final String PLAYER_STATUS    = "Player_Status";
    public static final String PLAYER_TEAM_FK   = "Player_Team_FK";

    public static final String PLAYER_NAME      = "Player_Name";
    public static final String PLAYER_NICKNAME  = "Player_NickName";
    public static final String PLAYER_POSITION  = "Player_Position";

    public static final String PLAYER_SHIRTNO   = "Player_ShirtNo";
    public static final String PLAYER_IMAGENAME = "Player_ImageName";
    public static final String PLAYER_IMAGE     = "Player_Image";
    // Stats
    public static final String PLAYER_GAMES    = "Player_Games";
    public static final String PLAYER_GOALS    = "Player_Goals";
    public static final String PLAYER_YELLOWS  = "Player_Yellows";
    public static final String PLAYER_REDS     = "Player_Reds";

    // Default player values
    public static final String DEFAULT_NAME       =  "Player";
    public static final String DEFAULT_IMAGENAME  =  "player.png";
    public static final String DEFAULT_NICKNAME   =  "Alex";
    public static final String DEFAULT_POSITION   =  "Misc Player";
    public static final int    DEFAULT_TEAM       =  1;


    public void createTable(SQLiteDatabase database){

        Log.d(TAG,"createTable "+TABLE_NAME);

        database.execSQL( "CREATE TABLE " + TABLE_NAME + "("
                + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PLAYER_TEAM_FK + " INTEGER DEFAULT 0, "
                + PLAYER_STATUS + " INTEGER INTEGER DEFAULT 0, "
                + PLAYER_NAME + " TEXT, "
                + PLAYER_NICKNAME + " TEXT, "
                + PLAYER_POSITION + " TEXT, "
                + PLAYER_SHIRTNO + " INTEGER INTEGER DEFAULT 0, "
                + PLAYER_IMAGENAME + " TEXT, "
                + PLAYER_GAMES + " INTEGER INTEGER DEFAULT 0, "
                + PLAYER_GOALS + " INTEGER INTEGER DEFAULT 0, "
                + PLAYER_YELLOWS + " INTEGER INTEGER DEFAULT 0, "
                + PLAYER_REDS + " INTEGER INTEGER DEFAULT 0,"
                + PLAYER_IMAGE + " BLOB"
        +")"
        );

    }

    public void deleteTable(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    public void addPlayer(SQLiteDatabase db, PlayerArray  pa) {
        Log.d(TAG, "addPlayer: "+pa.Name);
       // SQLiteDatabase db1 = this .getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PLAYER_TEAM_FK,   pa.Team_FK);
        values.put(PLAYER_STATUS,    pa.Status);
        values.put(PLAYER_NAME,      pa.Name);
        values.put(PLAYER_NICKNAME,  pa.Nickname);
        values.put(PLAYER_POSITION,  pa.Position);
        values.put(PLAYER_SHIRTNO,   pa.ShirtNo);
        values.put(PLAYER_IMAGENAME, pa.ImageName);
        values.put(PLAYER_GAMES,     pa.Games);
        values.put(PLAYER_GOALS,     pa.Goals);
        values.put(PLAYER_YELLOWS,   pa.Yellows);
        values.put(PLAYER_REDS,      pa.Reds);

        db.insert(TABLE_NAME, null, values);
    }


    public void add_Defaults(SQLiteDatabase database){
        Log.d(TAG, "add_Defaults");

        PlayerArray pa = new PlayerArray();

        pa.Team_FK   = DEFAULT_TEAM;
        pa.ImageName = DEFAULT_IMAGENAME;
        pa.Games     = 0;
        pa.Goals     = 0;
        pa.Yellows   = 0;
        pa.Reds      = 0;
        pa.Name      = DEFAULT_NAME;
        pa.Nickname  = "Nick";

        // add 13 players
        for (int i = 1; i < 14 ; i++) {
            pa.ShirtNo = i;
            pa.Name = DEFAULT_NAME + Integer.toString(i);
            pa.Nickname = DEFAULT_NAME + Integer.toString(i);
            pa.Position = "Field player " + Integer.toString(i);

            addPlayer(database, pa);
        }
    }

    public ArrayList<PlayerArray> get_Players(int TeamID  ){

        ArrayList<PlayerArray> pa = new ArrayList<PlayerArray>();

        SQLiteDatabase  db = MainActivity.get_dbHandler().getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+PLAYER_TEAM_FK+"=?" , new String[] { Integer.toString( TeamID) });
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                PlayerArray playerX = new PlayerArray();
                playerX._id = cursor.getInt(cursor.getColumnIndex(PLAYER_ID));
                playerX.Team_FK = cursor.getInt(cursor.getColumnIndex(PLAYER_TEAM_FK));
                playerX.Status = cursor.getInt(cursor.getColumnIndex(PLAYER_STATUS));
                playerX.Name = cursor.getString(cursor.getColumnIndex(PLAYER_NAME));
                playerX.Nickname = cursor.getString(cursor.getColumnIndex(PLAYER_NICKNAME));
                playerX.Position = cursor.getString(cursor.getColumnIndex(PLAYER_POSITION));
                playerX.ShirtNo = cursor.getInt(cursor.getColumnIndex(PLAYER_SHIRTNO));
                playerX.ImageName = cursor.getString(cursor.getColumnIndex(PLAYER_IMAGENAME));
                playerX.Games = cursor.getInt(cursor.getColumnIndex(PLAYER_GAMES));
                playerX.Goals = cursor.getInt(cursor.getColumnIndex(PLAYER_GOALS));
                playerX.Yellows = cursor.getInt(cursor.getColumnIndex(PLAYER_YELLOWS));
                playerX.Reds = cursor.getInt(cursor.getColumnIndex(PLAYER_REDS));
                pa.add(playerX);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();


        }
        return pa;
    }


}
