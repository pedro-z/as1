package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

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
        assertTrue("Habit1 does not have name", habitName.equals(habit1.toString()));
        assertTrue("Habit1 does not have date", date.toString().equals(habit1.getDate()));
        assertFalse("Habit1 has comment", comment.equals(habit1.getComment()));
        assertTrue("Habit2 does not have date", date.toString().equals(habit2.getDate()));
        assertTrue("Habit3 does not have date", date2.toString().equals(habit3.getDate()));
        assertTrue("Habit3 does not have comment", comment.equals(habit3.getComment()));

        String newHabitName = "New habit name";
        habit1.setName(newHabitName);
        assertTrue("Habit1 name not updated", newHabitName.equals(habit1.getName()));
        assertTrue("Habit1 name not updated", newHabitName.equals(habit1.toString()));
        String newComment = "New comment";
        habit1.setComment(newComment);
        assertTrue("Habit1 comment not updated", newComment.equals(habit1.getComment()));
    }

    @Test
    public void testAddRecord() {
        Habit habit = new Habit("Test habit");
        String today = new FormattedDate().toString();
        habit.addRecord();
        assertTrue(habit.getRecordList().getSize(today).equals(1));
    }
}