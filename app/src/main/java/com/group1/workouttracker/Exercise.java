package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/24/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * modified to store data required for the WorkoutTracker app
 */
public class Exercise {

    private long eid;
    private String exercise;
    private String weekday;
    private long repetitions;
    private String notes;
    private long hid;
    private String date;
    private String time;

    public long getEId() {
        return eid;
    }

    public void setEId(long eid) {
        this.eid = eid;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getHId() {
        return hid;
    }

    public void setHId(long hid) {
        this.hid = hid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return notes;
    }  // need to set toString
}