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
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE + " TEXT, " + COLUMN_REPETITIONS + " INTEGER, " + COLUMN_NOTES + " TEXT )";

    // Exercise Has History table creation statement
    private static final String CREATE_EX_HAS_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EX_HAS_HIS + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_ID + " INTEGER, " + COLUMN_HISTORY_ID + " INTEGER )";

    // History table creation statement
    private static final String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE + " TEXT, " + COLUMN_WEEKDAY + " TEXT, " + COLUMN_REPETITIONS +
            " INTEGER, " + COLUMN_NOTES + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_COMPLETED_TIME + " TEXT )";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create required tables
        db.execSQL(CREATE_DAYOFWEEK_TABLE);
        //db.execSQL(CREATE_DAY_HAS_EX_TABLE);
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
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_HAS_EX);
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

}