package com.cas.squadselector;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class db_TableTeamSheets {

    private  static final String TAG = "TableTeamSheets";
    // Table columns Definitions
    public static final String TABLE_NAME  = "TeamSheets";
    public static final String KEY_ID                    = "_id";
    public static final String TEAMSHEET_MATCH_FK        = "Teamsheet_Match_FK";
    public static final String TEAMSHEET_MATCHPLAYER_FK  = "Teamsheet_MatchPlayer_FK";
    public static final String TEAMSHEET_PERIOD          = "Teamsheet_Period";
    public static final String TEAMSHEET_FIELDPOSTION    = "Teamsheet_FieldPosition";
    public static final String TEAMSHEET_SCREEN_X        = "Teamsheet_Screen_X";
    public static final String TEAMSHEET_SCREEN_Y        = "Teamsheet_Screen_Y";




    public void createTable(SQLiteDatabase database){

        database.execSQL( "CREATE TABLE " + TABLE_NAME + "("
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TEAMSHEET_MATCH_FK + " INTEGER, "
                        + TEAMSHEET_MATCHPLAYER_FK + " INTEGER DEFAULT 0, "
                        + TEAMSHEET_PERIOD + " INTEGEER INTEGER DEFAULT 0, "
                        + TEAMSHEET_FIELDPOSTION + " INTEGER INTEGER DEFAULT 0, "
                        + TEAMSHEET_SCREEN_X + " INTEGER INTEGER DEFAULT 0, "
                        + TEAMSHEET_SCREEN_Y + " INTEGER INTEGER DEFAULT 0 "
                        +" )"
        );

    }

    public void deleteTable(SQLiteDatabase database){
        database.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    public void addTeamSheet(SQLiteDatabase database, db_TeamSheetArray tsa) {
        //Log.d(TAG, "addTeamSheet");
        ContentValues values = new ContentValues();
        values.put(TEAMSHEET_MATCH_FK,        tsa.Match_FK);
        values.put(TEAMSHEET_MATCHPLAYER_FK,  tsa.Player_FK);
        values.put(TEAMSHEET_PERIOD,          tsa.Period);
        values.put(TEAMSHEET_FIELDPOSTION,    tsa.FieldPosition);
        values.put(TEAMSHEET_SCREEN_X,        tsa.Screen_X);
        values.put(TEAMSHEET_SCREEN_Y,        tsa.Screen_Y);
        database.insert(TABLE_NAME, null, values);
    }



    public void add_Defaults(SQLiteDatabase db){
        db_TeamSheetArray tsa = new db_TeamSheetArray();
        tsa.Match_FK       = 1;
        tsa.Player_FK      = 0;
        tsa.Period         = 0;
        tsa.FieldPosition  = 0;
        tsa.Screen_X       = 0;
        tsa.Screen_Y       = 0;

        // add 13 players
        for (int i = 1; i < 14 ; i++) {
            tsa.Player_FK = i;
            tsa.FieldPosition = i;
            tsa.Screen_X = i;
            tsa.Screen_Y = i * 100;

            addTeamSheet(db, tsa);
        }
    }



}
