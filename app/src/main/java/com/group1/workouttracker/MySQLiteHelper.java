package com.group1.workouttracker;

/**
 * Modified code adapted from SQLite tutorials
 * from www.vogella.com/tutorials/AndroidSQLite/article.html,
 * www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
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

    // Database name
    private static final String DATABASE_NAME = "workout.db";

    // Database version number
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_DAY_OF_WEEK = "table_day_of_week";
    public static final String TABLE_DAY_HAS_EX = "table_day_exercise";
    public static final String TABLE_EXERCISE = "table_exercise";
    public static final String TABLE_EX_HAS_HIS = "table_exercise_history";
    public static final String TABLE_HISTORY = "table_history";

    // Common column names
    public static final String KEY_ID = "_id";
    public static final String COLUMN_EXERCISE_ID = "exercise_id";
    public static final String COLUMN_EXERCISE = "exerciseName";
    public static final String COLUMN_REPETITIONS = "numReps";
    public static final String COLUMN_NOTES = "notes";

    // Dayofweek table columns
    public static final String COLUMN_WEEKDAY = "dayName";
    public static final String COLUMN_SUMMARY = "summary";

    // Day Has Exercises table columns
    public static final String COLUMN_DAY_ID = "day_id";

    // Exercise Has History table columns
    public static final String COLUMN_HISTORY_ID = "history_id";

    // History table columns
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMPLETED_TIME = "time";


    // Dayofweek table creation statement
    private static final String CREATE_DAYOFWEEK_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DAY_OF_WEEK + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WEEKDAY + " TEXT, " + COLUMN_SUMMARY + " TEXT )";

    // Day Has Exercises  table creation statement
    private static final String CREATE_DAY_HAS_EX_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DAY_HAS_EX + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DAY_ID + " INTEGER, " + COLUMN_EXERCISE_ID  + " INTEGER )";

    // Exercise table creation statement
    private static final String CREATE_EXERCISE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISE + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DAY_ID + " INTEGER, " + COLUMN_EXERCISE + " TEXT, " + COLUMN_REPETITIONS +
            " INTEGER, " + COLUMN_NOTES + " TEXT )";

    // Exercise Has History table creation statement
    private static final String CREATE_EX_HAS_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EX_HAS_HIS + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_ID + " INTEGER, " + COLUMN_HISTORY_ID + " INTEGER )";

    // History table creation statement
    private static final String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE + " TEXT, " + COLUMN_WEEKDAY + " TEXT, " + COLUMN_REPETITIONS +
            " INTEGER, " + COLUMN_NOTES + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_COMPLETED_TIME + " TEXT )";

    private static final String LOG = "";

    private static MySQLiteHelper sInstance;

    public static MySQLiteHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MySQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create required tables
        db.execSQL(CREATE_DAYOFWEEK_TABLE);
        db.execSQL(CREATE_DAY_HAS_EX_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        //db.execSQL(CREATE_EX_HAS_HISTORY_TABLE);
        //db.execSQL(CREATE_HISTORY_TABLE);

        // enter days of the week default entries
        insertWeekday("Monday", db);
        insertWeekday("Tuesday", db);
        insertWeekday("Wednesday", db);
        insertWeekday("Thursday", db);
        insertWeekday("Friday", db);
        insertWeekday("Saturday", db);
        insertWeekday("Sunday", db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data" );

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_OF_WEEK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_HAS_EX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_EX_HAS_HIS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // create new tables
        onCreate(db);
    }

    public boolean insertWeekday(String dayName, SQLiteDatabase db) {

        boolean createSuccessful = false;
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEEKDAY, dayName);
        values.put(COLUMN_SUMMARY, "");

        try {
            db = this.getWritableDatabase();
            synchronized (db) {
                createSuccessful = db.insertWithOnConflict(TABLE_DAY_OF_WEEK, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE) > 0;
            }
        } finally {
            if (db!= null && db.isOpen()) {
                db.close();
            }
        }
        return createSuccessful;
    }

    // created an exercise and assign day(s) to it
    //public long createExercise(ObjectExercise objectExercise) {
    public long createExercise(ObjectExercise objectExercise) {
        SQLiteDatabase database = this.getWritableDatabase();

        long day_id = objectExercise.getDayID();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_ID, day_id);
        values.put(COLUMN_EXERCISE, objectExercise.getExerciseName());
        values.put(COLUMN_REPETITIONS, objectExercise.getNumReps());
        values.put(COLUMN_NOTES, objectExercise.getNotes());

        // insert exercise in table_exercise
        long exercise_id = database.insert(TABLE_EXERCISE, null, values);
        database.close();

        // assign day to exercise
            createDayHasExercise(day_id, exercise_id);

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
                int nReps = Integer.parseInt(c.getString(c.getColumnIndex("numReps")));
                String notes = c.getString(c.getColumnIndex("notes"));

                ObjectExercise objectExercise = new ObjectExercise();
                objectExercise.setId(id);
                objectExercise.setExerciseName(eName);
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
            Integer nReps = cursor.getInt(cursor.getColumnIndex("numReps"));
            String notes = cursor.getString(cursor.getColumnIndex("notes"));

            objectExercise = new ObjectExercise();
            objectExercise.setId(_id);
            objectExercise.setExerciseName(eName);
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
        values.put("numReps", objectExercise.getNumReps());
        values.put("notes", objectExercise.getNotes());

        String where = "_id = ?";

        String[] whereArgs = {Long.toString(objectExercise.getId())};

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

        String[] whereArgs = {"" + objectDay.getId()};

        boolean updateSuccessful = db.update(TABLE_DAY_OF_WEEK, values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }

    public List<ObjectDay> readAllSummaries() {
        List<ObjectDay> summaries = new ArrayList<ObjectDay>();
        String selectQuery = "SELECT * FROM " + TABLE_DAY_OF_WEEK;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
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
    public long createDayHasExercise(long day_id, long exercise_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_ID, day_id);
        values.put(COLUMN_EXERCISE_ID, exercise_id);

        long id = db.insert(TABLE_DAY_HAS_EX, null, values);
        return id;
    }

    public int count() {
        SQLiteDatabase db = null;
        int recordCount = 0;
        try {
            db = this.getWritableDatabase();
            synchronized (db) {
                String sql = "SELECT * FROM table_exercise";
                recordCount = db.rawQuery(sql, null).getCount();
            }
        }
        finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return recordCount;
    }

}