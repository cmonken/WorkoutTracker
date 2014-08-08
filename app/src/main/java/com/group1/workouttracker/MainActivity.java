package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    DatabaseHelper db;
    String buttonClicked;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        db = DatabaseHelper.getInstance(this);
        countRecords();
        readRecords();

    }

    public void countRecords() {
        int recordCount = db.count();
        Toast.makeText(getApplicationContext(), "Exercises in DB: " + recordCount, Toast.LENGTH_LONG).show();
    }

    public void myClickHandler(View target) {
        buttonSetDay(target);
            /*Toast.makeText(getApplicationContext(), buttonClicked,
                    Toast.LENGTH_SHORT).show(); //used for testing*/
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

    public void readRecords() {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectDay> summaries = DatabaseHelper.getInstance(this).readAllSummaries();

        if(summaries.size() > 0 ) {
            for (ObjectDay obj : summaries) {
                long id = obj.getId();
                String weekday = obj.getDayName();
                String summary = obj.getSummary();

                String textViewContents = weekday + " - " + summary;

                TextView textViewLocationItem = new TextView(this);
                textViewLocationItem.setPadding(0, 10, 0, 10);
                textViewLocationItem.setText(textViewContents);
                textViewLocationItem.setTag(""+id);
            }
        }
        else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }

}