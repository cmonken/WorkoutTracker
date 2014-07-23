package com.group1.workouttracker;

import android.content.Intent;

/**
 * Created by John on 7/23/2014.
 */
public class DayActivity extends MainActivity {
    Intent intent = getIntent();
    String buttonClicked = intent.getStringExtra("Day");


}
