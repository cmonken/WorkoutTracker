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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class OnLongClickListenerEditExercise implements View.OnLongClickListener {

    Context context;
    String id;
    private String dayClicked;

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        final DatabaseHelper helper = DatabaseHelper.getInstance(context);
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Exercise")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        } else if (item == 1) {
                            //retrieve exercise to check if it has been completed before deletion
                            ObjectExercise obj = helper.readSingleExercise(Integer.parseInt(id));

                            boolean deleteSuccessful1 = helper.deleteExercise(id);
                            boolean deleteSuccessful2;
                            if(obj.getIsCompleted().equals(null) || obj.getIsCompleted().equals("false")) {
                                deleteSuccessful2 = true;
                            } //not completed, do nothing
                            else {
                                deleteSuccessful2 = helper.deleteExercise(id);
                            }

                            if (deleteSuccessful1 && deleteSuccessful2) {
                                Toast.makeText(context, "Exercise was deleted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Unable to delete exercise.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        ((DayActivity) context).refreshView(dayClicked);
                        dialog.dismiss();

                    }
                }).show();

        return false;
    }

    public void editRecord(final int exerciseId) {

        final DatabaseHelper db = DatabaseHelper.getInstance(context);
        ObjectExercise objectExercise = db.readSingleExercise(exerciseId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.exercise_input_form, null, false);

        final EditText editTextExerciseName = (EditText) formElementsView.findViewById(R.id.editTextExerciseName);
        final NumberPicker editRepetitions = (NumberPicker) formElementsView.findViewById(R.id.npNumReps);
        final EditText editTextNotes = (EditText) formElementsView.findViewById(R.id.editTextNotes);

        //NumberPicker setup
        String[] values = new String[5];
        for(int i = 0; i < values.length; i++){
            values[i] = Integer.toString(i + 1);
        }

        editTextExerciseName.setText(objectExercise.getExerciseName());
        editRepetitions.setValue(objectExercise.getNumSets());
        editTextNotes.setText(objectExercise.getNotes());

        editRepetitions.setMinValue(1);
        editRepetitions.setMaxValue(5);
        editRepetitions.setDisplayedValues(values);
        editRepetitions.setValue(3);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Exercise")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectExercise objectExercise = new ObjectExercise();
                                objectExercise.setId(exerciseId);
                                objectExercise.setDayName(dayClicked);
                                objectExercise.setExerciseName(editTextExerciseName.getText().toString());
                                objectExercise.setNumSets(editRepetitions.getValue());
                                objectExercise.setNotes(editTextNotes.getText().toString());

                                boolean updateSuccessful = DatabaseHelper.getInstance(context).updateExercise(objectExercise);

                                if (updateSuccessful) {
                                    Toast.makeText(context, "Exercise was updated.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Unable to update exercise.", Toast.LENGTH_SHORT).show();
                                }

                                ((DayActivity) context).refreshView(dayClicked);
                                dialog.dismiss();
                            }

                        }
                ).show();
    }

    public OnLongClickListenerEditExercise(String dayClicked) {
        this.dayClicked = dayClicked;
    }

}