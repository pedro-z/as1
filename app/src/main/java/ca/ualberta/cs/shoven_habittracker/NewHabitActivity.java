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

// TODO force them to give a habit a name

public class NewHabitActivity extends AppCompatActivity {
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

    // TODO possible error handling on saveNewHabit??
    public void saveNewHabit(View v, Schedule schedule) {
        // TODO do they have to give the habit a name??
        EditText habitName = (EditText) findViewById(R.id.habitNameEditText);
        TextView habitDate = (TextView) findViewById(R.id.pickDateTextView);
        EditText habitComment = (EditText) findViewById(R.id.commentEditText);

        String[] splitDate = habitDate.getText().toString().split("-");
        Integer year = Integer.parseInt(splitDate[0]);
        Integer month = Integer.parseInt(splitDate[1]);
        Integer day = Integer.parseInt(splitDate[2]);
        FormattedDate date = new FormattedDate(year, month, day);

        Habit habit = new Habit(habitName.getText().toString(), date, habitComment.getText().toString());

        WeeklyScheduleController controller = new WeeklyScheduleController();
        controller.addHabit(habit, schedule);

        Toast.makeText(this, "Habit added", Toast.LENGTH_SHORT).show();
    }
}
