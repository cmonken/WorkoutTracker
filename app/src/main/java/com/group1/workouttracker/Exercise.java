package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/24/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

public class Exercise {

    private long eid;
    private String exercise;
    private String weekday;
    private long repetitions;
    private String notes;

    // constructors
    public Exercise( long eid, String exercise, String weekday, long repetitions, String notes){
        this.eid = eid;
        this.exercise = exercise;
        this.weekday = weekday;
        this.repetitions = repetitions;
        this.notes = notes;
    }

    public Exercise( long eid, String exercise, String weekday, long repetitions){
        this.eid = eid;
        this.exercise = exercise;
        this.weekday = weekday;
        this.repetitions = repetitions;
    }

    public Exercise( long eid, String exercise, String weekday){
        this.eid = eid;
        this.exercise = exercise;
        this.weekday = weekday;
    }

    // setters
    public void setEId(long eid) {
        this.eid = eid;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // getters
    public long getEId() {
        return eid;
    }

    public String getExercise() {
        return exercise;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getNotes() {
        return notes;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return notes;
    }  // need to set toString
}