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

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HabitHomepageActivity extends AppCompatActivity {
    private Integer position = 0;
    private String activity = "";
    private Integer dayOfWeek = new LocalDateTime(DateTimeZone.forID("Canada/Mountain")).getDayOfWeek() % 7;
    private WeeklyScheduleController controller = new WeeklyScheduleController();
    private Habit habit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_homepage);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        activity = bundle.getString("activity");

        if (activity != null) {
            if (activity.equals("MainActivity")) {
                Habit dayIndexHabit = controller.getDailySchedule(dayOfWeek).getHabits().get(position);
                position = controller.getAllHabits().getHabitIndex(dayIndexHabit);
                activity = "AllHabitsActivity";

            }
        }

        habit = controller.getAllHabits().getHabitList().get(position);
        updateHabitDetails(habit);
        habit.addListener(new Listener() {
            @Override
            public void update() {
                updateHabitDetails(habit);
            }
        });

        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.habitHistoryListView);
        final HashMap<String, List<Date>> records = controller.getAllHabits().getHabitList().get(position).getRecordList().getRecordListValue();
        final Set<String> titleSet = records.keySet();
        final ArrayList<String> titleList = new ArrayList<>(titleSet);
        final CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this, titleList, records);
        expandableListView.setAdapter(expandableListAdapter);

        WeeklyScheduleController.getWeeklySchedule().addListener(new Listener() {
            @Override
            public void update() {
                expandableListAdapter.notifyDataSetChanged();
            }
        });

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int childPosition = ExpandableListView.getPackedPositionChild(id);
                    String groupText = ((TextView) findViewById(R.id.listGroupTitle)).getText().toString();
                    deleteRecord(groupText, childPosition);
                    return true;
                }
                return false;
            }
        });

        ImageButton completeHabitButton = (ImageButton) findViewById(R.id.completeHabitButton);
        completeHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                controller.addRecord(position);
                Toast.makeText(HabitHomepageActivity.this, "Habit completed", Toast.LENGTH_SHORT).show();
            }
        } );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void deleteRecord(final String groupText, final int childPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.check_if_delete_record);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(HabitHomepageActivity.this, "Habit completion deleted", Toast.LENGTH_SHORT).show();
                controller.deleteRecord(position, groupText, childPosition);

                ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.habitHistoryListView);
                HashMap<String, List<Date>> records = controller.getAllHabits().getHabitList().get(position).getRecordList().getRecordListValue();
                Set<String> titleSet = records.keySet();
                ArrayList<String> titleList = new ArrayList<>(titleSet);
                CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(HabitHomepageActivity.this, titleList, records);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_habit_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editHabit(MenuItem Menu) {
        Toast.makeText(this, "Edit Habit", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HabitHomepageActivity.this, EditHabitActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("activity", activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void deleteHabit(MenuItem Menu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.check_if_delete_habit);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(HabitHomepageActivity.this, "Habit deleted", Toast.LENGTH_SHORT).show();
                Bundle bundle = getIntent().getExtras();
                Integer position = bundle.getInt("position");
                Habit habit = controller.getAllHabits().getHabitList().get(position);
                controller.removeHabit(habit);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateHabitDetails(Habit habit) {
        String formattedDateString = new FormattedDate().toString();

        // update this to be the habit name for the habit screen we are on
        setTitle(habit.getName());
        TextView commentTextView = (TextView) findViewById(R.id.habitHomepageCommentTextView);
        commentTextView.setText(habit.getComment());

        Integer todayCount = habit.getRecordList().getSize(formattedDateString);
        TextView countTextView = (TextView) findViewById(R.id.dailyGoalCount);
        countTextView.setText(Integer.toString(todayCount));

        int totalCount = habit.getRecordList().valueCount();
        TextView totalCountTextView = (TextView) findViewById(R.id.streakCount);
        totalCountTextView.setText(Integer.toString(totalCount));

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.habitHistoryListView);
        HashMap<String, List<Date>> records = controller.getAllHabits().getHabitList().get(position).getRecordList().getRecordListValue();
        Set<String> titleSet = records.keySet();
        ArrayList<String> titleList = new ArrayList<>(titleSet);
        CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this, titleList, records);
        expandableListView.setAdapter(expandableListAdapter);

        TextView sundayTextView = (TextView) findViewById(R.id.sundayTextView);
        TextView mondayTextView = (TextView) findViewById(R.id.mondayTextView);
        TextView tuesdayTextView = (TextView) findViewById(R.id.tuesdayTextView);
        TextView wednesdayTextView = (TextView) findViewById(R.id.wednesdayTextView);
        TextView thursdayTextView = (TextView) findViewById(R.id.thursdayTextView);
        TextView fridayTextView = (TextView) findViewById(R.id.fridayTextView);
        TextView saturdayTextView = (TextView) findViewById(R.id.saturdayTextView);

        Schedule schedule= controller.getHabitSchedule(habit);
        Schedule antiSchedule = new Schedule();
        antiSchedule.fillSchedule();
        for (Integer day : schedule.getSchedule()) {
            switch (day) {
                case 0:
                    sundayTextView.setBackgroundResource(R.drawable.ic_circle);
                    sundayTextView.setTextColor(Color.WHITE);
                    antiSchedule.removeFromSchedule(0);
                    break;
                case 1:
                    mondayTextView.setBackgroundResource(R.drawable.ic_circle);
                    mondayTextView.setTextColor(Color.WHITE);
                    antiSchedule.removeFromSchedule(1);
                    break;
                case 2:
                    tuesdayTextView.setBackgroundResource(R.drawable.ic_circle);
                    tuesdayTextView.setTextColor(Color.WHITE);
                    antiSchedule.removeFromSchedule(2);
                    break;
                case 3:
                    wednesdayTextView.setBackgroundResource(R.drawable.ic_circle);
                    wednesdayTextView.setTextColor(Color.WHITE);
                    antiSchedule.removeFromSchedule(3);
                    break;
                case 4:
                    thursdayTextView.setBackgroundResource(R.drawable.ic_circle);
                    thursdayTextView.setTextColor(Color.WHITE);
                    antiSchedule.removeFromSchedule(4);
                    break;
                case 5:
                    fridayTextView.setBackgroundResource(R.drawable.ic_circle);
                    fridayTextView.setTextColor(Color.WHITE);
                    antiSchedule.removeFromSchedule(5);
                    break;
                case 6:
                    saturdayTextView.setBackgroundResource(R.drawable.ic_circle);
                    saturdayTextView.setTextColor(Color.WHITE);
                    antiSchedule.removeFromSchedule(6);
                    break;
            }
        }
        for (Integer day : antiSchedule.getSchedule()) {
            switch (day) {
                case 0:
                    sundayTextView.setBackgroundResource(R.drawable.ic_circle_white);
                    sundayTextView.setTextColor(Color.parseColor("#ff4081"));
                    break;
                case 1:
                    mondayTextView.setBackgroundResource(R.drawable.ic_circle_white);
                    mondayTextView.setTextColor(Color.parseColor("#ff4081"));
                    break;
                case 2:
                    tuesdayTextView.setBackgroundResource(R.drawable.ic_circle_white);
                    tuesdayTextView.setTextColor(Color.parseColor("#ff4081"));
                    break;
                case 3:
                    wednesdayTextView.setBackgroundResource(R.drawable.ic_circle_white);
                    wednesdayTextView.setTextColor(Color.parseColor("#ff4081"));
                    break;
                case 4:
                    thursdayTextView.setBackgroundResource(R.drawable.ic_circle_white);
                    thursdayTextView.setTextColor(Color.parseColor("#ff4081"));
                    break;
                case 5:
                    fridayTextView.setBackgroundResource(R.drawable.ic_circle_white);
                    fridayTextView.setTextColor(Color.parseColor("#ff4081"));
                    break;
                case 6:
                    saturdayTextView.setBackgroundResource(R.drawable.ic_circle_white);
                    saturdayTextView.setTextColor(Color.parseColor("#ff4081"));
                    break;
            }
        }
    }
}
