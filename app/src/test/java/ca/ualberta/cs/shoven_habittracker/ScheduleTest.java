package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by shoven on 2016-09-28.
 */

public class ScheduleTest {
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
    public void testFillSchedule() {
        Schedule schedule = new Schedule();
        schedule.fillSchedule();
        assertTrue(schedule.getSize().equals(7));
    }

    @Test
    public void testClear() {
        Schedule schedule = new Schedule();
        schedule.fillSchedule();
        assertFalse(schedule.getSize().equals(0));
        schedule.clear();
        assertTrue(schedule.getSize().equals(0));
    }

    boolean updated = false;
    @Test
    public void testAddListener() {
        Schedule schedule = new Schedule();
        updated = false;
        Listener l = new Listener() {
            public void update() {
                ScheduleTest.this.updated = true;
            }
        };
        schedule.addListener(l);
        schedule.addToSchedule(0);
        assertTrue("WeeklySchedule didn't fire an update", this.updated);
    }

    @Test
    public void testRemoveListeners() {
        Schedule schedule = new Schedule();
        updated = false;
        Listener l = new Listener() {
            public void update() {
                ScheduleTest.this.updated = true;
            }
        };
        schedule.addListener(l);
        schedule.removeListener(l);
        schedule.addToSchedule(0);
        assertFalse("WeeklySchedule didn't fire an update", this.updated);
    }
}
