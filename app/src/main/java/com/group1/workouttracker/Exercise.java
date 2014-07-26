package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/24/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

public class Exercise {

    private long id;
    private String exercise;
    private String weekday;
    private long repetitions;
    private String notes;

    // constructors
    public Exercise( long id, String exercise, String weekday, long repetitions, String notes){
        this.id = id;
        this.exercise = exercise;
        this.weekday = weekday;
        this.repetitions = repetitions;
        this.notes = notes;
    }

    public Exercise( long id, String exercise, String weekday, long repetitions){
        this.id = id;
        this.exercise = exercise;
        this.weekday = weekday;
        this.repetitions = repetitions;
    }

    public Exercise( long id, String exercise, String weekday){
        this.id = id;
        this.exercise = exercise;
        this.weekday = weekday;
    }

    public Exercise( String exercise, String weekday){
        this.exercise = exercise;
        this.weekday = weekday;
    }

    public Exercise(){
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public void setRepetitions(long repetitions) {
        this.repetitions = repetitions;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // getters
    public long getId() {
        return id;
    }

    public String getExercise() {
        return exercise;
    }

    public String getWeekday() {
        return weekday;
    }

    public long getRepetitions() {
        return repetitions;
    }

    public String getNotes() {
        return notes;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return exercise + " " + repetitions + " " + notes;
    }
}