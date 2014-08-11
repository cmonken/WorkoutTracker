package com.group1.workouttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GraphActivity extends Activity {

    private Intent intent;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_graph);
        db = DatabaseHelper.getInstance(getApplicationContext());
        intent = getIntent();
    }
}
