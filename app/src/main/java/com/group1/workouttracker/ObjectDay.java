package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

public class ObjectDay {


    private int id;
    private String dayName;
    private String summary;

    // constructors
    public ObjectDay ( int id, String dName, String summary){
        this.id = id;
        this.dayName = dName;
        this.summary = summary;
    }

    public ObjectDay (){
        // blank constructor
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDayName(String dName) {
        this.dayName = dName;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getDayName() {
        return dayName;
    }

    public String getSummary() {
        return summary;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return dayName + ": " + summary;
    }
}