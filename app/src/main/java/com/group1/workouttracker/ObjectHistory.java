package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import java.sql.Time;
import java.util.Date;

public class ObjectHistory {

    private long id;
    private String exerciseName;
    private String dayName;
    private long numReps;
    private String notes;
    private String date;
    private String time;


    // constructors
    public ObjectHistory( long id, String eName, String dName, long nReps, String notes, String date, String time){
        this.id = id;
        this.exerciseName = eName;
        this.dayName = dName;
        this.numReps = nReps;
        this.notes = notes;
        this.date = date;
        this.time = time;
    }

    public ObjectHistory(){
        // blank constructor
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setExerciseName(String eName) {
        this.exerciseName = eName;
    }

    public void setDayName(String dName) {
        this.dayName = dName;
    }

    public void setNumReps(long nReps) {
        this.numReps = nReps;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // getters
    public long getId() {
        return id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getDayName() {
        return dayName;
    }

    public long getNumReps() {
        return numReps;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}