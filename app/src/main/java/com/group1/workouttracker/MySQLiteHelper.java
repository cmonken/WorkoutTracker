package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/23/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Logcat
    private static final String LOG = "DatabaseHelper";

    // Database name
    private static final String DATABASE_NAME = "workout.db";

    // Database version number
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_EXERCISES = "exercises";
    public static final String TABLE_HAS = "has";
    public static final String TABLE_HISTORY = "history";

    // Common column names
    public static final String KEY_ID = "id";

    // Exercise table columns
    public static final String COLUMN_EXERCISE = "exercise";
    public static final String COLUMN_WEEKDAY = "weekday";
    public static final String COLUMN_REPETITIONS = "repetitions";
    public static final String COLUMN_NOTES = "notes";

    // Has table columns
    public static final String KEY_HISTORY_ID = "history_id";
    public static final String KEY_EXERCISE_ID = "exercise_id";


    // History table columns
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMPLETED_TIME = "time";

    // Exercises table creation statement
    private static final String CREATE_EXERCISE_TABLE = "create table if not exists " + TABLE_EXERCISES + "(" + KEY_ID +
            " integer primary key, " + COLUMN_EXERCISE + " text, " + COLUMN_WEEKDAY + " text, " + COLUMN_REPETITIONS +
            " integer, " + COLUMN_NOTES + " text);";

    // Has table creation statement
    private static final String CREATE_HAS_TABLE = "create table if not exists " + TABLE_HAS + "(" + KEY_ID +
            " integer primary key, " + KEY_EXERCISE_ID + " integer, " + KEY_HISTORY_ID + " integer);";

    // History table creation statement
    private static final String CREATE_HISTORY_TABLE = "create table if not exists " + TABLE_HISTORY + "(" + KEY_ID +
            " integer primary key, " + COLUMN_DATE + " date, " + COLUMN_COMPLETED_TIME + " time);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create required tables
        database.execSQL(CREATE_EXERCISE_TABLE);
        database.execSQL(CREATE_HAS_TABLE);
        database.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion +
            ", which will destroy all old data" );

        // on upgrade drop older tables
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HAS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // create new tables
        onCreate(database);
    }

    public long createExercise(Exercise exercise) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE, exercise.getExercise());
        values.put(COLUMN_WEEKDAY, exercise.getWeekday());
        values.put(COLUMN_REPETITIONS, exercise.getRepetitions());
        values.put(COLUMN_NOTES, exercise.getNotes());

        // insert row
        long exercise_id = database.insert(TABLE_EXERCISES, null, values);

        return exercise_id;
    }

    /**
     * get all exercises for a specific day
     */
    public List<Exercise> getExercisesFor(String weekday){
        List<Exercise> exercises = new ArrayList<Exercise>();
        String selectQuery = "select * from " + TABLE_EXERCISES + " where " + COLUMN_WEEKDAY + " = '" + weekday + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to the list
        if(cursor.moveToFirst()){
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                exercise.setExercise(cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE)));
                exercise.setWeekday(cursor.getString(cursor.getColumnIndex(COLUMN_WEEKDAY)));
                exercise.setRepetitions(cursor.getInt(cursor.getColumnIndex(COLUMN_REPETITIONS)));
                exercise.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_NOTES)));

                // adding to exercise list
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }

        return exercises;
    }

    /**
     * Updating an exercise
     */
    public int updateExercise(Exercise exercise){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE, exercise.getExercise());
        values.put(COLUMN_WEEKDAY, exercise.getWeekday());
        values.put(COLUMN_REPETITIONS, exercise.getRepetitions());
        values.put(COLUMN_NOTES, exercise.getNotes());

        // updating row
        return database.update(TABLE_EXERCISES, values, KEY_ID + " = ?", new String[] { String.valueOf(exercise.getId())});
    }

    /**
     * Deleting an exercise
     */
    public void deleteExercise(long exercise_id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_EXERCISES, KEY_ID + " =?", new String[] { String.valueOf(exercise_id)});
    }
}