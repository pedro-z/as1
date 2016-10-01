package ca.ualberta.cs.shoven_habittracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;

// TODO add schedule to the habit homepage view

public class HabitHomepageActivity extends AppCompatActivity {
    private Integer position;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_homepage);

        int dayIndex = new LocalDateTime(DateTimeZone.forID("Canada/Mountain")).getDayOfWeek();
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        activity = bundle.getString("activity");
        WeeklyScheduleController controller = new WeeklyScheduleController();
        Habit habitToSet;
        //Toast.makeText(this, "" + position + " " + activity, Toast.LENGTH_SHORT).show();
        if (activity.equals("MainActivity")) {
            Habit dayIndexHabit = controller.getDailySchedule(dayIndex).getHabits().get(position);
            position = controller.getAllHabits().getHabitIndex(dayIndexHabit);
            activity = "AllHabitsActivity";
        }
        habitToSet = controller.getAllHabits().getHabitList().get(position);
        //Toast.makeText(this, "" + position + " " + activity, Toast.LENGTH_SHORT).show();
        final Habit habit = habitToSet;
        if (habit != null) {
            updateHabitDetails(habit);
            habit.addListener(new Listener() {
                @Override
                public void update() {
                    updateHabitDetails(habit);
                }
            });
        } else {
            Toast.makeText(this, "Unknown habit", Toast.LENGTH_SHORT).show();
            finish();
        }
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
        builder.setPositiveButton(R.string.delete_habit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(HabitHomepageActivity.this, "Habit deleted", Toast.LENGTH_SHORT).show();
                WeeklyScheduleController controller = new WeeklyScheduleController();
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
        // update this to be the habit name for the habit screen we are on
        setTitle(habit.getName());
        TextView commentTextView = (TextView) findViewById(R.id.habitHomepageCommentTextView);
        commentTextView.setText(habit.getComment());
    }
}
