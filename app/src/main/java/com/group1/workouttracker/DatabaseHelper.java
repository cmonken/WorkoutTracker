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

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "";
    private static DatabaseHelper sInstance;
    private static SQLiteDatabase db = null;
    private static Context context;

    // Database name
    private static final String DATABASE_NAME = "workout.db";

    // Database version number
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_DAY_OF_WEEK = "table_day_of_week";
    public static final String TABLE_EXERCISE = "table_exercise";
    public static final String TABLE_EX_HAS_HIS = "table_exercise_history";
    public static final String TABLE_HISTORY = "table_history";

    // Common column names
    public static final String KEY_ID = "_id";
    public static final String COLUMN_EXERCISE = "exerciseName";
    public static final String COLUMN_WEEKDAY = "dayName";
    public static final String COLUMN_SETS = "numSets";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_COMPLETED = "isCompleted";

    // Dayofweek table columns
    public static final String COLUMN_SUMMARY = "summary";

    // Exercise Has History table columns
    public static final String COLUMN_EXERCISE_ID = "exercise_id";
    public static final String COLUMN_HISTORY_ID = "history_id";

    // History table columns
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMPLETED_TIME = "time";


    // Dayofweek table creation statement
    private static final String CREATE_DAYOFWEEK_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DAY_OF_WEEK + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WEEKDAY + " TEXT UNIQUE, " + COLUMN_SUMMARY + " TEXT )";

    // Exercise table creation statement
    private static final String CREATE_EXERCISE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXERCISE + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE + " TEXT, " + COLUMN_WEEKDAY + " TEXT, " + COLUMN_SETS + " INTEGER, "
            + COLUMN_NOTES + " TEXT, " + COLUMN_COMPLETED + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_COMPLETED_TIME + " TEXT )";

    // Exercise Has History table creation statement
    private static final String CREATE_EX_HAS_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EX_HAS_HIS + "( " + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXERCISE_ID + " INTEGER, " + COLUMN_HISTORY_ID + " INTEGER )";

    // History table creation statement
    private static final String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HISTORY + "( " + KEY_ID +
            " INTEGER, " + COLUMN_EXERCISE + " TEXT, " + COLUMN_WEEKDAY + " TEXT, " + COLUMN_SETS +
            " INTEGER, " + COLUMN_NOTES + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_COMPLETED_TIME + " TEXT )";
    //KEY_ID for history table tied to exercise table ID, so no auto-increment

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        DatabaseHelper.context = context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
            db = sInstance.getWritableDatabase();
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create required tables
        db.execSQL(CREATE_DAYOFWEEK_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
        //db.execSQL(CREATE_EX_HAS_HISTORY_TABLE);

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
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data" );

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_OF_WEEK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EX_HAS_HIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // create new tables
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        if (sInstance != null)
            db.close();
    }

    public boolean insertWeekday(String dayName, SQLiteDatabase db) {

        //getWritableDatabase();
        boolean createSuccessful = false;
        ContentValues values = new ContentValues();
        values.put(COLUMN_WEEKDAY, dayName);
        values.put(COLUMN_SUMMARY, "");

        createSuccessful = db.insertWithOnConflict(TABLE_DAY_OF_WEEK, null, values,
                SQLiteDatabase.CONFLICT_IGNORE) > 0;
        return createSuccessful;
    }

    /**********************************************************************************************/
    /****************************** Begin MyDatabaseHandler Code **********************************/

    // created an exercise and assign day(s) to it
    public boolean createExercise(ObjectExercise objectExercise) {
        getWritableDatabase();
        boolean success = false;

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE, objectExercise.getExerciseName());
        values.put(COLUMN_WEEKDAY, objectExercise.getDayName());
        values.put(COLUMN_SETS, objectExercise.getNumSets());
        values.put(COLUMN_NOTES, objectExercise.getNotes());

        // insert exercise in table_exercise
        int exercise_id = (int)(db.insert(TABLE_EXERCISE, null, values));
        if(exercise_id > 0)
            success = true;

        return success;
    }

    // created an exercise and assign day(s) to it
    public boolean createCompletedRecord(ObjectExercise objectExercise, int id) {
        getWritableDatabase();
        boolean success = false;

        ContentValues values = new ContentValues();
        values.put(COLUMN_EXERCISE, objectExercise.getExerciseName());
        values.put(COLUMN_WEEKDAY, objectExercise.getDayName());
        values.put(COLUMN_SETS, objectExercise.getNumSets());
        values.put(COLUMN_NOTES, objectExercise.getNotes());
        values.put(COLUMN_DATE, objectExercise.getDate());
        values.put(KEY_ID, id); //set record to original exercise id

        // insert exercise in table_exercise
        int exercise_id = (int)(db.insert(TABLE_HISTORY, null, values));
        if(exercise_id > 0)
            success = true;

        return success;
    }

    public List<ObjectExercise> getAllExercisesByDay(String dName) {
        getReadableDatabase();
        List<ObjectExercise> exerciseList = new ArrayList<ObjectExercise>();

        String sql = "SELECT * FROM " + TABLE_EXERCISE + " WHERE " + COLUMN_WEEKDAY + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { dName });

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
                String eName = cursor.getString(cursor.getColumnIndex("exerciseName"));
                String dayName = cursor.getString(cursor.getColumnIndex("dayName"));
                int numSets = Integer.parseInt(cursor.getString(cursor.getColumnIndex("numSets")));
                String notes = cursor.getString(cursor.getColumnIndex("notes"));
                String isCompleted = cursor.getString(cursor.getColumnIndex("isCompleted"));

                ObjectExercise objectExercise = new ObjectExercise();
                objectExercise.setId(id);
                objectExercise.setExerciseName(eName);
                objectExercise.setDayName(dayName);
                objectExercise.setNumSets(numSets);
                objectExercise.setNotes(notes);
                objectExercise.setIsCompleted(isCompleted);

                exerciseList.add(objectExercise);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return exerciseList;
    }

    public List<ObjectExercise> getAllCompletedExercises() {
        getReadableDatabase();
        List<ObjectExercise> exerciseList = new ArrayList<ObjectExercise>();

        String sql = "SELECT * FROM " + TABLE_HISTORY; //get all records in table
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));
                String eName = cursor.getString(cursor.getColumnIndex("exerciseName"));
                String dayName = cursor.getString(cursor.getColumnIndex("dayName"));
                int numSets = Integer.parseInt(cursor.getString(cursor.getColumnIndex("numSets")));
                String notes = cursor.getString(cursor.getColumnIndex("notes"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                ObjectExercise objectExercise = new ObjectExercise();
                objectExercise.setId(id);
                objectExercise.setExerciseName(eName);
                objectExercise.setDayName(dayName);
                objectExercise.setNumSets(numSets);
                objectExercise.setNotes(notes);
                objectExercise.setDate(date);

                exerciseList.add(objectExercise);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return exerciseList;
    }

    public ObjectExercise readSingleExercise(int id) {
        getReadableDatabase();
        ObjectExercise objectExercise = null;
        String sql = "SELECT * FROM table_exercise WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            int _id = (Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
            String eName = cursor.getString(cursor.getColumnIndex("exerciseName"));
            String dayName = cursor.getString(cursor.getColumnIndex("dayName"));
            Integer numSets = cursor.getInt(cursor.getColumnIndex("numSets"));
            String notes = cursor.getString(cursor.getColumnIndex("notes"));
            String isCompleted = cursor.getString(cursor.getColumnIndex("isCompleted"));

            objectExercise = new ObjectExercise();
            objectExercise.setId(_id);
            objectExercise.setExerciseName(eName);
            objectExercise.setDayName(dayName);
            objectExercise.setNumSets(numSets);
            objectExercise.setNotes(notes);
            objectExercise.setIsCompleted(isCompleted);
        }
        cursor.close();
        return objectExercise;
    }

    public boolean updateExercise(ObjectExercise objectExercise) {

        ContentValues values = new ContentValues();

        values.put("exerciseName", objectExercise.getExerciseName());
        values.put("dayName", objectExercise.getDayName());
        values.put("numSets", objectExercise.getNumSets());
        values.put("notes", objectExercise.getNotes());
        values.put("isCompleted", objectExercise.getIsCompleted());

        String where = "_id = ?";

        String[] whereArgs = {Integer.toString(objectExercise.getId())};

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("table_exercise", values, where, whereArgs) > 0;

        return updateSuccessful;
    }

    public boolean deleteExercise(String id) {
        getWritableDatabase();
        boolean deleteSuccessful = db.delete("table_exercise", "_id = " + id, null) > 0;
        return deleteSuccessful;
    }

    public boolean deleteCompletedRecord(String id) {
        getWritableDatabase();
        boolean deleteSuccessful = db.delete("table_history", "_id = " + id, null) > 0;
        return deleteSuccessful;
    }

    public boolean updateHistory(ObjectExercise objectExercise) {
        getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("exerciseName", objectExercise.getExerciseName());
        values.put("dayName", objectExercise.getDayName());
        values.put("numSets", objectExercise.getNumSets());
        values.put("notes", objectExercise.getNotes());
        values.put("isCompleted", objectExercise.getIsCompleted());

        String where = "_id = ?";
        String[] whereArgs = {Integer.toString(objectExercise.getId())};
        boolean updateSuccessful = db.update("table_exercise", values, where, whereArgs) > 0;
        return updateSuccessful;
    }

    public boolean updateCompleted(ObjectExercise objectExercise) {
        getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isCompleted", objectExercise.getIsCompleted());
        values.put("date", objectExercise.getDate());
        String where = "_id = ?";
        String[] whereArgs = {Integer.toString(objectExercise.getId())};

        boolean updateSuccessful = db.update("table_exercise", values, where, whereArgs) > 0;
        return updateSuccessful;
    }

    // get the summary for a specific day
    public ObjectDay readSummary(String dName) {
        getReadableDatabase();

        ObjectDay objectDay = new ObjectDay();
        objectDay.setDayName(dName);

        String sql = "SELECT * FROM " + TABLE_DAY_OF_WEEK + " WHERE " + COLUMN_WEEKDAY + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { dName });

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)));
            String summary = cursor.getString(cursor.getColumnIndex(COLUMN_SUMMARY));

            objectDay.setId(id);
            objectDay.setSummary(summary);
        }

        cursor.close();
        return objectDay;
    }

    // update the summary field for a weekday
    public boolean updateSummary(ObjectDay objectDay) {
        getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, objectDay.getId());
        values.put(COLUMN_WEEKDAY, objectDay.getDayName());
        values.put(COLUMN_SUMMARY, objectDay.getSummary());

        String where = KEY_ID + " = ?";
        String[] whereArgs = {"" + objectDay.getId()};
        boolean updateSuccessful = db.update(TABLE_DAY_OF_WEEK, values, where, whereArgs) > 0;
        return updateSuccessful;
    }

    public List<ObjectDay> readAllSummaries() {
        getReadableDatabase();
        List<ObjectDay> summaries = new ArrayList<ObjectDay>();
        String selectQuery = "SELECT * FROM " + TABLE_DAY_OF_WEEK;
        Log.e(LOG, selectQuery);
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
/*    public int createDayHasExercise(int day_id, int exercise_id) {
        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_ID, day_id);
        values.put(COLUMN_EXERCISE_ID, exercise_id);

        int id = db.insert(TABLE_DAY_HAS_EX, null, values);
        return id;
    } */

    /*
    public int count() {
        //SQLiteDatabase db = null;
        int recordCount = 0;
        try {
            getWritableDatabase();
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
    }*/

    public int count() {
        int recordCount = 0;
        getWritableDatabase();
        String sql = "SELECT * FROM table_exercise";
        recordCount = db.rawQuery(sql, null).getCount();

        return recordCount;
    }

    /**********************************************************************************************/
    /******************************** End MyDatabaseHandler Code **********************************/

}