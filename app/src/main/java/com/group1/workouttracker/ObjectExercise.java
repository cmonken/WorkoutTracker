package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

public class ObjectExercise {

    private int id;
    private String exerciseName;
    private String dayName;
    //private int day_id;
    private int numSets;
    private String notes;
    private String isCompleted;

    // constructors
    //public ObjectExercise( int id, String eName, int dID, int nReps, String notes){
    public ObjectExercise( int id, String eName, String dayName, int nReps, String notes, String isCompleted){
        this.id = id;
        this.exerciseName = eName;
        //this.day_id = dID;
        this.dayName = dayName;
        this.numSets = numSets;
        this.notes = notes;
        this.isCompleted = isCompleted;
    }

    public ObjectExercise(){
        // blank constructor
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setExerciseName(String eName) {
        this.exerciseName = eName;
    }

    /*public void setDayID(int dID) {
        this.day_id = dID;
    }*/

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public void setNumSets(int numSets) {
        this.numSets = numSets;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setIsCompleted(String isCompleted) {
        if(isCompleted == null) {} //do nothing on null
        else if(isCompleted.equals("true")) {
            this.isCompleted = "true";
        }
        else
            this.isCompleted = "false";
    }

    // getters

    public int getId() {
        return id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    /*public int getDayID() {
        return day_id;
    }*/

    public String getDayName() {
        return dayName;
    }

    public int getNumSets() {
        return numSets;
    }

    public String getNotes() {
        return notes;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return "Exercise: " + exerciseName + ", # Reps:  " + numSets + ", Notes: " + notes;
    }
}