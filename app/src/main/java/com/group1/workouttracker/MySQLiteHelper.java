package com.group1.workouttracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chris_000 on 7/23/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * modified to store data required for the WorkoutTracker app
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EID = "_eid";
    public static final String COLUMN_EXERCISE = "exercise";
    public static final String COLUMN_WEEKDAY = "weekday";
    public static final String COLUMN_REPETITIONS = "repetitions";
    public static final String COLUMN_NOTES = "notes";

    public static final String TABLE_COMPLETED = "completed";

    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_HID = "_hid";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";

    private static final String DATABASE_NAME = "exercises.db";
    private static final int DATABASE_VERSION = 1;

    // Database exercises creation sql statement
    private static final String DATABASE_CREATE1 = "create table " + TABLE_EXERCISES + "(" + COLUMN_EID +
            " integer primary key autoincrement, " + COLUMN_EXERCISE + " text not null, " + COLUMN_WEEKDAY +
            " text not null, " + COLUMN_REPETITIONS + " integer, " + COLUMN_NOTES + " text);";

    // Database completed table creation sql statement
    private static final String DATABASE_CREATE2 = "create table " + TABLE_COMPLETED + "(" + COLUMN_EID +
            " integer not null, " + COLUMN_HID + " integer not null);";

    // Database history table creation sql statement
    private static final String DATABASE_CREATE3 = "create table " + TABLE_HISTORY + "(" + COLUMN_HID +
            " integer primary key autoincrement, " + COLUMN_DATE + " text not null, " + COLUMN_TIME +
            " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE1);
        database.execSQL(DATABASE_CREATE2);
        database.execSQL(DATABASE_CREATE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

}