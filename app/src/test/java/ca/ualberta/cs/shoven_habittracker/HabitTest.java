package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HabitTest {
    private static final String TAG = "HabitTest";

    @Test
    public void testHabit() {
        String habitName = "Test Habit";
        String date = new FormattedDate().getFormattedDate();
        String comment = "This is a comment about the habit.";

        Habit habit1 = new Habit(habitName);
        Habit habit2 = new Habit(habitName, date);
        Habit habit3 = new Habit(habitName, date, comment);

        assertTrue("Habit1 does not have name", habitName.equals(habit1.getName()));
        assertTrue("Habit1 does not have date", date.equals(habit1.getDate()));
        assertFalse("Habit1 has comment", comment.equals(habit1.getComment()));
        assertTrue("Habit2 does not have date", date.equals(habit2.getDate()));
        assertTrue("Habit3 does not have comment", comment.equals(habit3.getComment()));
    }

    @Test
    public void testSchedule() {
        String habitName = "Test Habit";
        Habit habit1 = new Habit(habitName);

        Schedule habit1Schedule = new Schedule();
        habit1Schedule.addToSchedule(2);
        habit1Schedule.addToSchedule(3);
        habit1Schedule.addToSchedule(4);
        habit1.setSchedule(habit1Schedule);

        assertTrue("Habit1 does not have schedule", habit1Schedule.equals(habit1.getSchedule()));
        Schedule schedule = habit1.getSchedule();
        assertTrue("Initial schedule size incorrect", schedule.getSize() == 3);
        schedule.addToSchedule(5);
        assertTrue("Schedule size did not update to 4", schedule.getSize() == 4);
        schedule.addToSchedule(5);
        assertTrue("Schedule size did not stay at 4", schedule.getSize() == 4);
        schedule.removeFromSchedule(2);
        assertTrue("Schedule size did not update to 3", schedule.getSize() == 3);
        schedule.removeFromSchedule(1);
        assertTrue("Schedule size changed", schedule.getSize() == 3);

        Collection<Integer> newSchedule = new ArrayList<>();
        newSchedule.add(1);
        newSchedule.add(2);
        newSchedule.add(3);
        newSchedule.add(4);
        newSchedule.add(5);
        habit1.getSchedule().setScheduleValue(newSchedule);
        assertTrue("Schedule did not set", habit1.getSchedule().getScheduleValue().size() == 5);
    }

    @Test
    public void testRecordList() {
        String habitName = "Test Habit";
        Habit habit1 = new Habit(habitName);

        RecordList recordList = new RecordList();
        habit1.setRecordList(recordList);
        assertTrue("recordList not set", habit1.getRecordList().equals(recordList));
        assertTrue("recordList not empty", habit1.getRecordList().getSize() == 0);
        recordList.addRecord(new Date());
        recordList.addRecord(new Date());
        assertTrue("recordList size did not update to 2", habit1.getRecordList().getSize() == 2 );
        Date currentDate = new Date();
        recordList.addRecord(currentDate);
        Date nextDate = new Date();
        recordList.addRecord(nextDate);
        recordList.deleteRecord(currentDate);
        assertTrue("Record of date does not exist", habit1.getRecordList().getSize() == 3);
        recordList.deleteRecord(nextDate);
        assertTrue("Record of data still exists", habit1.getRecordList().getSize() == 2);

        RecordList newRecordList = new RecordList();
        habit1.setRecordList(newRecordList);
        assertTrue("Schedule did not set", habit1.getRecordList().getRecordListValue().equals(newRecordList.getRecordListValue()));
        assertTrue("Schedule not empty", habit1.getRecordList().getRecordListValue().size() == 0);
    }

    @Test
    public void weeklyScheduleTest() {

    }
}