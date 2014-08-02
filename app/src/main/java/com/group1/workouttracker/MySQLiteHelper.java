package com.group1.workouttracker;

/**
 * Modified code adapted from SQLite tutorials
 * from www.vogella.com/tutorials/AndroidSQLite/article.html
 * www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "workout.db";

    // Database version number
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_DAYOFWEEK = "table_dayofweek";
    public static final String TABLE_EXERCISES = "table_exercises";
    public static final String TABLE_HAS = "table_has";
    public static final String TABLE_HISTORY = "table_history";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String COLUMN_EXERCISE = "exercise";
    public static final String COLUMN_WEEKDAY = "weekday";
    public static final String COLUMN_REPETITIONS = "repetitions";
    public static final String COLUMN_NOTES = "notes";

    // Dayofweek table columns
    public static final String COLUMN_SUMMARY = "summary";

    // Has table columns
    public static final String COLUMN_HISTORY_ID = "history_id";
    public static final String COLUMN_EXERCISE_ID = "exercise_id";


    // History table columns
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMPLETED_TIME = "time";


    // Dayofweek table creation statement
    private static final String CREATE_DAYOFWEEK_TABLE = "create table if not exists " + TABLE_DAYOFWEEK + "(" + KEY_ID +
            " integer primary key, " + COLUMN_WEEKDAY + " text, " + COLUMN_SUMMARY + " text);";

    // Exercises table creation statement
    private static final String CREATE_EXERCISE_TABLE = "create table if not exists " + TABLE_EXERCISES + "(" + KEY_ID +
            " integer primary key, " + COLUMN_EXERCISE + " text, " + COLUMN_WEEKDAY + " text, " + COLUMN_REPETITIONS +
            " integer, " + COLUMN_NOTES + " text);";

    // Has table creation statement
    private static final String CREATE_HAS_TABLE = "create table if not exists " + TABLE_HAS + "(" + KEY_ID +
            " integer primary key, " + COLUMN_EXERCISE_ID + " integer, " + COLUMN_HISTORY_ID + " integer);";

    // History table creation statement
    private static final String CREATE_HISTORY_TABLE = "create table if not exists " + TABLE_HISTORY + "(" + KEY_ID +
            " integer primary key, " + COLUMN_EXERCISE + " text, " + COLUMN_WEEKDAY + " text, " + COLUMN_REPETITIONS +
            " integer, " + COLUMN_NOTES + " text, " + COLUMN_DATE + " date, " + COLUMN_COMPLETED_TIME + " time);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create required tables
        database.execSQL(CREATE_DAYOFWEEK_TABLE);
        // enter days of the week default entries
        Log.e("Monday", "Creating Monday: " + insertWeekday("Monday"));
        Log.e("Tuesday", "Creating Tuesday: " + insertWeekday("Tuesday"));
        Log.e("Wednesday", "Creating Wednesday: " + insertWeekday("Wednesday"));
        Log.e("Thursday", "Creating Thursday: " + insertWeekday("Thursday"));
        Log.e("Friday", "Creating Friday: " + insertWeekday("Friday"));
        Log.e("Saturday", "Creating Saturday: " + insertWeekday("Saturday"));
        Log.e("Sunday", "Creating Sunday: " + insertWeekday("Sunday"));
        database.execSQL(CREATE_EXERCISE_TABLE);
        database.execSQL(CREATE_HAS_TABLE);
        database.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion +
            ", which will destroy all old data" );

        // on upgrade drop older tables
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYOFWEEK);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HAS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // create new tables
        onCreate(database);
    }

    public boolean insertWeekday(String weekday) {

        ContentValues values = new ContentValues();

        values.put("weekday", weekday);

        SQLiteDatabase database = this.getWritableDatabase();

        boolean createSuccessful = database.insert("table_dayofweek", null, values) > 0;
        database.close();

        return createSuccessful;
    }

}