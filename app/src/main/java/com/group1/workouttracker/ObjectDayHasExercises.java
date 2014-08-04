package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

public class ObjectDayHasExercises {

    private long id;
    private long day_id;
    private long exercise_id;

    // constructors
    public ObjectDayHasExercises( long id, long d_id, long e_id){
        this.id = id;
        this.day_id = d_id;
        this.exercise_id = e_id;
    }

    public ObjectDayHasExercises(){
        // blank constructor
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setDayId(long d_id) {
        this.day_id = d_id;
    }

    public void setExerciseId(long e_id) {
        this.exercise_id = e_id;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public long getDayId() {
        return this.day_id;
    }

    public long getExerciseId() {
        return this.exercise_id;
    }

}