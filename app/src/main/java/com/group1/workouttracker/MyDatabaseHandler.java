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

    public boolean createExercise(ObjectExercise objectExercise) {

        ContentValues values = new ContentValues();

        values.put("exercisename", objectExercise.getExerciseName());
        values.put("dayname", objectExercise.getDayName());
        values.put("numreps", objectExercise.getNumReps());
        values.put("notes", objectExercise.getNotes());

        SQLiteDatabase database = this.getWritableDatabase();

        boolean createSuccessful = database.insert("table_exercise", null, values) > 0;
        database.close();

        return createSuccessful;
    }

    public List<ObjectExercise> readExercise() {

        List<ObjectExercise> exerciseList = new ArrayList<ObjectExercise>();

        String sql = "SELECT * FROM table_exercises ORDER BY _id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                long id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
                String eName = cursor.getString(cursor.getColumnIndex("exerciseName"));
                String dName = cursor.getString(cursor.getColumnIndex("dayName"));
                int nReps = Integer.parseInt(cursor.getString(cursor.getColumnIndex("numReps")));
                String notes = cursor.getString(cursor.getColumnIndex("notes"));

                ObjectExercise objectExercise = new ObjectExercise();
                objectExercise.setId(id);
                objectExercise.setExerciseName(eName);
                objectExercise.setDayName(dName);
                objectExercise.setNumReps(nReps);
                objectExercise.setNotes(notes);

                exerciseList.add(objectExercise);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return exerciseList;
    }

    public ObjectExercise readSingleExercise(long id) {

        ObjectExercise objectExercise = null;

        String sql = "SELECT * FROM table_exercise WHERE _id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            long _id = ((long) Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
            String eName = cursor.getString(cursor.getColumnIndex("exerciseName"));
            String dName = cursor.getString(cursor.getColumnIndex("dayName"));
            Integer nReps = cursor.getInt(cursor.getColumnIndex("numReps"));
            String notes = cursor.getString(cursor.getColumnIndex("notes"));

            objectExercise = new ObjectExercise();
            objectExercise.setId(_id);
            objectExercise.setExerciseName(eName);
            objectExercise.setDayName(dName);
            objectExercise.setNumReps(nReps);
            objectExercise.setNotes(notes);
        }

        cursor.close();
        db.close();

        return objectExercise;

    }

    public boolean updateExercise(ObjectExercise objectExercise) {

        ContentValues values = new ContentValues();

        values.put("exerciseName", objectExercise.getExerciseName());
        values.put("dayName", objectExercise.getDayName());
        values.put("numReps", objectExercise.getNumReps());
        values.put("notes", objectExercise.getNotes());

        String where = "_id = ?";

        String[] whereArgs = { Long.toString(objectExercise.getId()) };

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

    public int exerciseCount() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM table_exercise";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;
    }

    public ObjectDay readSummary(String dName) {

        ObjectDay objectDay = null;

        String sql = "SELECT * FROM table_dayofweek WHERE dayName = " + dName;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
            String summary = cursor.getString(cursor.getColumnIndex("summary"));

            objectDay = new ObjectDay();
            objectDay.setId(id);
            objectDay.setDayName(dName);
            objectDay.setSummary(summary);
        }

        cursor.close();
        db.close();

        return objectDay;
    }

    public boolean updateSummary(ObjectDay objectDay) {

        ContentValues values = new ContentValues();

        values.put("_id", objectDay.getId());
        values.put("dayName", objectDay.getDayName());
        values.put("summary", objectDay.getSummary());

        String where = "_id = ?";

        String[] whereArgs = { Integer.toString(objectDay.getId()) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("table_weekday", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }
}