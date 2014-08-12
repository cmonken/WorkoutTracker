package com.group1.workouttracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class ReportsActivity extends Activity {

    private DatabaseHelper db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reports);
        intent = getIntent();

         //readRecords(buttonClicked);
        readRecords();

        Button helpBtn = (Button) findViewById(R.id.buttonHelp);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHelpIntent();
            }
        });

        Button reportBtn = (Button) findViewById(R.id.buttonReport);
        reportBtn.setOnClickListener(new OnClickListenerCreateReport());

    }

    //used to set textfields on history report
    public void setData(int numExercises, int numRemaining, int numCompleted, float percentage) {
        String percentStr= "";
        setContentView(R.layout.history_report);

        TextView data1 = (TextView) findViewById(R.id.textViewData1);
        TextView data2 = (TextView) findViewById(R.id.textViewData2);
        TextView data3 = (TextView) findViewById(R.id.textViewData3);
        TextView data4 = (TextView) findViewById(R.id.textViewData4);

        data1.setText(Integer.toString(numExercises));
        data2.setText(Integer.toString(numRemaining));
        data3.setText(Integer.toString(numCompleted));
        data4.setText(percentStr + (percentage) * 100 + "%");

    }

    public void callHelpIntent() {
        intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void readRecords() {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutExercise);
        linearLayoutRecords.removeAllViews();
        final DatabaseHelper helper = DatabaseHelper.getInstance(this);

        final List<ObjectExercise> exercise = helper.getAllCompletedExercises();

        if (exercise.size() > 0) {

            for (ObjectExercise obj : exercise) {
                //final ObjectExercise myExercise = obj; //need to declare this as final in order to call setters

                int id = obj.getId();
                String exerciseName = obj.getExerciseName();
                int numSets = obj.getNumSets();
                String notes = obj.getNotes();
                String date = obj.getDate();
                String textViewContents;

                if(numSets == 1) {
                    textViewContents = exerciseName + ", \t" + numSets + " Set" + ", \tNotes: " + notes +
                            "\n Completed on: " + date;
                }
                else {
                    textViewContents = exerciseName + ", \t" + numSets + " Sets" + ", \tNotes: " + notes +
                            "\n Completed on: " + date;
                }

                TextView textViewLocationItem = new TextView(this);
                textViewLocationItem.setPadding(10, 10, 10, 10);
                textViewLocationItem.setText(textViewContents);
                textViewLocationItem.setTag(Integer.toString(id));
                textViewLocationItem.setTextSize(16);

                //no listener for these textViews at this time.. exercise must be updated from DayActivity
                linearLayoutRecords.addView(textViewLocationItem);
            }
        }
        else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(10, 10, 10, 10);
            locationItem.setText("No records yet.");
            linearLayoutRecords.addView(locationItem);
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

    /*public void callToast(){
        Toast.makeText(getApplicationContext(), "Entered Reports Activity. Button clicked: " + buttonClicked, Toast.LENGTH_LONG).show();
    }*/
}
