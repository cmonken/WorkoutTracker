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

    private long _id;
    private String exercise;
    private String weekday;
    private int repetitions;
    private String notes;
    private Date date;
    private Time time;


    // constructors
    public History( long id, String exercise, String weekday, int repetitions, String notes, Date date, Time time){
        this._id = id;
        this.exercise = exercise;
        this.weekday = weekday;
        this.repetitions = repetitions;
        this.notes = notes;
        this.date = date;
        this.time = time;
    }

    public History( long id, String exercise, String weekday, int repetitions, String notes){
        this._id = id;
        this.exercise = exercise;
        this.weekday = weekday;
        this.repetitions = repetitions;
        this.notes = notes;
    }

    public History(){
    }

    // setters
    public void setId(long id) {
        this._id = id;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    // getters
    public long getId() {
        return _id;
    }

    public String getExercise() {
        return exercise;
    }

    public String getWeekday() {
        return weekday;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public String getNotes() {
        return notes;
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
        return exercise + " " + weekday + " " + repetitions + " " + notes + " " + date + ", " + time;
    }
}