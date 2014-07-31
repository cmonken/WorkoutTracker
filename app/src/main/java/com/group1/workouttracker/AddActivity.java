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
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {

    private String fieldClicked;
    private String name;
    View target;
    private MySQLiteHelper helper;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);
        Intent intent = getIntent();
        /*buttonClicked = intent.getStringExtra("Day");
        callToast();*/
        textView = (TextView) findViewById(R.id.textView1);
    }

    public void addExercise(Exercise newExercise) {
        helper.createExercise(newExercise);
    }

    public void editName() {
        name = textView.getText().toString();
    }

    public void editNotes() {
        name = textView.getText().toString();
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
}
