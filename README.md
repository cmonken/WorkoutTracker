WorkoutTracker
==============

WorkoutTracker project for CSE494
by John Lillyblad and Chris Monken

Project Proposal
Our app will be a Workout Application list that has the capability to update the progress of each task. The app will enable
the user to build a workout routine and know exactly what they are going to do when they enter the gym. The user will be able
to record notes and generate reports based on percentage of completed exercises, allowing the user to save time in the gym
and better analyze performance.

Milestones

Aug 1st:
1. Implement SQL Database to hold workout data entered by user. Both complete and incomplete exercise will be added to the
   database. 

2. Create start screen with the following components:
        * 7 buttons; one for each day of the week
        * ‘Reports’ button
        * Help button with a question mark on it. The user will be able to click this to get instructions to use the app

3. Add user ability to create daily workout list. The user will click on the button corresponding to the day they would like
   to edit. This will open the day and the user can edit the title which will then update the button appearance on the main
   page. Next, exercises can be added. When the user adds an exercise, the toggle ‘Incomplete’/’Complete’ button will
   activate. The user will now be able to record that an exercise is complete. 

4. Create ‘Sets’ drop-down menu that will contain numbers 1-5. This will allow the user to optionally enter the number of
   sets the exercise contains.

5. Implement a ‘Notes’ field that the user can use to enter notes. For example, maybe the user lifted 150 lbs. on the
   benchpress and it was a little too easy. They could put a note “Do 175 lbs. next time” to advise them on the next time
   they perform that exercise.

Aug 12th:
1. Add timestamp functionality that will activate when user changes status (e.g. from incomplete to complete).

2. Add ‘Reports’ functionality that will allow the user to generate reports on percentage complete of exercise for week or
   for month.

3. Create a graph view that will plot percent complete vs. time.

4. Implement custom app icon for the app. 

5. Implement a Java’s Calendar class or something similar so that all dates and timestamps are accurate.
