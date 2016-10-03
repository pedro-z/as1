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

import static android.R.color.white;

// TODO add schedule to the habit homepage view

public class HabitHomepageActivity extends AppCompatActivity {
    private Integer position;
    private String activity;
    private int dayIndex = new LocalDateTime(DateTimeZone.forID("Canada/Mountain")).getDayOfWeek() % 7;
    private WeeklyScheduleController controller = new WeeklyScheduleController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_homepage);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        activity = bundle.getString("activity");

        if (activity == null) {
            activity = "AllHabitsActivity";
        } else if (activity.equals("MainActivity")) {
            Habit dayIndexHabit = controller.getDailySchedule(dayIndex).getHabits().get(position);
            position = controller.getAllHabits().getHabitIndex(dayIndexHabit);
            activity = "AllHabitsActivity";
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        final Habit habit = controller.getAllHabits().getHabitList().get(position);
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

        controller.getAllHabits().getHabitList().get(position).getRecordList().addListener(new Listener() {
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
                    //Toast.makeText(HabitHomepageActivity.this, "" + groupText, Toast.LENGTH_SHORT).show();
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
                WeeklyScheduleController.getWeeklySchedule().getAllHabits().getHabitList().get(position).addRecord();
                Toast.makeText(HabitHomepageActivity.this, "Habit completed", Toast.LENGTH_SHORT).show();
            }
        } );
    }

    private void deleteRecord(final String groupText, final int childPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.check_if_delete_record);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(HabitHomepageActivity.this, "Habit completion deleted", Toast.LENGTH_SHORT).show();
                Habit habit = controller.getAllHabits().getHabitList().get(position);
                String formattedDateString = new FormattedDate().toString();
                RecordList recordList = habit.getRecordList();
                recordList.getRecordListValue().get(groupText).remove(childPosition);

                ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.habitHistoryListView);
                HashMap<String, List<Date>> records = habit.getRecordList().getRecordListValue();
                Set<String> titleSet = records.keySet();
                ArrayList<String> titleList = new ArrayList<>(titleSet);
                CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(HabitHomepageActivity.this, titleList, records);
                expandableListView.setAdapter(expandableListAdapter);

                Integer todayCount = habit.getRecordList().getSize(formattedDateString);
                TextView todayCountTextView = (TextView) findViewById(R.id.dailyGoalCount);
                todayCountTextView.setText(Integer.toString(todayCount));

                int totalCount = habit.getRecordList().valueCount();
                TextView totalCountTextView = (TextView) findViewById(R.id.streakCount);
                totalCountTextView.setText(Integer.toString(totalCount));
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

    // TODO set character limit in setName...no enter
    // TODO force user to set a habit name

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

        Schedule schedule = controller.getHabitSchedule(habit);
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
