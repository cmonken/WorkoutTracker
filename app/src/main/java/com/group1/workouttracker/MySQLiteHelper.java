package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/23/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    // Common Table columns
    public static final String COLUMN_EID = "_eid";
    public static final String COLUMN_HID = "_hid";

    // Exercise Table columns
    public static final String COLUMN_EXERCISE = "exercise";
    public static final String COLUMN_WEEKDAY = "weekday";
    public static final String COLUMN_REPETITIONS = "repetitions";
    public static final String COLUMN_NOTES = "notes";

    // History Table columns
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    // Exercises table creation statement
    private static final String CREATE_EXERCISE_TABLE = "create table " + TABLE_EXERCISES + "(" + COLUMN_EID +
            " integer primary key autoincrement, " + COLUMN_EXERCISE + " text not null, " + COLUMN_WEEKDAY +
            " text not null, " + COLUMN_REPETITIONS + " integer, " + COLUMN_NOTES + " text);";

    // Has table creation statement
    private static final String CREATE_HAS_TABLE = "create table " + TABLE_HAS + "(" + COLUMN_EID +
            " integer not null, " + COLUMN_HID + " integer not null);";

    // History table creation statement
    private static final String CREATE_HISTORY_TABLE = "create table " + TABLE_HISTORY + "(" + COLUMN_HID +
            " integer, " + COLUMN_DATE + " text not null, " + COLUMN_TIME +
            " text not null);";

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

}