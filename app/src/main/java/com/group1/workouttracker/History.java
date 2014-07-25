package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/24/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

public class History {

    private long hid;
    private String date;
    private String time;

    // constructor
    public History( long hid, String date, String time){
        this.hid = hid;
        this.date = date;
        this.time = time;
    }

    // setters
    public void setHId(long hid) {
        this.hid = hid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // getters
    public long getHId() {
        return hid;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return date + ", " + time;
    }
}