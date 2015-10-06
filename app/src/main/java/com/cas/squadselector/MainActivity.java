package com.cas.squadselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import static com.cas.squadselector.util.*; // ALLOW acces to other class stat


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static String PACKAGE_NAME;
    public static Context appcontext;
    private PitchView gView;
    public static db_Handler dbH;
    public static Match myMatch;
    Button btn_backup, btn_restore, btn_selectTeam;
    TextView tvlog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // ONLY portrait

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate  MAIN Starting ****************************************************************");
        appcontext = getApplicationContext();
        PACKAGE_NAME = getApplicationContext().getPackageName();



  //      set_ScreenSize(this);
  //      set_HeadSizes(this);
   //     set_Pitch_Areas(0);

        // test to see scale player head
        ImageView imageview = (ImageView) findViewById(R.id.iv1);
        imageview.setImageResource(R.drawable.player);
        imageview.getLayoutParams().height = (int) head_Height_px;
        imageview.getLayoutParams().width = (int) head_Width_px;
        imageview.invalidate();

        dbH = new db_Handler(this);
        dbH.getWritableDatabase();

        myMatch = new Match();

        //setup_Buttons();
        gView = new PitchView(this);

        RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.layout_pitch);

        // Needed this params otherwise some draw rec to canvas did NOT work;
        // FILL_PARENT depreciated
      //  RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);

        lp.addRule(RelativeLayout.ALIGN_TOP);
        gView.setLayoutParams(lp);
    //    gView.setBackgroundColor(Color.GREEN);
        myLayout.addView(gView);


        // setup button
        btn_selectTeam = (Button) findViewById(R.id.btn_SelectTeamMatch);
        btn_selectTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent newIntent =  new Intent( MainActivity.this, PickerActivity.class);
             //   startActivity(newIntent);
                loadSpinnerTeams();
            }

        });

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


    }


    public static db_Handler get_dbHandler(){
        return  dbH;
    }


    public void loadSpinnerTeams(){
      //  SQLiteDatabase db = MainActivity.get_dbHandler().getReadableDatabase();
        List<String> teams = MainActivity.get_dbHandler().teams.getAllTeams();

        TextView tv = (TextView) findViewById(R.id.tv_TeamList);
        tv.setText("Teams\n");
        for (int i = 0; i < teams.size() ; i++) {
          tv.append( teams.get(i)+"\n");
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_picker) {
            Intent newIntentA =  new Intent( MainActivity.this, PickerActivity.class);
            startActivity(newIntentA);

            return true;
        }


        if (id == R.id.action_db_backup) {
            Toast.makeText(getApplicationContext(),"Create Backup of Database",Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_db_restore) {
            Toast.makeText(getApplicationContext(),"Restore Database from backup",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
