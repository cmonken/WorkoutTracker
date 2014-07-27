package com.group1.workouttracker;

/**
 * Created by chris_000 on 7/26/2014.
 * using code from www.vogella.com/tutorials/AndroidSQLite/article.html
 * and www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * modified to store data required for the WorkoutTracker app
 */

public class DayOfWeek {

        private long id;
        private String weekday;
        private String summary;

        // constructors
        public DayOfWeek ( long id, String weekday, String summary){
            this.id = id;
            this.weekday = weekday;
            this.summary = summary;
        }

        public DayOfWeek (){
        }

        // setters
        public void setId(long id) {
            this.id = id;
        }

        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        // getters
        public long getId() {
            return id;
        }

        public String getWeekday() {
            return weekday;
        }

        public String getSummary() {
            return summary;
        }

        // Will be used by the ArrayAdapter in the ListView
        @Override
        public String toString() {
            return weekday + ": " + summary;
        }
    }