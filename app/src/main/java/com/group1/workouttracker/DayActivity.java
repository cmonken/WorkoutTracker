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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

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

    public void passThrough(ObjectDay objDay) {
        textViewSummary.setText(objDay.getSummary());
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

        List<ObjectExercise> exercise = DatabaseHelper.getInstance(this).getAllExercisesByDay(buttonClicked);

        if (exercise.size() > 0) {

            for (ObjectExercise obj : exercise) {

                long id = obj.getId();
                String exerciseName = obj.getExerciseName();
                long numReps = obj.getNumReps();
                String notes = obj.getNotes();

                String textViewContents = exerciseName + ", # Reps: " + numReps + "  Notes: " + notes;

                TextView textViewLocationItem = new TextView(this);
                textViewLocationItem.setPadding(0, 10, 0, 10);
                textViewLocationItem.setText(textViewContents);
                textViewLocationItem.setTag(Integer.toString((int) id));

                textViewLocationItem.setOnLongClickListener(new OnLongClickListenerEditExercise());

                linearLayoutRecords.addView(textViewLocationItem);
            }
        }
        else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
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