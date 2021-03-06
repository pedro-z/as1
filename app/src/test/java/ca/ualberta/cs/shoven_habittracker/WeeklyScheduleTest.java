package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shoven on 2016-09-28.
 */

public class WeeklyScheduleTest {
    Integer sun = 0, mon = 0, tue = 0, wed = 0, thu = 0, fri = 0, sat = 0;
    @Test
    public void testGetWeeklySchedule() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        assertTrue("Weekly Schedule not initialised", weeklySchedule.getWeeklySchedule().size() == 7);
        assertEquals(sun, weeklySchedule.getWeeklySchedule().get(sun).getDayIndex());
        assertEquals(mon, weeklySchedule.getWeeklySchedule().get(mon).getDayIndex());
        assertEquals(tue, weeklySchedule.getWeeklySchedule().get(tue).getDayIndex());
        assertEquals(wed, weeklySchedule.getWeeklySchedule().get(wed).getDayIndex());
        assertEquals(thu, weeklySchedule.getWeeklySchedule().get(thu).getDayIndex());
        assertEquals(fri, weeklySchedule.getWeeklySchedule().get(fri).getDayIndex());
        assertEquals(sat, weeklySchedule.getWeeklySchedule().get(sat).getDayIndex());
    }

    @Test
    public void testAddHabit() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();

        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        Schedule schedule = new Schedule();
        schedule.addToSchedule(wed);
        assertFalse(weeklySchedule.getAllHabits().getHabitList().contains(habit1));
        assertFalse(weeklySchedule.getAllHabits().getHabitList().contains(habit2));
        weeklySchedule.addHabit(habit1, null);
        assertTrue(weeklySchedule.getAllHabits().getHabitList().contains(habit1));
        assertFalse(weeklySchedule.getAllHabits().getHabitList().contains(habit2));
        weeklySchedule.addHabit(habit2, schedule);
        assertTrue(weeklySchedule.getAllHabits().getHabitList().contains(habit1));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().contains(habit2));
    }

    @Test
    public void testHasHabit() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        Habit habit = new Habit("Test habit");
        assertFalse(weeklySchedule.hasHabit(habit));
        weeklySchedule.addHabit(habit, null);
        assertTrue(weeklySchedule.hasHabit(habit));
    }

    @Test
    public void testGetHabits() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        assertTrue("Habit list not initialised", weeklySchedule.getAllHabits().getHabitList().size() == 0);

        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        assertFalse(weeklySchedule.getAllHabits().hasHabit(habit1));
        assertFalse(weeklySchedule.getAllHabits().hasHabit(habit2));
        weeklySchedule.addHabit(habit1, null);
        weeklySchedule.addHabit(habit2, null);
        assertTrue(weeklySchedule.getAllHabits().hasHabit(habit1));
        assertTrue(weeklySchedule.getAllHabits().hasHabit(habit2));
    }

    @Test
    public void testGetDailySchedule() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        assertTrue(weeklySchedule.getDailySchedule(sun).getHabits().size() == 0);
        assertTrue(weeklySchedule.getDailySchedule(mon).getHabits().size() == 0);
        assertTrue(weeklySchedule.getDailySchedule(tue).getHabits().size() == 0);
        assertTrue(weeklySchedule.getDailySchedule(wed).getHabits().size() == 0);
        assertTrue(weeklySchedule.getDailySchedule(thu).getHabits().size() == 0);
        assertTrue(weeklySchedule.getDailySchedule(fri).getHabits().size() == 0);
        assertTrue(weeklySchedule.getDailySchedule(sat).getHabits().size() == 0);

        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        Schedule schedule = new Schedule();
        schedule.addToSchedule(wed);
        weeklySchedule.addHabit(habit1, schedule);
        weeklySchedule.addHabit(habit2, schedule);
        assertEquals(wed, weeklySchedule.getDailySchedule(wed).getDayIndex());
        assertTrue(weeklySchedule.getDailySchedule(wed).hasHabit(habit1));
        assertTrue(weeklySchedule.getDailySchedule(wed).hasHabit(habit2));
        assertTrue(weeklySchedule.getDailySchedule(wed).getHabits().size() == 2);
    }

    @Test
    public void testRemoveHabit() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();

        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        Schedule schedule = new Schedule();
        schedule.addToSchedule(wed);
        weeklySchedule.addHabit(habit1, null);
        weeklySchedule.addHabit(habit2, schedule);
        assertTrue(weeklySchedule.getAllHabits().getHabitList().contains(habit1));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().contains(habit2));
        weeklySchedule.removeHabit(habit1);
        assertFalse(weeklySchedule.getAllHabits().getHabitList().contains(habit1));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().contains(habit2));
        weeklySchedule.removeHabit(habit2);
        assertFalse(weeklySchedule.getAllHabits().getHabitList().contains(habit1));
        assertFalse(weeklySchedule.getAllHabits().getHabitList().contains(habit2));
        try{
            weeklySchedule.removeHabit(habit2);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testInsertHabit() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();

        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        Habit habit3 = new Habit("Test habit");
        Schedule schedule = new Schedule();
        schedule.addToSchedule(wed);
        weeklySchedule.addHabit(habit1, schedule);
        weeklySchedule.addHabit(habit2, schedule);
        weeklySchedule.addHabit(habit3, schedule);

        assertTrue(schedule.getSchedule().equals(weeklySchedule.getHabitSchedule(habit1).getSchedule()));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().get(0).equals(habit1));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().get(1).equals(habit2));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().get(2).equals(habit3));

        Schedule newSchedule = new Schedule();
        newSchedule.addToSchedule(mon);
        newSchedule.addToSchedule(tue);
        weeklySchedule.updateHabitSchedule(habit1, newSchedule);

        assertTrue(newSchedule.getSchedule().equals(weeklySchedule.getHabitSchedule(habit1).getSchedule()));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().get(0).equals(habit1));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().get(1).equals(habit2));
        assertTrue(weeklySchedule.getAllHabits().getHabitList().get(2).equals(habit3));
    }

    @Test
    public void testGetHabitIndex() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();

        Habit habit1 = new Habit("Test habit");
        Habit habit2 = new Habit("Test habit");
        Habit habit3 = new Habit("Test habit");
        Schedule schedule = new Schedule();
        schedule.addToSchedule(wed);
        weeklySchedule.addHabit(habit1, schedule);
        weeklySchedule.addHabit(habit2, schedule);

        assertTrue(weeklySchedule.getAllHabits().getHabitIndex(habit1).equals(0));
        assertTrue(weeklySchedule.getAllHabits().getHabitIndex(habit2).equals(1));
        assertNull(weeklySchedule.getAllHabits().getHabitIndex(habit3));
    }

    boolean updated = false;
    @Test
    public void testNotifyAllListeners() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        Habit habit = new Habit("Test Habit");
        updated = false;
        Listener l = new Listener() {
            public void update() {
                WeeklyScheduleTest.this.updated = true;
            }
        };
        weeklySchedule.addListener(l);
        weeklySchedule.addHabit(habit, null);
        assertTrue("WeeklySchedule didn't fire an update", this.updated);
    }

    @Test
    public void testRemoveListeners() {
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        Habit habit = new Habit("Test Habit");
        updated = false;
        Listener l = new Listener() {
            public void update() {
                WeeklyScheduleTest.this.updated = true;
            }
        };
        weeklySchedule.addListener(l);
        weeklySchedule.removeListener(l);
        weeklySchedule.addHabit(habit, null);
        assertFalse("WeeklySchedule didn't fire an update", this.updated);
    }
}
