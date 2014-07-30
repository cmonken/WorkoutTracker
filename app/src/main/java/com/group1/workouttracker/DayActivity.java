package com.group1.workouttracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DayActivity extends Activity {

    String buttonClicked;
    View v;
    Button reportsBtn;
    Button helpBtn;
    Intent intent;
    mySQLiteHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_day);
        Intent intent = getIntent();
        reportsBtn = (Button) findViewById(R.id.button8);
        helpBtn = (Button) findViewById(R.id.button9);
        /*buttonClicked = intent.getStringExtra("Day");
        callToast();*/ //used for testing

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

    public void myClickHandler(View target) {
        buttonSetDay(target);
        Toast.makeText(getApplicationContext(), buttonClicked, Toast.LENGTH_SHORT).show();
        if (buttonClicked == "Reports") {
            callReportsIntent();
        }
        else if (buttonClicked == "Help") {
            callHelpIntent();
        }
    }

    public void callReportsIntent(){
        intent = new Intent(this, ReportsActivity.class);
        intent.putExtra("Reports", buttonClicked); //may remove this line once testing complete
        startActivity(intent);
    }

    public void callHelpIntent(){
        intent = new Intent(this, HelpActivity.class);
        intent.putExtra("Help", buttonClicked); //may remove this line once testing complete
        startActivity(intent);
    }

    public void buttonSetDay(View v) {
        switch (v.getId()) {
            case R.id.button8:
                buttonClicked = "Reports";
                break;
            case R.id.button9:
                buttonClicked = "Help";
                break;
        }
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

    public void callToast(){
        Toast.makeText(getApplicationContext(),"Entered Day Activity. Button clicked: " + buttonClicked, Toast.LENGTH_LONG).show();
    }
}