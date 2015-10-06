package com.cas.squadselector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Clive on 27/08/2015.
 */
public class db_TableMatchs {
    private  static final String TAG = "TableMatchs";
    // Table columns Definitions
    public static final String TABLE_NAME = "Matchs";
    public static final String MATCH_ID           = "_id";         // for adapters Views etc
    public static final String MATCH_TEAM_FK    = "Match_Team_FK";
    public static final String MATCH_DATE       = "Match_Date";
    public static final String MATCH_VENUE      = "Match_Venue";
    public static final String MATCH_HOMEGAME   = "Match_HomeGame";
    public static final String MATCH_GOALS_F    = "Match_Goals_F";
    public static final String MATCH_GOALS_A    = "Match_Goals_A";
    public static final String MATCH_PLAYED     = "Match_Played";
    public static final String MATCH_WON        = "Match_Won";
    public static final String MATCH_LOST       = "Match_Lost";
    public static final String MATCH_DRAW       = "Match_Draw";
    public static final String MATCH_DURATION   = "Match_Time";   // 45 mins x2


    public void createTable(SQLiteDatabase database){

        database.execSQL( "CREATE TABLE " + TABLE_NAME + "("
                        + MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + MATCH_TEAM_FK + " INTEGER, "
                        + MATCH_DATE + " DATE, "
                        + MATCH_VENUE + " TEXT, "
                        + MATCH_HOMEGAME + " INTEGER DEFAULT 0, "
                        + MATCH_GOALS_F + " INTEGER INTEGER DEFAULT 0, "
                        + MATCH_GOALS_A + " INTEGER INTEGER DEFAULT 0, "
                        + MATCH_PLAYED + " INTEGER INTEGER DEFAULT 0, "
                        + MATCH_WON + " INTEGER INTEGER DEFAULT 0, "
                        + MATCH_LOST + " INTEGER INTEGER DEFAULT 0, "
                        + MATCH_DRAW + " INTEGER INTEGER DEFAULT 0,"
                        + MATCH_DURATION + " INTEGER INTEGER DEFAULT 0"
                        +" )"
        );

    }

    public void deleteTable(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

    }


    public void addMatch(SQLiteDatabase database, MatchArray ma) {
        Log.d(TAG, "addMatch");
        // SQLiteDatabase db1 = this .getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MATCH_TEAM_FK,  ma.Team_FK);
        values.put(MATCH_DATE,     ma.Date);
        values.put(MATCH_VENUE,    ma.Venue);
        values.put(MATCH_HOMEGAME, ma.Duration);
        values.put(MATCH_GOALS_F,  ma.Goals_F);
        values.put(MATCH_GOALS_A,  ma.Goals_A);
        values.put(MATCH_PLAYED,   ma.Played);
        values.put(MATCH_WON,      ma.Won);
        values.put(MATCH_LOST,     ma.Lost);
        values.put(MATCH_DRAW,     ma.Draw);
        values.put(MATCH_DURATION, ma.Duration);
        database.insert(TABLE_NAME, null, values);
    }

    public void add_Default(SQLiteDatabase database){
        MatchArray match = new MatchArray();

        match.Team_FK  = 1;
        match.Date     = "1/12/2014";
        match.Venue    = "Mereworth";
        match.HomeGame = 1;
        match.Duration = 90;
        match.Goals_F  = 0;
        match.Goals_A  = 0;
        match.Played   = 0;
        match.Won      = 0;
        match.Lost     = 0;
        match.Draw     = 0;
        match.Duration = 0;

        addMatch(database, match);
    }


    public MatchArray load_Match(MatchArray ma,  int MatchID ){

        SQLiteDatabase  db = MainActivity.get_dbHandler().getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+MATCH_ID+"=?" , new String[] { Integer.toString( MatchID) });
        if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                ma._id      = cursor.getInt(cursor.getColumnIndex(MATCH_ID));
                ma.Team_FK  = cursor.getInt(cursor.getColumnIndex(MATCH_TEAM_FK));
                ma.Date     = cursor.getString(cursor.getColumnIndex(MATCH_DATE));
                ma.Venue    = cursor.getString(cursor.getColumnIndex(MATCH_VENUE));
                ma.HomeGame = cursor.getInt(cursor.getColumnIndex(MATCH_HOMEGAME));
                ma.Goals_F  = cursor.getInt(cursor.getColumnIndex(MATCH_GOALS_F));
                ma.Goals_A  = cursor.getInt(cursor.getColumnIndex(MATCH_GOALS_A));
                ma.Played   = cursor.getInt(cursor.getColumnIndex(MATCH_PLAYED));
                ma.Won      = cursor.getInt(cursor.getColumnIndex(MATCH_WON));
                ma.Lost     = cursor.getInt(cursor.getColumnIndex(MATCH_LOST));
                ma.Draw     = cursor.getInt(cursor.getColumnIndex(MATCH_DRAW));
                ma.Duration = cursor.getInt(cursor.getColumnIndex(MATCH_DURATION));

            }
        cursor.close();
        db.close();

        return ma;

    }







}
