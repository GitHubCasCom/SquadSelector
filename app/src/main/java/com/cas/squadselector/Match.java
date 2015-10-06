package com.cas.squadselector;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;
import java.util.List;


public class Match {
    private static final String TAG = "Match";

    public SQLiteDatabase dbMatch;

    public static MatchArray matchArray;
    public List<PlayerArray> players;


    public  Match(){
        Log.d(TAG, "constructor Match");
 //   if (db != null){ dbMatch = db; }
        matchArray = new MatchArray();

        get_Match( 1 ); // get default match



    }


    public void get_Match( int MatchId){
        Log.d(TAG, "get_Match");


        MainActivity.get_dbHandler().matchs.load_Match(matchArray, MatchId);
        Log.d(TAG, "Playing at Venue " + matchArray.Venue);
    }

    public void get_Players(int TeamID){
        players.clear();

      //  players =  MainActivity.get_dbHandler().matchs() get_Players(TeamID);




    }



}
