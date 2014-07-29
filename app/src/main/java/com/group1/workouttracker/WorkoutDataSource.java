package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/28/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * modified to store data required for the WorkoutTracker app
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WorkoutDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] DayColumns = { MySQLiteHelper.KEY_ID, MySQLiteHelper.COLUMN_WEEKDAY, MySQLiteHelper.COLUMN_SUMMARY };
    private String[] ExerciseColumns = { MySQLiteHelper.KEY_ID, MySQLiteHelper.COLUMN_EXERCISE, MySQLiteHelper.COLUMN_WEEKDAY, MySQLiteHelper.COLUMN_REPETITIONS, MySQLiteHelper.COLUMN_NOTES };
    private String[] HasColumns = { MySQLiteHelper.KEY_ID, MySQLiteHelper.COLUMN_EXERCISE_ID, MySQLiteHelper.COLUMN_HISTORY_ID };
    private String[] HistoryColumns = { MySQLiteHelper.KEY_ID, MySQLiteHelper.COLUMN_EXERCISE, MySQLiteHelper.COLUMN_WEEKDAY, MySQLiteHelper.COLUMN_REPETITIONS, MySQLiteHelper.COLUMN_NOTES, MySQLiteHelper.COLUMN_DATE, MySQLiteHelper.COLUMN_COMPLETED_TIME };

    public WorkoutDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

     public Exercise createExercise(String exercise, String weekday, Integer repetitions, String notes) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_EXERCISE, exercise);
        values.put(MySQLiteHelper.COLUMN_WEEKDAY, weekday);
        values.put(MySQLiteHelper.COLUMN_REPETITIONS, repetitions);
        values.put(MySQLiteHelper.COLUMN_NOTES, notes);
        long insertId = database.insert(MySQLiteHelper.TABLE_EXERCISES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISES,
                ExerciseColumns, MySQLiteHelper.KEY_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Exercise newExercise = cursorToExercise(cursor);
        cursor.close();
        return newExercise;
    }

    public void deleteExercise(Exercise exercise) {
        long id = exercise.getId();
        System.out.println("Exercise deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_EXERCISES, MySQLiteHelper.KEY_ID
                + " = " + id, null);
    }

    public List<Exercise> getExercisesFor(String day) {
        List<Exercise> exercises = new ArrayList<Exercise>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXERCISES,
                ExerciseColumns, "weekday = ?", new String[] {day}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return exercises;
    }

    private Exercise cursorToExercise(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setExercise(cursor.getString(1));
        exercise.setWeekday(cursor.getString(2));
        exercise.setRepetitions(cursor.getInt(3));
        exercise.setNotes(cursor.getString(4));

        return exercise;
    }

    public DayOfWeek createDayOfWeek(String weekday, String summary) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_WEEKDAY, weekday);
        values.put(MySQLiteHelper.COLUMN_SUMMARY, summary);
        long insertId = database.insert(MySQLiteHelper.TABLE_DAYOFWEEK, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYOFWEEK,
                DayColumns, MySQLiteHelper.KEY_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        DayOfWeek newDayOfWeek = cursorToDayOfWeek(cursor);
        cursor.close();
        return newDayOfWeek;
    }

    public void deleteDayOfWeek(DayOfWeek dayofweek) {
        long id = dayofweek.getId();
        System.out.println("DayOfWeek deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_DAYOFWEEK, MySQLiteHelper.KEY_ID
                + " = " + id, null);
    }

    public List<DayOfWeek> getDayOfWeek() {
        List<DayOfWeek> daysofweek = new ArrayList<DayOfWeek>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYOFWEEK,
                DayColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DayOfWeek dayofweek = cursorToDayOfWeek(cursor);
            daysofweek.add(dayofweek);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return daysofweek;
    }

    private DayOfWeek cursorToDayOfWeek(Cursor cursor) {
        DayOfWeek dayofweek = new DayOfWeek();
        dayofweek.setId(cursor.getLong(0));
        dayofweek.setWeekday(cursor.getString(1));
        dayofweek.setSummary(cursor.getString(2));

        return dayofweek;
    }

}