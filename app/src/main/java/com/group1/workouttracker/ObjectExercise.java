package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

public class ObjectExercise {

    private long id;
    private String exerciseName;
    private String dayName;
    private long numReps;
    private String notes;

    // constructors
    public ObjectExercise( long id, String eName, String dName, int nReps, String notes){
        this.id = id;
        this.exerciseName = eName;
        this.dayName = dName;
        this.numReps = nReps;
        this.notes = notes;
    }

    public ObjectExercise(){
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

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return "Exercise: " + exerciseName + ", # Reps:  " + numReps + ", Notes: " + notes;
    }
}