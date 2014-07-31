package com.group1.workouttracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {

    private String dayClicked;
    private String fieldClicked;
    private String name;
    private int reps;
    private String notes;
    private View target;
    private MySQLiteHelper helper;
    private TextView textView;
    private NumberPicker np;
    private Exercise newExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);
        Intent intent = getIntent();
        dayClicked = intent.getStringExtra("Day");
        //callToast();
        textView = (TextView) findViewById(R.id.editText1);
        np = (NumberPicker) findViewById(R.id.np);
        np.setMinValue(0);
        np.setMaxValue(10);
        np.setValue(1);
    }

    public void addExercise() {
        Exercise newExercise = new Exercise(1, name, notes, reps);
        helper.createExercise(newExercise);
    }

    public void editName() {
        name = textView.getText().toString();
    }

    public void editReps() {
        reps = np.getValue();
    }

    public void editNotes() {
        notes = textView.getText().toString();
    }

    public void myClickHandler(View target) {
        fieldSet(target);
        //Toast.makeText(getApplicationContext(), fieldClicked, Toast.LENGTH_SHORT).show();
        if (fieldClicked == "Edit") {
            editName();
        }
        else if (fieldClicked == "Help") {
            //callHelpIntent();
        }
    }

    public void fieldSet(View v) {
        switch (v.getId()) {
            case R.id.editText1:
                fieldClicked = "Edit Name";
                break;
            case R.id.editText3:
                fieldClicked = "Edit Notes";
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
        Toast.makeText(getApplicationContext(), "Entered Reports Activity. Button clicked: " + fieldClicked, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        editName();
        editReps();
        editNotes();
        addExercise();
    }
}
