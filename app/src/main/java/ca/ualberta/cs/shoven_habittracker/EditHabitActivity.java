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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        final Schedule schedule = new Schedule();
        final Integer sun = 0, mon = 1, tue = 2, wed = 3, thu = 4, fri = 5, sat = 6;

        initializeFields();

        FloatingActionButton editFab = (FloatingActionButton) findViewById(R.id.saveEditFab);
        editFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Habit update saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                saveHabitChanges(view, schedule);
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

    public void initializeFields() {
        setTitle("Edit Habit");

        WeeklyScheduleController controller = new WeeklyScheduleController();
        int dayIndex = new LocalDateTime(DateTimeZone.forID("Canada/Mountain")).getDayOfWeek();
        Bundle bundle = getIntent().getExtras();
        Integer position = bundle.getInt("position");
        Habit habit = controller.getDailySchedule(dayIndex).getHabits().get(position);

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
        }
        if (habitSchedule.contains(1)) {
            mondayToggle.setChecked(true);
        }
        if (habitSchedule.contains(2)) {
            tuesdayToggle.setChecked(true);
        }
        if (habitSchedule.contains(3)) {
            wednesdayToggle.setChecked(true);
        }
        if (habitSchedule.contains(4)) {
            thursdayToggle.setChecked(true);
        }
        if (habitSchedule.contains(5)) {
            fridayToggle.setChecked(true);
        }
        if (habitSchedule.contains(6)) {
            saturdayToggle.setChecked(true);
        }
    }

    // TODO possible error handling on saveHabitChanges
    public void saveHabitChanges (View v, Schedule schedule) {
        // TODO do they have to give the habit a name??
        EditText habitName = (EditText) findViewById(R.id.habitNameEditText);
        EditText habitComment = (EditText) findViewById(R.id.commentEditText);

        Habit habit = new Habit(habitName.getText().toString(), new FormattedDate(), habitComment.getText().toString());

        //WeeklyScheduleController controller = new WeeklyScheduleController();
        //controller.updateHabitSchedule(habit, schedule);

        Toast.makeText(this, "Habit changes saved", Toast.LENGTH_SHORT).show();
    }
}
