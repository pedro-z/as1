package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shoven on 2016-09-28.
 */

public class DailyScheduleTest {
    @Test
    public void testGetDayIndex() {
        Integer dayIndex = 0;
        DailySchedule dailySchedule = new DailySchedule(dayIndex);
        assertTrue("dayIndex not set", dailySchedule.getDayIndex().equals(dayIndex));
    }

    @Test
    public void testGetHabits() {
        Integer dayIndex = 0;
        DailySchedule sundaySchedule = new DailySchedule(dayIndex);
        assertTrue("habits initialised", sundaySchedule.getHabits().size() == 0);
        Habit habit1 = new Habit("Test Habit");
        Habit habit2 = new Habit("Another test habit");
        sundaySchedule.addHabit(habit1);
        sundaySchedule.addHabit(habit2);
        assertTrue(sundaySchedule.getHabits().contains(habit1));
        assertTrue(sundaySchedule.getHabits().contains(habit2));
    }

    @Test
    public void testAddHabit() {
        Integer dayIndex = 0;
        DailySchedule sundaySchedule = new DailySchedule(dayIndex);
        Habit habit = new Habit("Test habit");
        assertFalse(sundaySchedule.hasHabit(habit));
        assertTrue(sundaySchedule.getHabits().size() == 0);
        sundaySchedule.addHabit(habit);
        assertTrue(sundaySchedule.hasHabit(habit));
        assertTrue(sundaySchedule.getHabits().size() == 1);
        sundaySchedule.addHabit(habit);
    }

    @Test
    public void testRemoveHabit() {
        Integer dayIndex = 0;
        DailySchedule sundaySchedule = new DailySchedule(dayIndex);
        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Another test habit");
        sundaySchedule.addHabit(habit1);
        assertTrue(sundaySchedule.hasHabit(habit1));
        assertTrue(sundaySchedule.getHabits().size() == 1);
        sundaySchedule.addHabit(habit2);
        assertTrue(sundaySchedule.hasHabit(habit2));
        assertTrue(sundaySchedule.getHabits().size() == 2);
        sundaySchedule.removeHabit(habit1);
        assertFalse(sundaySchedule.hasHabit(habit1));
        assertTrue(sundaySchedule.getHabits().size() == 1);
        sundaySchedule.removeHabit(habit1);
        assertTrue(sundaySchedule.getHabits().size() == 1);
        sundaySchedule.removeHabit(habit2);
        assertFalse(sundaySchedule.hasHabit(habit2));
        assertTrue(sundaySchedule.getHabits().size() == 0);
    }

    @Test
    public void testClear() {
        Integer dayIndex = 0;
        DailySchedule sundaySchedule = new DailySchedule(dayIndex);
        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Another test habit");
        sundaySchedule.addHabit(habit1);
        sundaySchedule.addHabit(habit2);
        assertTrue(sundaySchedule.getHabits().size() == 2);
        sundaySchedule.clear();
    }
}
