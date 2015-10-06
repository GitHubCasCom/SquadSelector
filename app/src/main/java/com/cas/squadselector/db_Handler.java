package com.cas.squadselector;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;



public class db_Handler extends SQLiteOpenHelper  {
    // Static variables
    private static final String TAG = "db_Handler";

    public   static final int    DATABASE_VERSION = 1;
    public   static final String DATABASE_NAME = "squadDB.db3";
    private  static final String DATABASE_BACKUPNAME = "squadDB_backup.db3";
    private  static final String DATABASE_RESCUENAME = "rescue.db3";
    private  static final String DATABASE_BACKUPDIR = "/db_Backups";

    SQLiteDatabase database ;

    Context AppContext;
    db_TableTeamSheets teamsheets;
    db_TableMatchPlayers  matchplayers;
    db_TablePlayers players;
    db_TableMatchs matchs;
    db_TableTeams  teams;


    public db_Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
           AppContext = context;
           Log.d(TAG, "Constructor");

           players = new db_TablePlayers();
           matchs = new db_TableMatchs();
           teams = new db_TableTeams();
           matchplayers = new db_TableMatchPlayers();
           teamsheets = new db_TableTeamSheets();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       // only runs if db does not exsist
        Log.d(TAG, "Database onCreate "+ db.toString());
        // create all the tables in the database
        if ( db != null) {
            Log.d(TAG, "onCreate  create tables ");
            players.createTable(db);
            matchs.createTable(db);
            teams.createTable(db);
            matchplayers.createTable(db);
            teamsheets.createTable(db);
            setup_DefaultData(db);
        }
       else {
            Log.d(TAG, "onCreate  db is NULL ");

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db3, int oldVersion, int newVersion) {
        // drop older table if exists
        Log.d(TAG,"onUpgrade");
        players.deleteTable(db3);
        matchplayers.deleteTable(db3);
        teams.deleteTable(db3);
        teamsheets.deleteTable(db3);
        matchs.deleteTable(db3);
        onCreate(db3);
    }




    public void setup_DefaultData(SQLiteDatabase db3){
       // Log.d(TAG, "setup_DefaultData");
        if(db3 != null) {
            Log.d(TAG, "setup_Default Data:  Adding Default Team/players");
            players.add_Defaults(db3);
            teams.add_Defaults(db3);
            matchs.add_Default(db3);
            matchplayers.add_Defaults(db3);
            teamsheets.add_Defaults(db3);

        }
        {
            Log.d(TAG, "setup_DefaultData  db3 is NULL");
        }

    }


    public  void Backup_Database(Context context) throws IOException {
        final String inFileName = "/data/data/"+MainActivity.PACKAGE_NAME+"/databases/"+DATABASE_NAME;
        String outFileName = Environment.getExternalStorageDirectory()+ DATABASE_BACKUPDIR+"/"+DATABASE_BACKUPNAME;


        Log.d(TAG,"Backup Database "+ DATABASE_NAME   );
        Copy_Database(context,inFileName, outFileName);
    }


    public  void Restore_Database(Context context) throws IOException {
        final String inFileName = Environment.getExternalStorageDirectory()+ DATABASE_BACKUPDIR+"/"+DATABASE_BACKUPNAME;
        String outFileName = "/data/data/"+MainActivity.PACKAGE_NAME+"/databases/"+DATABASE_NAME;

        Log.d(TAG,"Restore Database "+ DATABASE_BACKUPNAME   );
        Copy_Database(context, inFileName, outFileName);
    }

    public  void Copy_Database(Context context, String inFileName, String outFileName)throws IOException {

        Log.d(TAG,"InFile: " + inFileName  );
        Log.d(TAG,"OutFile:  "+ outFileName );

        File dbFile = new File(inFileName);
        if (dbFile.exists()) {
            FileInputStream fis = new FileInputStream(dbFile);
            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            fis.close();
        }
       else {  Log.d(TAG, "File not found " + inFileName);

          // log("Loading Database...");
           Toast.makeText( context ,"File not found "+ inFileName, Toast.LENGTH_LONG)
                    .show();
        }

    }









}
