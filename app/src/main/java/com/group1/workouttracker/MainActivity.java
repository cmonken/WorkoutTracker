package com.group1.workouttracker;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

public class MainActivity extends Activity {

    Button monBtn;
    Button tueBtn;
    Button wedBtn;
    Button thursBtn;
    Button friBtn;
    Button satBtn;
    Button sunBtn;
    Button reportsBtn;
    Button helpBtn;
    Intent intRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_main);

        monBtn  = (Button) findViewById(R.id.button1);
        tueBtn  = (Button) findViewById(R.id.button2);
        wedBtn  = (Button) findViewById(R.id.button3);
        thursBtn  = (Button) findViewById(R.id.button4);
        friBtn  = (Button) findViewById(R.id.button5);
        satBtn  = (Button) findViewById(R.id.button6);
        sunBtn  = (Button) findViewById(R.id.button7);
        reportsBtn  = (Button) findViewById(R.id.button8);
        helpBtn  = (Button) findViewById(R.id.button9);


        monBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callMonIntent();
            }
        });

        tueBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callTueIntent();
            }
        });

        wedBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callWedIntent();
            }
        });

        thursBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callThursIntent();
            }
        });

        friBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callFriIntent();
            }
        });

        satBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callSatIntent();
            }
        });

        sunBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callSunIntent();
            }
        });

        reportsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callReportsIntent();
            }
        });

        helpBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callHelpIntent();
            }
        });
    }

    public void callMonIntent(){
        intRun = new Intent(this, MonActivity.class);
        startActivity(intRun);
    }

    public void callTueIntent(){
        intRun = new Intent(this, TueActivity.class);
        startActivity(intRun);
    }

    public void callWedIntent(){
        intRun = new Intent(this, WedActivity.class);
        startActivity(intRun);
    }

    public void callThursIntent(){
        intRun = new Intent(this, ThursActivity.class);
        startActivity(intRun);
    }

    public void callFriIntent(){
        intRun = new Intent(this, FriActivity.class);
        startActivity(intRun);
    }

    public void callSatIntent(){
        intRun = new Intent(this, SatActivity.class);
        startActivity(intRun);
    }

    public void callSunIntent(){
        intRun = new Intent(this, SunActivity.class);
        startActivity(intRun);
    }

    public void callReportsIntent(){
        intRun = new Intent(this, ReportsActivity.class);
        startActivity(intRun);
    }

    public void callHelpIntent(){
        intRun = new Intent(this, HelpActivity.class);
        startActivity(intRun);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
