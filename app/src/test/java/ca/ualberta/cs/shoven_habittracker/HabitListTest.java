package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by shoven on 2016-09-28.
 */
public class HabitListTest {
    private static final String TAG = "HabitListTest";

    @Test
    public void testAddHabit() {
        HabitList habitList = new HabitList();
        Habit habit = new Habit("Test habit");
        habitList.addHabit(habit);
        assertTrue(habitList.hasHabit(habit));
    }

    @Test
    public void testHasHabit() {
        HabitList habitList = new HabitList();
        Habit habit = new Habit("Test habit");
        assertFalse(habitList.hasHabit(habit));
        habitList.addHabit(habit);
        assertTrue(habitList.hasHabit(habit));
    }

    @Test
    public void testRemoveHabit() {
        HabitList habitList = new HabitList();
        Habit habit = new Habit("Test habit");
        habitList.addHabit(habit);
        assertTrue(habitList.hasHabit(habit));
        habitList.removeHabit(habit);
        assertFalse(habitList.hasHabit(habit));
    }

    @Test
    public void testGetHabits() {
        HabitList habitList = new HabitList();
        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        habitList.addHabit(habit1);

        assertEquals(habit1, habitList.getHabitList().get(0));

        habitList.addHabit(habit2);

        assertEquals(habit1, habitList.getHabitList().get(0));
        assertEquals(habit2, habitList.getHabitList().get(1));
    }

    @Test
    public void testClear() {
        HabitList habitList = new HabitList();
        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        habitList.addHabit(habit1);
        habitList.addHabit(habit2);

        assertTrue(habitList.getHabitList().contains(habit1));
        assertTrue(habitList.getHabitList().contains(habit2));

        habitList.clear();

        assertFalse(habitList.getHabitList().contains(habit1));
        assertFalse(habitList.getHabitList().contains(habit2));
    }
}
