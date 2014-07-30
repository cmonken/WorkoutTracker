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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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
    //private ArrayAdapter<Exercise> adapter;
    private ArrayAdapter<String> adapter;
    private ListView myList;
    //List<Exercise> values;
    private String[] values;
    private Exercise newExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_day);

        TextView summary = (TextView) findViewById(R.id.textView1);
        registerForContextMenu(summary);

        Intent intent = getIntent();
        helper = new MySQLiteHelper(this);
        reportsBtn = (Button) findViewById(R.id.button8);
        helpBtn = (Button) findViewById(R.id.button9);
        buttonClicked = intent.getStringExtra("Day");
        //callToast(); //used for testing



        datasource = new WorkoutDataSource(this); //create new datasource
        datasource.open(); //open the datasource

        //Exercise newExercise = new Exercise(1, "pushups", "monday", 3, "I rock at pushups, brah!");
        //addExercise(newExercise);

        myList = (ListView) findViewById(android.R.id.list);
        //buttonClicked has to be a day to even be in DayActivity
        //values = datasource.getExercisesFor(buttonClicked);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        /*ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);*/

        /***Test Code***/
        /*
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        // Assign adapter to ListView
        myList.setAdapter(adapter);

        // ListView Item Click Listener
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) myList.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }
        });*/

        /***End Test Code***/

        //myList.setAdapter(adapter); //bind adapter to myList

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Summary");
        menu.add(0, v.getId(), 0, "Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Edit"){editSummary(item.getItemId());}
        else {return false;}
        return true;
    }

    public void editSummary(int id){
        Toast.makeText(this, "function 1 called", Toast.LENGTH_SHORT).show();
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
        else if (buttonClicked == "Add Exercise") {
            addExercise(newExercise);
        }

    }

    public void callReportsIntent() {
        intent = new Intent(this, ReportsActivity.class);
        intent.putExtra("Reports", buttonClicked); //may remove this line once testing complete
        startActivity(intent);
    }

    public void addExercise(Exercise newExercise) {
        helper.createExercise(newExercise);
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
            case R.id.button11:
                buttonClicked = "Add Exercise";
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