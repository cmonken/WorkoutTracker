package com.group1.workouttracker;

/**
 * Some code used adapted from SQLite Tutorials located at
 * http://www.vogella.com/tutorials/AndroidSQLite/article.html
 * www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
 * and www.codeofaninja.com/2013/02/android-sqlite-tutorial.html
 */

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OnClickListenerCreateReport implements OnClickListener {

    private float numExercises;
    private int numRemaining;
    private float numCompleted;
    private float percentage;
    private float dontCount;

    @Override
    public void onClick(View view) {

        final Context context = view.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formElementsView = inflater.inflate(R.layout.history_report, null, false);
        numExercises = 0;
        numRemaining = 0;
        numCompleted = 0;
        percentage = 0;
        dontCount = 0;
        String percentStr = "\n";

        final TextView data1 = (TextView)formElementsView.findViewById(R.id.textViewData1);
        final TextView data2 = (TextView)formElementsView.findViewById(R.id.textViewData2);
        final TextView data3 = (TextView)formElementsView.findViewById(R.id.textViewData3);
        final TextView data4 = (TextView)formElementsView.findViewById(R.id.textViewData4);

        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        List<ObjectExercise> allExercises = helper.getAllExercises();
        List<ObjectExercise> completedExercises = helper.getAllCompletedExercises();

        numCompleted = completedExercises.size();
        numExercises = (allExercises).size();

        if (allExercises.size() > 0) {

            for (ObjectExercise obj : allExercises) {
                if (obj.getIsCompleted() == null || obj.getIsCompleted().equals("false")) {
                    numRemaining++;
                    if (getDayOfWeek() > obj.getDay()) {
                    }//if current day is greater than exercise day, exercise is in future and doesn't count
                    else {
                        dontCount++;
                    }
                }
            }
            if(numExercises - dontCount != 0) {
                if(numCompleted == 0) {
                    percentage = 0;
                }
                else {
                    percentage = (numCompleted / (numExercises - dontCount)); //add try catch to prevent possible divide by zero
                }
            }
        }

        data1.setText(Integer.toString((int)numExercises));
        data2.setText(Integer.toString(numRemaining));
        data3.setText(Float.toString((int)numCompleted));
        data4.setText(percentStr + (percentage) * 100 + "%");

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("History Report")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                /*if (createSuccessful) {
                                    Toast.makeText(context, "Report generated.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Unable to generate report.", Toast.LENGTH_SHORT).show();
                                }*/
                                if (percentage * 100 < 25) {
                                    Toast.makeText(context, ":(", Toast.LENGTH_LONG).show();
                                }
                                else if (percentage * 100 < 50) {
                                    Toast.makeText(context, ":|", Toast.LENGTH_LONG).show();
                                }
                                else if (percentage * 100 < 50) {
                                    Toast.makeText(context, ":)", Toast.LENGTH_LONG).show();
                                }
                                else if (percentage * 100 <= 100) {
                                    Toast.makeText(context, ":D" , Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            }
                        }
                ).

                            show();
                        }

    public int getDayOfWeek () { //returns day of week; 1 is Sunday, 2 is Monday...

        Date myDate = new Date();
        myDate.getTime();

        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        c.setTime(myDate);

        //String date = DateFormat.format("MM-dd-yyyy", c).toString();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;

    }

}
