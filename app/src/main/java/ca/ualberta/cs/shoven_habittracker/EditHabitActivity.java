/*
Except as noted, this content is licensed under Creative Commons Attribution 2.5. For details and restrictions, see the Content License.

Habit Tracker: Android app to track daily habits.
Copyright (C) 2016  Sarah Hoven shoven@ualberta.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.


COPYRIGHT AND PERMISSION NOTICE

Copyright (c) 1995-2015 International Business Machines Corporation and others

All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, provided that the above copyright notice(s) and this permission notice appear in all copies of the Software and that both the above copyright notice(s) and this permission notice appear in supporting documentation.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF THIRD PARTY RIGHTS. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR HOLDERS INCLUDED IN THIS NOTICE BE LIABLE FOR ANY CLAIM, OR ANY SPECIAL INDIRECT OR CONSEQUENTIAL DAMAGES, OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

Except as contained in this notice, the name of a copyright holder shall not be used in advertising or otherwise to promote the sale, use or other dealings in this Software without prior written authorization of the copyright holder.
*/

package ca.ualberta.cs.shoven_habittracker;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Collection;
import java.util.Date;

public class EditHabitActivity extends AppCompatActivity {
    private Integer position;
    private String activity;
    private WeeklyScheduleController controller = new WeeklyScheduleController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        Schedule scheduleToSet = new Schedule();
        final Integer sun = 0, mon = 1, tue = 2, wed = 3, thu = 4, fri = 5, sat = 6;

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        activity = bundle.getString("activity");
        Habit habitToSet;
        habitToSet = controller.getAllHabits().getHabitList().get(position);

        final Habit habit = habitToSet;

        final Schedule schedule = initializeFields(controller, habit, scheduleToSet);

        FloatingActionButton editFab = (FloatingActionButton) findViewById(R.id.saveEditFab);
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Habit update saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                saveHabitChanges(view, habit, schedule);
                finish();
            }
        });

        ToggleButton sundayToggle = (ToggleButton) findViewById(R.id.editSundayToggleButton);
        sundayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule.addToSchedule(sun);
                } else {
                    // The toggle is disabled
                    schedule.removeFromSchedule(sun);
                }
            }
        });

        ToggleButton mondayToggle = (ToggleButton) findViewById(R.id.editMondayToggleButton);
        mondayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule.addToSchedule(mon);
                } else {
                    // The toggle is disabled
                    schedule.removeFromSchedule(mon);
                }
            }
        });

        ToggleButton tuesdayToggle = (ToggleButton) findViewById(R.id.editTuesdayToggleButton);
        tuesdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule.addToSchedule(tue);
                } else {
                    // The toggle is disabled
                    schedule.removeFromSchedule(tue);
                }
            }
        });

        ToggleButton wednesdayToggle = (ToggleButton) findViewById(R.id.editWednesdayToggleButton);
        wednesdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule.addToSchedule(wed);
                } else {
                    // The toggle is disabled
                    schedule.removeFromSchedule(wed);
                }
            }
        });

        ToggleButton thursdayToggle = (ToggleButton) findViewById(R.id.editThursdayToggleButton);
        thursdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule.addToSchedule(thu);
                } else {
                    // The toggle is disabled
                    schedule.removeFromSchedule(thu);
                }
            }
        });

        ToggleButton fridayToggle = (ToggleButton) findViewById(R.id.editFridayToggleButton);
        fridayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule.addToSchedule(fri);
                } else {
                    // The toggle is disabled
                    schedule.removeFromSchedule(fri);
                }
            }
        });

        ToggleButton saturdayToggle = (ToggleButton) findViewById(R.id.editSaturdayToggleButton);
        saturdayToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    schedule.addToSchedule(sat);
                } else {
                    // The toggle is disabled
                    schedule.removeFromSchedule(sat);
                }
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public Schedule initializeFields(WeeklyScheduleController controller, Habit habit, Schedule schedule) {
        setTitle("Edit Habit");

        EditText habitNameEditText = (EditText) findViewById(R.id.editHabitNameEditText);
        EditText habitCommentEditText = (EditText) findViewById(R.id.editCommentEditText);
        habitNameEditText.setText(habit.getName());
        habitCommentEditText.setText(habit.getComment());

        Collection<Integer> habitSchedule = controller.getHabitSchedule(habit).getSchedule();
        ToggleButton sundayToggle = (ToggleButton) findViewById(R.id.editSundayToggleButton);
        ToggleButton mondayToggle = (ToggleButton) findViewById(R.id.editMondayToggleButton);
        ToggleButton tuesdayToggle = (ToggleButton) findViewById(R.id.editTuesdayToggleButton);
        ToggleButton wednesdayToggle = (ToggleButton) findViewById(R.id.editWednesdayToggleButton);
        ToggleButton thursdayToggle = (ToggleButton) findViewById(R.id.editThursdayToggleButton);
        ToggleButton fridayToggle = (ToggleButton) findViewById(R.id.editFridayToggleButton);
        ToggleButton saturdayToggle = (ToggleButton) findViewById(R.id.editSaturdayToggleButton);
        if (habitSchedule.contains(0)) {
            sundayToggle.setChecked(true);
            schedule.addToSchedule(0);
        }
        if (habitSchedule.contains(1)) {
            mondayToggle.setChecked(true);
            schedule.addToSchedule(1);
        }
        if (habitSchedule.contains(2)) {
            tuesdayToggle.setChecked(true);
            schedule.addToSchedule(2);
        }
        if (habitSchedule.contains(3)) {
            wednesdayToggle.setChecked(true);
            schedule.addToSchedule(3);
        }
        if (habitSchedule.contains(4)) {
            thursdayToggle.setChecked(true);
            schedule.addToSchedule(4);
        }
        if (habitSchedule.contains(5)) {
            fridayToggle.setChecked(true);
            schedule.addToSchedule(5);
        }
        if (habitSchedule.contains(6)) {
            saturdayToggle.setChecked(true);
            schedule.addToSchedule(6);
        }
        return schedule;
    }

    public void saveHabitChanges (View v, Habit habit, Schedule schedule) {
        EditText newHabitName = (EditText) findViewById(R.id.editHabitNameEditText);
        EditText newHabitComment = (EditText) findViewById(R.id.editCommentEditText);

        controller.updateHabitSchedule(habit, schedule);
        habit.setName(newHabitName.getText().toString());
        habit.setComment(newHabitComment.getText().toString());

        Toast.makeText(this, "Habit changes saved", Toast.LENGTH_SHORT).show();
    }
}
