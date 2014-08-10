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
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class DayActivity extends Activity {
    //does not extend ListActivity, so list functions must be called by myList object

    private String buttonClicked;
    private String thisSummary;
    private Intent intent;
    DatabaseHelper db;
    TextView textViewSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_day);
        db = DatabaseHelper.getInstance(getApplicationContext());

        intent = getIntent();
        buttonClicked = intent.getStringExtra("Day");

        thisSummary = db.readSummary(buttonClicked).getSummary();
        textViewSummary = (TextView) findViewById(R.id.textViewSummary);

        TextView summary = (TextView) findViewById(R.id.textViewSummary);
        summary.setOnLongClickListener(new OnLongClickListenerEditSummary(buttonClicked));
        summary.setText(thisSummary);

        Button buttonCreateExercise = (Button) findViewById(R.id.buttonAddExercise);
        buttonCreateExercise.setOnClickListener(new OnClickListenerCreateExercise(buttonClicked));

        readRecords(buttonClicked);

        Button reportsBtn = (Button) findViewById(R.id.buttonReports);
        reportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReportsIntent();
            }
        });

        Button helpBtn = (Button) findViewById(R.id.buttonHelp);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHelpIntent();
            }
        });

    }

    public void passThroughSummary(ObjectDay obj) {
        textViewSummary.setText(obj.getSummary());
    }

    /*public void passThroughExercise(ObjectExercise obj) {
        readRecords(obj.getDayName());
    }*///replaced with function below -- delete soon

    public void refreshView(String dayName) {
        readRecords(dayName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


     public void readSummary(String buttonClicked) {
        //TextView textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewSummary.setOnLongClickListener(new OnLongClickListenerEditSummary(buttonClicked));
    }

    public void readRecords(String buttonClicked) {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutExercise);
        linearLayoutRecords.removeAllViews();
        final DatabaseHelper helper = DatabaseHelper.getInstance(this);

        final List<ObjectExercise> exercise = helper.getAllExercisesByDay(buttonClicked);

        if (exercise.size() > 0) {

            for (ObjectExercise obj : exercise) {
                final ObjectExercise myExercise = obj; //need to declare this as final in order to call setters
                final Switch completedSwitch = new Switch(this);
                final int id = myExercise.getId();
                String exerciseName = myExercise.getExerciseName();
                int numSets = myExercise.getNumSets();
                String notes = myExercise.getNotes();
                String isCompleted = myExercise.getIsCompleted();
                String textViewContents;

                if(numSets == 1)
                    textViewContents = exerciseName + ", \t" + numSets + " Set, " + "\tNotes: " + notes;
                else
                    textViewContents = exerciseName + ", \t" + numSets + " Sets, " + "\tNotes: " + notes;

                TextView singleExercise = new TextView(this);
                singleExercise.setPadding(10, 10, 10, 10);
                singleExercise.setText(textViewContents);
                singleExercise.setTag(Integer.toString(id));
                singleExercise.setTextSize(16);

                //completedSwitch.setText("Done");
                completedSwitch.setTextOn("Done");
                completedSwitch.setTextOff("Pending");
                //completedSwitch.setGravity(0x05); //right, although these tags don't seem to do anything but cause
                //null pointer exceptions

                //set switch based on whether exercise has been completed
                if(isCompleted == null) {} //do nothing on null
                else if(isCompleted.equals("true")) {
                    completedSwitch.setChecked(true);
                }
                else {
                    completedSwitch.setChecked(false);
                }
                //http://developer.android.com/reference/android/widget/TextView.html#attr_android:gravity
                completedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            myExercise.setIsCompleted("true");
                            myExercise.setDate(getDateTimeWithTimezone());
                            updateDatabase(myExercise);
                            //add record to history table -- exercise id shared with history table
                            boolean createSuccessful = helper.createCompletedRecord(myExercise, myExercise.getId());
                        }
                        else if (isChecked == false) {
                            int id = myExercise.getId();
                            myExercise.setDate(null);
                            boolean deleteSuccessful = helper.deleteCompletedRecord(String.valueOf(id));
                        }
                    }
                });

                singleExercise.setOnLongClickListener(new OnLongClickListenerEditExercise(buttonClicked));
                linearLayoutRecords.addView(singleExercise);
                linearLayoutRecords.addView(completedSwitch);
            }
        }
        else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(10, 10, 10, 10);
            locationItem.setText("No records yet.");
            linearLayoutRecords.addView(locationItem);
        }
    }

    /* simpler implementation -- delete at production stage
    public String getTimeStamp () {
        long timestamp = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("MM-dd-yyyy", cal).toString();
        Toast.makeText(getApplicationContext(), "Date: " + date, Toast.LENGTH_SHORT).show();
        return date;
    }*/

    private String getDateTimeWithTimezone(){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        //Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT")); //can set specific timezone here
        Calendar cal = new GregorianCalendar(TimeZone.getDefault()); //pull timezone from settings
        cal.setTime(date);

        //some of the following code adapted from
        //http://www.deepakgaikwad.net/index.php/2010/02/10/long-time-stamp-to-date-conversion-in-java.html
        String str = ((cal.get(Calendar.MONTH) + 1) //+1 to account for January == 0
                + "/" + cal.get(Calendar.DATE)
                + "/" + cal.get(Calendar.YEAR)
                /*+ " " + cal.get(Calendar.HOUR) //not using time at this point, but it was verified to be accurate
                + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND)
                + " " + (cal.get(Calendar.AM_PM)==0?"AM":"PM")*/
        );

        Toast.makeText(getApplicationContext(), "Date: " + str, Toast.LENGTH_SHORT).show();
        return str;

    }

    public void updateDatabase (ObjectExercise myExercise) {
        DatabaseHelper helper = DatabaseHelper.getInstance(this);
        helper.updateCompleted(myExercise);
    }

    public void callReportsIntent() {
        intent = new Intent(this, ReportsActivity.class);
        startActivity(intent);
    }

    public void callHelpIntent() {
        intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
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