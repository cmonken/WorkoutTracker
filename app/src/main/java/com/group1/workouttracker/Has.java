package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/28/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

public class Has {

    private long _id;
    private long exercise_id;
    private long history_id;

    // constructors
    public Has( long id, long exercise_id, long history_id){
        this._id = id;
        this.exercise_id = exercise_id;
        this.history_id = history_id;
    }

    public Has( long exercise_id, long history_id){
        this.exercise_id = exercise_id;
        this.history_id = history_id;
    }

    public Has(){
    }

    // setters
    public void setId(long id) {
        this._id = id;
    }

    public void setExerciseId(long eid) {
        this.exercise_id = eid;
    }

    public void setHistoryId(long hid) {
        this.history_id = hid;
    }

    // getters
    public long getId() {
        return this._id;
    }

    public long getExerciseId() {
        return this.exercise_id;
    }

    public long getHistoryId() {
        return this.history_id;
    }

}