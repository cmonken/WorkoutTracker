package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/24/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

import java.sql.Time;
import java.util.Date;

public class History {

    private long id;
    private Date date;
    private Time time;

    // constructors
    public History( long id, Date date, Time time){
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public History(){
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    // getters
    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return date + ", " + time;
    }
}