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
        FormattedDate date = new FormattedDate();
        FormattedDate date2 = new FormattedDate(new Date());
        String comment = "This is a comment about the habit.";

        Habit habit1 = new Habit(habitName);
        Habit habit2 = new Habit(habitName, date);
        Habit habit3 = new Habit(habitName, date2, comment);

        assertTrue("Habit1 does not have name", habitName.equals(habit1.getName()));
        assertTrue("Habit1 does not have date", date.toString().equals(habit1.getDate()));
        assertFalse("Habit1 has comment", comment.equals(habit1.getComment()));
        assertTrue("Habit2 does not have date", date.toString().equals(habit2.getDate()));
        assertTrue("Habit3 does not have date", date2.toString().equals(habit3.getDate()));
        assertTrue("Habit3 does not have comment", comment.equals(habit3.getComment()));

        String newHabitName = "New habit name";
        habit1.setName(newHabitName);
        assertTrue("Habit1 name not updated", newHabitName.equals(habit1.getName()));
        String newComment = "New comment";
        habit1.setComment(newComment);
        assertTrue("Habit1 comment not updated", newComment.equals(habit1.getComment()));
    }

    @Test
    public void testHabitList() {
        // TODO write HabitList tests
    }

    @Test
    public void testSchedule() {
        Schedule newSchedule = new Schedule();
        newSchedule.addToSchedule(2);
        newSchedule.addToSchedule(3);
        newSchedule.addToSchedule(4);
        assertTrue("Schedule not set", newSchedule.getSize() == 3);
        newSchedule.addToSchedule(4);
        assertTrue("Duplicate added", newSchedule.getSize() == 3);
        newSchedule.removeFromSchedule(4);
        assertTrue("Schedule not updated", newSchedule.getSize() == 2);
        newSchedule.addToSchedule(-1);
        assertTrue("Schedule updated with negative index", newSchedule.getSize() == 2);
        newSchedule.addToSchedule(7);
        assertTrue("Schedule updated with too large index", newSchedule.getSize() == 2);
        newSchedule.removeFromSchedule(5);
        assertTrue("Schedule removed non-existent index", newSchedule.getSize() == 2);
    }

    @Test
    public void testDailySchedule() {
        Integer dayIndex = 0;
        DailySchedule dailySchedule = new DailySchedule(dayIndex);
        assertTrue("dayIndex not set", dailySchedule.getDayIndex().equals(dayIndex));
        assertTrue("habits initialised", dailySchedule.getHabits().size() == 0);

        String habitName = "Test habit";
        Habit habit1 = new Habit(habitName);
        dailySchedule.addHabit(habit1);
        assertTrue("Habit not added", dailySchedule.getHabits().size() == 1);
        dailySchedule.addHabit(habit1);
        assertTrue("Duplicate habit added", dailySchedule.getHabits().size() == 1);
        Habit habit2 = new Habit(habitName);
        dailySchedule.addHabit(habit2);
        assertTrue("Identical habit name not added", dailySchedule.getHabits().size() == 2);
        dailySchedule.removeHabit(habit2);
        assertTrue("Habit not removed", dailySchedule.getHabits().size() == 1);
        dailySchedule.removeHabit(habit2);
        assertTrue("Non-existent habit removed", dailySchedule.getHabits().size() == 1);
        dailySchedule.removeHabit(habit1);
        assertTrue("dailySchedule not emptied", dailySchedule.getHabits().size() == 0);
    }

    @Test
    public void testWeeklySchedule() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        assertTrue("Weekly Schedule not initialised", weeklySchedule.getWeeklySchedule().size() == 7);
        assertTrue("Daily Schedule not empty", weeklySchedule.getDailySchedule(0).getHabits().size() == 0);
        assertEquals("Daily schedule exists", null, weeklySchedule.getDailySchedule(8));

        String habitName = "Test habit 1";
        Habit habit1 = new Habit(habitName);
        Schedule schedule1 = new Schedule();
        schedule1.addToSchedule(1);
        schedule1.addToSchedule(2);
        schedule1.addToSchedule(3);
        schedule1.addToSchedule(4);
        schedule1.addToSchedule(5);
        weeklySchedule.addHabitSchedule(habit1, schedule1);

        habitName = "Test habit 2";
        Habit habit2 = new Habit(habitName);
        Schedule schedule2 = new Schedule();
        schedule2.addToSchedule(0);
        schedule2.addToSchedule(1);
        schedule2.addToSchedule(2);
        schedule2.addToSchedule(3);
        weeklySchedule.addHabitSchedule(habit2, schedule2);

        assertTrue("Sunday schedule incorrect", weeklySchedule.getDailySchedule(0).getHabits().size() == 1);
        assertTrue("Monday schedule incorrect", weeklySchedule.getDailySchedule(1).getHabits().size() == 2);
        assertTrue("Saturday schedule incorrect", weeklySchedule.getDailySchedule(6).getHabits().size() == 0);
        assertEquals("Unknown schedule exists", null, weeklySchedule.getDailySchedule(7));
        assertTrue("Habit1 schedule incorrect", weeklySchedule.getHabitSchedule(habit1).getSize() == 5);
        assertTrue("Habit2 schedule incorrect", weeklySchedule.getHabitSchedule(habit2).getSize() == 4);
        assertTrue("Habit count incorrect", weeklySchedule.getAllHabits().size() == 2);

        weeklySchedule.removeHabitSchedule(habit1);
        assertTrue("Habit1 schedule not cleared", weeklySchedule.getHabitSchedule(habit1).getSize() == 0);
        assertTrue("New Monday schedule incorrect", weeklySchedule.getDailySchedule(1).getHabits().size() == 1);
        assertTrue("New Friday schedule incorrect", weeklySchedule.getDailySchedule(5).getHabits().size() == 0);
        assertTrue("Habit count incorrect", weeklySchedule.getAllHabits().size() == 1);

        Schedule newSchedule = weeklySchedule.getHabitSchedule(habit2);
        newSchedule.addToSchedule(4);
        weeklySchedule.updateHabitSchedule(habit2, newSchedule);
        assertTrue("New Habit2 schedule incorrect", weeklySchedule.getHabitSchedule(habit2).getSize() == 5);
        newSchedule.removeFromSchedule(4);
        newSchedule.removeFromSchedule(3);
        weeklySchedule.updateHabitSchedule(habit2, newSchedule);
        assertTrue("Newer Habit2 schedule incorrect", weeklySchedule.getHabitSchedule(habit2).getSize() == 3);

        weeklySchedule.removeHabitSchedule(habit2);
        assertTrue("Habit2 schedule not cleared", weeklySchedule.getHabitSchedule(habit2).getSize() == 0);
        assertTrue("Final Sunday schedule incorrect", weeklySchedule.getDailySchedule(0).getHabits().size() == 0);
        assertTrue("Final Monday schedule incorrect", weeklySchedule.getDailySchedule(1).getHabits().size() == 0);
        assertTrue("Final Tuesday schedule incorrect", weeklySchedule.getDailySchedule(2).getHabits().size() == 0);
        assertTrue("Final Wednesday schedule incorrect", weeklySchedule.getDailySchedule(3).getHabits().size() == 0);
        assertTrue("Final Thursday schedule incorrect", weeklySchedule.getDailySchedule(4).getHabits().size() == 0);
        assertTrue("Final Friday schedule incorrect", weeklySchedule.getDailySchedule(5).getHabits().size() == 0);
        assertTrue("Final Saturday schedule incorrect", weeklySchedule.getDailySchedule(6).getHabits().size() == 0);
        assertTrue("Final Habit count incorrect", weeklySchedule.getAllHabits().size() == 0);
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
        assertTrue("Record list did not set", habit1.getRecordList().getRecordListValue().equals(newRecordList.getRecordListValue()));
        assertTrue("Record list not empty", habit1.getRecordList().getRecordListValue().size() == 0);
    }
}