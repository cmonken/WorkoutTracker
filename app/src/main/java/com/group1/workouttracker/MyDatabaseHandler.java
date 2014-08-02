package com.group1.workouttracker;

/**
 * Modified code adapted from SQLite tutorials
 * www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabaseHandler extends MySQLiteHelper {

    public MyDatabaseHandler(Context context) {
        super(context);
    }

    public boolean createExercise(Exercise exercise) {

        ContentValues values = new ContentValues();

        values.put("exercise", exercise.getExercise());
        values.put("weekday", exercise.getWeekday());
        values.put("repetitions", exercise.getRepetitions());
        values.put("notes", exercise.getNotes());

        SQLiteDatabase database = this.getWritableDatabase();

        boolean createSuccessful = database.insert("table_exercise", null, values) > 0;
        database.close();

        return createSuccessful;
    }

/*    public List<Exercise> read() {

        List<Exercise> recordsList = new ArrayList<Exercise>();

        String sql = "SELECT * FROM students ORDER BY id DESC";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String studentFirstname = cursor.getString(cursor.getColumnIndex("firstname"));
                String studentEmail = cursor.getString(cursor.getColumnIndex("email"));

                ObjectStudent objectStudent = new ObjectStudent();
                objectStudent.id = id;
                objectStudent.firstname = studentFirstname;
                objectStudent.email = studentEmail;

                recordsList.add(objectStudent);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    } */

    public Exercise readSingleExercise(int studentId) {

        Exercise exercise = null;

        String sql = "SELECT * FROM table_exercise WHERE _id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            long id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
            String exerciseName = cursor.getString(cursor.getColumnIndex("exercise"));
            String weekday = cursor.getString(cursor.getColumnIndex("weekday"));
            Long repetitions = cursor.getLong(cursor.getColumnIndex("repetitions"));
            String notes = cursor.getString(cursor.getColumnIndex("notes"));

            exercise = new Exercise();
            exercise.setId(id);
            exercise.setExercise(exerciseName);
            exercise.setWeekday(weekday);
            exercise.setRepetitions(repetitions);
            exercise.setNotes(notes);
        }

        cursor.close();
        db.close();

        return exercise;

    }

    public boolean updateExercise(Exercise exercise) {

        ContentValues values = new ContentValues();

        values.put("exercise", exercise.getExercise());
        values.put("weekday", exercise.getWeekday());
        values.put("repetitions", exercise.getRepetitions());
        values.put("notes", exercise.getNotes());

        String where = "_id = ?";

        String[] whereArgs = { Long.toString(exercise.getId()) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("table_exercise", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean deleteExercise(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        boolean deleteSuccessful = db.delete("table_exercise", "_id = " + id, null) > 0;
        db.close();

        return deleteSuccessful;

    }

/*    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM table_exercise";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    } */

    public DayOfWeek readSummary(int weekdayId) {

        DayOfWeek dayofWeek = null;

        String sql = "SELECT * FROM table_dayofweek WHERE _id = " + weekdayId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            long id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
            String weekday = cursor.getString(cursor.getColumnIndex("weekday"));
            String summary = cursor.getString(cursor.getColumnIndex("summary"));

            dayofWeek = new DayOfWeek();
            dayofWeek.setId(id);
            dayofWeek.setWeekday(weekday);
            dayofWeek.setSummary(summary);
        }

        cursor.close();
        db.close();

        return dayofWeek;
    }

    public boolean updateSummary(DayOfWeek dayofweek) {

        ContentValues values = new ContentValues();

        values.put("_id", dayofweek.getId());
        values.put("weekday", dayofweek.getWeekday());
        values.put("summary", dayofweek.getSummary());

        String where = "_id = ?";

        String[] whereArgs = { Long.toString(dayofweek.getId()) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("table_weekday", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }

}