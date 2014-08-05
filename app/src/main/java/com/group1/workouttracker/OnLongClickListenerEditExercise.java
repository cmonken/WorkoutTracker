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

    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Exercise")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }

                        else if (item == 1) {

                            boolean deleteSuccessful = DatabaseHelper.getInstance(context).deleteExercise(id);

                            if (deleteSuccessful){
                                Toast.makeText(context, "Exercise was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete exercise.", Toast.LENGTH_SHORT).show();
                            }

                            // ((MainActivity) context).countRecords();
                            // ((MainActivity) context).readRecords();

                        }

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
        final NumberPicker numberPickerNumReps = (NumberPicker) formElementsView.findViewById(R.id.npNumReps);
        final EditText editTextNotes = (EditText) formElementsView.findViewById(R.id.editTextNotes);

        editTextExerciseName.setText(objectExercise.getExerciseName());
        numberPickerNumReps.setValue((int) objectExercise.getNumReps());
        editTextNotes.setText(objectExercise.getNotes());

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Exercise")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectExercise objectExercise = new ObjectExercise();
                                objectExercise.setId(exerciseId);
                                objectExercise.setExerciseName(editTextExerciseName.getText().toString());
                                objectExercise.setNumReps(numberPickerNumReps.getValue());
                                objectExercise.setNotes(editTextNotes.getText().toString());

                                boolean updateSuccessful = DatabaseHelper.getInstance(context).updateExercise(objectExercise);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Exercise was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update exercise.", Toast.LENGTH_SHORT).show();
                                }

                                // ((MainActivity) context).countRecords();
                                // ((MainActivity) context).readRecords();

                                dialog.cancel();
                            }

                        }).show();
    }

}