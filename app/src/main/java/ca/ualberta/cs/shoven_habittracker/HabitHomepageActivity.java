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

public class HabitHomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_homepage);

        updateHabitDetails();
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
        Bundle bundle = getIntent().getExtras();
        Integer position = bundle.getInt("position");
        bundle.putInt("position", position);
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
                int dayIndex = new LocalDateTime(DateTimeZone.forID("Canada/Mountain")).getDayOfWeek();
                Bundle bundle = getIntent().getExtras();
                Integer position = bundle.getInt("position");
                Habit habit = controller.getDailySchedule(dayIndex).getHabits().get(position);
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

    public void updateHabitDetails() {
        int dayIndex = new LocalDateTime(DateTimeZone.forID("Canada/Mountain")).getDayOfWeek();
        Bundle bundle = getIntent().getExtras();
        Integer position = bundle.getInt("position");
        Habit habit = new WeeklyScheduleController().getDailySchedule(dayIndex).getHabits().get(position);
        // update this to be the habit name for the habit screen we are on
        setTitle(habit.getName());
        TextView commentTextView = (TextView) findViewById(R.id.habitHomepageCommentTextView);
        commentTextView.setText(habit.getComment());
    }
}
