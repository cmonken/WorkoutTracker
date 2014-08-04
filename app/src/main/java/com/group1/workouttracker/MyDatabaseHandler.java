package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDatabaseHandler extends MySQLiteHelper {

    private static final String LOG = "";

    public MyDatabaseHandler(Context context) {
        super(context);
    }


    // created an exercise and assign day(s) to it
    public long createExercise(ObjectExercise objectExercise) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE, objectExercise.getExerciseName());
        values.put(COLUMN_WEEKDAY, objectExercise.getDayName());
        values.put(COLUMN_REPETITIONS, objectExercise.getNumReps());
        values.put(COLUMN_NOTES, objectExercise.getNotes());

        // insert exercise in table_exercise
        long exercise_id = database.insert(TABLE_EXERCISE, null, values);
        database.close();

        // assign day to exercise
/*        for(long day_id : day_ids){
            createDayHasExercise(day_id, exercise_id);
        } */

        return exercise_id;
    }

    public List<ObjectExercise> getAllExercisesByDay(String dayName) {

        List<ObjectExercise> exerciseList = new ArrayList<ObjectExercise>();

        String sql = "SELECT * FROM table_exercise WHERE dayName = " + dayName + " ORDER BY _id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {

                long id = Integer.parseInt(c.getString(c.getColumnIndex("_id")));
                String eName = c.getString(c.getColumnIndex("exerciseName"));
                String dName = c.getString(c.getColumnIndex("dayName"));
                int nReps = Integer.parseInt(c.getString(c.getColumnIndex("numReps")));
                String notes = c.getString(c.getColumnIndex("notes"));

                ObjectExercise objectExercise = new ObjectExercise();
                objectExercise.setId(id);
                objectExercise.setExerciseName(eName);
                objectExercise.setDayName(dName);
                objectExercise.setNumReps(nReps);
                objectExercise.setNotes(notes);

                exerciseList.add(objectExercise);

            } while (c.moveToNext());
        }

        c.close();
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

    // get the summary for a specific day
    public ObjectDay readSummary(String dName) {
        SQLiteDatabase db = this.getReadableDatabase();

        ObjectDay objectDay = new ObjectDay();
        objectDay.setDayName(dName);

        String sql = "SELECT * FROM " + TABLE_DAY_OF_WEEK + " WHERE " + COLUMN_WEEKDAY + " = " + dName;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)));
            String summary = cursor.getString(cursor.getColumnIndex(COLUMN_SUMMARY));

            objectDay.setId(id);
            objectDay.setSummary(summary);
        }

        cursor.close();
        db.close();

        return objectDay;
    }

    // update the summary field for a weekday
    public boolean updateSummary(ObjectDay objectDay) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, objectDay.getId());
        values.put(COLUMN_WEEKDAY, objectDay.getDayName());
        values.put(COLUMN_SUMMARY, objectDay.getSummary());

        String where = KEY_ID + " = ?";

        String[] whereArgs = { "" + objectDay.getId() };

        boolean updateSuccessful = db.update(TABLE_DAY_OF_WEEK, values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }

    public List<ObjectDay> readAllSummaries(){
        List<ObjectDay> summaries = new ArrayList<ObjectDay>();
        String selectQuery = "SELECT * FROM " + TABLE_DAY_OF_WEEK;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()){
            do {
                ObjectDay day = new ObjectDay();
                day.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                day.setDayName(c.getString(c.getColumnIndex(COLUMN_WEEKDAY)));
                day.setSummary(c.getString(c.getColumnIndex(COLUMN_SUMMARY)));

                // adding to summary list
                summaries.add(day);
            } while (c.moveToNext());
        }
        return summaries;
    }

    // create relationship between days and exercises
    public long createDayHasExercise(long day_id, long exercise_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_ID, day_id);
        values.put(COLUMN_EXERCISE_ID, exercise_id);

        long id = db.insert(TABLE_DAY_HAS_EX, null, values);
        return id;
    }
}
