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
import android.widget.Toast;

public class MainActivity extends Activity {

    /*Button monBtn;
    Button tueBtn;
    Button wedBtn;
    Button thursBtn;
    Button friBtn;
    Button satBtn;
    Button sunBtn;
    Button reportsBtn;
    Button helpBtn;*/
    String buttonClicked;
    Intent intent;
    //View v;
    View target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_main);

        /*monBtn = (Button) findViewById(R.id.button1);
        tueBtn = (Button) findViewById(R.id.button2);
        wedBtn = (Button) findViewById(R.id.button3);
        thursBtn = (Button) findViewById(R.id.button4);
        friBtn = (Button) findViewById(R.id.button5);
        satBtn = (Button) findViewById(R.id.button6);
        sunBtn = (Button) findViewById(R.id.button7);
        reportsBtn = (Button) findViewById(R.id.button8);
        helpBtn = (Button) findViewById(R.id.button9);*/
    }

        public void myClickHandler(View target) {
            buttonSetDay(target);
            /*Toast.makeText(getApplicationContext(), buttonClicked,
                    Toast.LENGTH_SHORT).show();*/ //used for testing
            if(buttonClicked == "Reports" || buttonClicked == "Help") {
                if(buttonClicked == "Reports") {
                    callReportsIntent();
                }
                else if(buttonClicked == "Help") {
                    callHelpIntent();
                }
            }
            else if (buttonClicked == "Monday" || buttonClicked == "Tuesday" ||
                    buttonClicked == "Wednesday" || buttonClicked == "Thursday" ||
                    buttonClicked == "Friday" || buttonClicked == "Saturday" ||
                    buttonClicked == "Sunday") {
                callDayIntent();
            }
        }

    public void callDayIntent(){
        intent = new Intent(this, DayActivity.class);
        intent.putExtra("Day", buttonClicked);
        startActivity(intent);
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
            case R.id.button1:
                buttonClicked = "Monday";
                break;
            case R.id.button2:
                buttonClicked = "Tuesday";
                break;
            case R.id.button3:
                buttonClicked = "Wednesday";
                break;
            case R.id.button4:
                buttonClicked = "Thursday";
                break;
            case R.id.button5:
                buttonClicked = "Friday";
                break;
            case R.id.button6:
                buttonClicked = "Saturday";
                break;
            case R.id.button7:
                buttonClicked = "Sunday";
                break;
            case R.id.button8:
                buttonClicked = "Reports";
                break;
            case R.id.button9:
                buttonClicked = "Help";
                break;
        }
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

}