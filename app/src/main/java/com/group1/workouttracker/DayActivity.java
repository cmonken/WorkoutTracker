package com.group1.workouttracker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DayActivity extends MainActivity {

    String buttonClicked;
    View target1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_day);
        Intent intent = getIntent();
        /*buttonClicked = intent.getStringExtra("Day");
        callToast();*/ //used for testing
    }

    public void myClickHandler(View target) {
        buttonSetDay(target1);
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
        else if (buttonClicked == "Back") {
            callMainIntent();
        }
    }

    public void callMainIntent(){
        intent = new Intent(this, MainActivity.class);
        //intent.putExtra("Day", buttonClicked);
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
            case R.id.button10:
                buttonClicked = "Back";
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
