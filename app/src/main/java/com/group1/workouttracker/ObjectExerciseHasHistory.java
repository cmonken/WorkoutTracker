package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

public class ObjectExerciseHasHistory {

    private long id;
    private long exercise_id;
    private long history_id;

    // constructors
    public ObjectExerciseHasHistory( long id, long e_id, long h_id){
        this.id = id;
        this.exercise_id = e_id;
        this.history_id = h_id;
    }

    public ObjectExerciseHasHistory(){
        // blank constructor
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setExerciseId(long e_id) {
        this.exercise_id = e_id;
    }

    public void setHistoryId(long h_id) {
        this.history_id = h_id;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public long getExerciseId() {
        return this.exercise_id;
    }

    public long getHistoryId() {
        return this.history_id;
    }
}