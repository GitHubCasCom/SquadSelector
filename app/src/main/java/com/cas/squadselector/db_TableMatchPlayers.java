package com.cas.squadselector;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Clive on 27/08/2015.
 */
public class db_TableMatchPlayers {

    private  static final String TAG = "TableMatchPlayers";
    // Table columns Definitions
    public static final String TABLE_NAME  = "MatchPlayers";
    public static final String KEY_ID                 = "_id";
    public static final String MATCHPLAYER_MATCH_FK   = "MatchPlayer_Match_FK";
    public static final String MATCHPLAYER_PLAYER_FK  = "MatchPlayer_Player_FK";
    public static final String MATCHPLAYER_GOALS      = "MatchPlayer_Goals";
    public static final String MATCHPLAYER_YELLOWS    = "MatchPlayer_Yellows";
    public static final String MATCHPLAYER_REDS       = "MatchPlayer_Reds";
    public static final String MATCHPLAYER_STATUS     = "MatchPlayer_Status";
    public static final String MATCHPLAYER_COMMENTS   = "MatchPlayer_Comments";



    public void createTable(SQLiteDatabase database){

        database.execSQL( "CREATE TABLE " + TABLE_NAME + "("
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + MATCHPLAYER_MATCH_FK + " INTEGER, "
                        + MATCHPLAYER_PLAYER_FK + " INTEGER, "
                        + MATCHPLAYER_GOALS + " INTEGER, "
                        + MATCHPLAYER_YELLOWS + " TEXT, "
                        + MATCHPLAYER_REDS + " INTEGER DEFAULT 0, "
                        + MATCHPLAYER_STATUS + " INTEGER DEFAULT 0, "
                        + MATCHPLAYER_COMMENTS + " INTEGER DEFAULT 0 "
                        +" )"
        );

    }

    public void deleteTable(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

    }


    public void addMatchPlayers(SQLiteDatabase database, MatchPlayersArray ma) {
       // Log.d(TAG, "addMatchPlayers");

        ContentValues values = new ContentValues();
        values.put(MATCHPLAYER_MATCH_FK,   ma.Match_FK);
        values.put(MATCHPLAYER_PLAYER_FK,  ma.Player_FK);
        values.put(MATCHPLAYER_GOALS,      ma.Goals);
        values.put(MATCHPLAYER_YELLOWS,    ma.Yellows);
        values.put(MATCHPLAYER_REDS,       ma.Reds);
        values.put(MATCHPLAYER_STATUS,     ma.Status);
        values.put(MATCHPLAYER_COMMENTS,   ma.Comments);
        database.insert(TABLE_NAME, null, values);
    }




    public void add_Defaults(SQLiteDatabase db){

        MatchPlayersArray mpa = new MatchPlayersArray();
        mpa.Match_FK  = 1;
        mpa.Player_FK = 2;
        mpa.Goals     = 0;
        mpa.Yellows   = 0;
        mpa.Reds      = 0;
        mpa.Status    = 0;
        mpa.Comments    = "None";

        // add 13 players
        for (int i = 1; i < 14 ; i++) {
           mpa.Player_FK = i;
           mpa.Comments = "Player " + Integer.toString(i);

            addMatchPlayers(db, mpa);
        }
    }

}
