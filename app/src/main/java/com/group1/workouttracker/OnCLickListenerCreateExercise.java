package com.group1.workouttracker;

/**
 * Some code used adapted from SQLite Tutorial located at
 * http://www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class OnCLickListenerCreateExercise implements OnClickListener {

    @Override
    public void onClick(View view) {

        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formElementsView = inflater.inflate(R.layout.add_exercise_form, null, false);

        final EditText editExerciseName = (EditText) formElementsView.findViewById(R.id.editTextExerciseName);
        final NumberPicker editRepetitions = (NumberPicker) formElementsView.findViewById(R.id.npNoReps);
        final EditText editNotes = (EditText) formElementsView.findViewById(R.id.editTextNotes);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create Exercise")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String exerciseName = editExerciseName.getText().toString();
                                String weekDay = "weekday placeholder";
                                Long numReps = ((long) editRepetitions.getValue());
                                String notes = editNotes.getText().toString();

                                Exercise exercise = new Exercise();
                                exercise.setExercise(exerciseName);
                                exercise.setWeekday(weekDay);
                                exercise.setRepetitions(numReps);
                                exercise.setNotes(notes);

/*                                boolean createSuccessful = new TableControllerStudent(context).create(exercise);

                                if(createSuccessful){
                                    Toast.makeText(context, "Exercise was saved.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to save exercise.", Toast.LENGTH_SHORT).show();
                                } */

//                                ((MainActivity) context).countRecords();
//                                ((MainActivity) context).readRecords();

                                dialog.cancel();
                            }

                        }).show();

    }

}