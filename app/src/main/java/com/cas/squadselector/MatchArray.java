package com.cas.squadselector;

import android.util.Log;

import java.sql.Date;

/**
 * Created by Clive on 29/08/2015.
 */
public class MatchArray {
    private static final String TAG = "MatchArray";
    // allow to pass data between class's
    public   int _id     = 0;
    public   int Status  = 0;
    public   int Team_FK = 0;

    public   String Date    = "21/10/1954";
    public   String Venue  = "Stanford Bridge";
    public   int HomeGame  = 0;

    public   int Goals_F  = 0;
    public   int Goals_A  = 0;
    public   int Played   = 0;
    public   int Won      = 0;
    public   int Lost     = 0;
    public   int Draw     = 0;
    public   int Duration = 0;

    public void MatchArray (){
        Log.d(TAG, "constructor");


    }

}
