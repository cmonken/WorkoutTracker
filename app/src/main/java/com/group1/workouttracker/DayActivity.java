/* Some code used adapted from SQLite Tutorial located at
http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */

package com.group1.workouttracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.List;

public class DayActivity extends Activity {
    //does not extend ListActivity, so list functions must be called by myList object

    private String buttonClicked;
    private View v;
    private Button reportsBtn;
    private Button helpBtn;
    private Intent intent;
    private MySQLiteHelper helper;
    private WorkoutDataSource datasource;
    private ArrayAdapter<Exercise> adapter;
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_day);
        Intent intent = getIntent();
        helper = new MySQLiteHelper(this);
        reportsBtn = (Button) findViewById(R.id.button8);
        helpBtn = (Button) findViewById(R.id.button9);
        buttonClicked = intent.getStringExtra("Day");
        //callToast(); //used for testing

        datasource = new WorkoutDataSource(this); //create new datasource
        datasource.open(); //open the datasource

        ListView myList = (ListView) findViewById(android.R.id.list);
        TextView excerciseLabel = new TextView(this);
        //.setText("Add new session...");
        //listView.addHeaderView(sessionLabel);
        //buttonClicked has to be a day to even be in DayActivity
        List<Exercise> values = datasource.getExercisesFor(buttonClicked);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        /*ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(this,
                android.R.layout.simple_list_item_1, values);
        myList.setAdapter(adapter); //bind adapter to myList*/

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

        /*myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });*/
    }

    public void myClickHandler(View target) {
        buttonSetDay(target);
        Toast.makeText(getApplicationContext(), buttonClicked, Toast.LENGTH_SHORT).show();
        if (buttonClicked == "Reports") {
            callReportsIntent();
        } else if (buttonClicked == "Help") {
            callHelpIntent();
        }
    }

    public void callReportsIntent() {
        intent = new Intent(this, ReportsActivity.class);
        intent.putExtra("Reports", buttonClicked); //may remove this line once testing complete
        startActivity(intent);
    }

    public void callHelpIntent() {
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

    /*public List<Exercise> getAllExercises() {
        ArrayAdapter<Exercise> adapter = (ArrayAdapter<Exercise>) get();
        // Select All Query
        String selectQuery = "SELECT  * FROM contacts";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setExercise(cursor.getString(1));
                // Adding exercise to list
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }// return contact list
        return exerciseList;
    }*/

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

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


}