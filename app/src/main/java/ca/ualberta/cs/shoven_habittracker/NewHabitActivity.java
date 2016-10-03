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


This code draws from (and was modified):

Student Picker: Randomly pick students to answer questions

Copyright (C) 2014 Abram Hindle, Sarah Hoven abram.hindle@softwareprocess.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.


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

import java.util.Date;

public class NewHabitActivity extends AppCompatActivity {
    private WeeklyScheduleController controller = new WeeklyScheduleController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);
        final Schedule schedule = new Schedule();
        final Integer sun = 0, mon = 1, tue = 2, wed = 3, thu = 4, fri = 5, sat = 6;

        TextView textView = (TextView) findViewById(R.id.pickDateTextView);
        textView.setText(new FormattedDate().toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.saveFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewHabit(view, schedule);
                finish();
            }
        });

        ToggleButton sundayToggle = (ToggleButton) findViewById(R.id.sundayToggleButton);
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

        ToggleButton mondayToggle = (ToggleButton) findViewById(R.id.mondayToggleButton);
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

        ToggleButton tuesdayToggle = (ToggleButton) findViewById(R.id.tuesdayToggleButton);
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

        ToggleButton wednesdayToggle = (ToggleButton) findViewById(R.id.wednesdayToggleButton);
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

        ToggleButton thursdayToggle = (ToggleButton) findViewById(R.id.thursdayToggleButton);
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

        ToggleButton fridayToggle = (ToggleButton) findViewById(R.id.fridayToggleButton);
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

        ToggleButton saturdayToggle = (ToggleButton) findViewById(R.id.saturdayToggleButton);
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

    public void saveNewHabit(View v, Schedule schedule) {
        EditText habitName = (EditText) findViewById(R.id.habitNameEditText);
        TextView habitDate = (TextView) findViewById(R.id.pickDateTextView);
        EditText habitComment = (EditText) findViewById(R.id.commentEditText);

        String[] splitDate = habitDate.getText().toString().split("-");
        Integer year = Integer.parseInt(splitDate[0]);
        Integer month = Integer.parseInt(splitDate[1]);
        Integer day = Integer.parseInt(splitDate[2]);
        FormattedDate date = new FormattedDate(year, month, day);

        Habit habit = new Habit(habitName.getText().toString(), date, habitComment.getText().toString());

        controller.addHabit(habit, schedule);

        Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
    }
}
