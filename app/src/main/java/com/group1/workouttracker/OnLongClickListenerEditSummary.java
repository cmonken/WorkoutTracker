package com.group1.workouttracker;

/**
 *  Some code used adapted from SQLite Tutorials located at
 *  http://www.vogella.com/tutorials/AndroidSQLite/article.html
 *  www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 *  and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class OnLongClickListenerEditSummary implements View.OnLongClickListener {

    Context context;
    String dayClicked = "";

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();

        final DatabaseHelper db = DatabaseHelper.getInstance(context);
        ObjectDay objectDay = db.readSummary(dayClicked);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.edit_summary_form, null, false);

        final long dayId = objectDay.getId();
        final String dayName = objectDay.getDayName();
        final EditText editTextSummary = (EditText) formElementsView.findViewById(R.id.editTextSummary);

        editTextSummary.setText(objectDay.getSummary());

        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Exercise");
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Summary for " + dayClicked)
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectDay objectDay = new ObjectDay();
                                objectDay.setId(dayId);
                                objectDay.setDayName(dayName);
                                objectDay.setSummary(editTextSummary.getText().toString());

                                boolean updateSuccessful = DatabaseHelper.getInstance(context).updateSummary(objectDay);

                                if(updateSuccessful) {
                                    Toast.makeText(context, "Summary was updated.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Unable to update summary.", Toast.LENGTH_SHORT).show();
                                }

                                ((DayActivity) context).passThroughSummary(objectDay);
                                dialog.dismiss();

                            }
                        }).show();
        return false;
    }

    public void editRecord(final String dayName) {

        final DatabaseHelper db = DatabaseHelper.getInstance(context);
        ObjectDay objectDay = db.readSummary(dayName);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.edit_summary_form, null, false);

        final EditText editTextDay = (EditText) formElementsView.findViewById(R.id.editTextSummary);
        final EditText editTextSummary = (EditText) formElementsView.findViewById(R.id.editTextSummary);

        editTextSummary.setText(objectDay.getSummary());
    }

    public OnLongClickListenerEditSummary(String dayClicked) {
        this.dayClicked = dayClicked;
    }

}