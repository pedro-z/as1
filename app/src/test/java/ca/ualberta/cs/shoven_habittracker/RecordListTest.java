package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by shoven on 2016-09-28.
 */

public class RecordListTest {

    @Test
    public void testRecordList() {
        String habitName = "Test Habit";
        Habit habit = new Habit(habitName);

        RecordList recordList = new RecordList();
        habit.setRecordList(recordList);

        assertTrue("recordList not set", habit.getRecordList().equals(recordList));
        String today = new FormattedDate().toString();
        assertTrue("recordList not empty", habit.getRecordList().getSize(today) == 0);

    }

    @Test
    public void testAddAndDeleteRecord() {
        String habitName = "Test Habit";
        Habit habit = new Habit(habitName);

        RecordList recordList = new RecordList();
        habit.setRecordList(recordList);
        String today = new FormattedDate().toString();
        Date date1 = new Date();
        Date date2 = new Date();

        recordList.addRecord(today, date1);
        recordList.addRecord(today, date2);
        assertTrue("recordList size did not update to 2", habit.getRecordList().getSize(today) == 2 );

        recordList.deleteRecord(today, date1);
        assertTrue("Record of date does not exist", habit.getRecordList().getSize(today) == 1);
        recordList.deleteRecord(today, date2);
        assertFalse("Record of data still exists", habit.getRecordList().getRecordListValue().containsKey(today));
    }

    boolean updated = false;
    @Test
    public void testAddListener() {
        RecordList recordList = new RecordList();
        String today = new FormattedDate().toString();

        updated = false;
        Listener l = new Listener() {
            public void update() {
                RecordListTest.this.updated = true;
            }
        };
        recordList.addListener(l);
        recordList.addRecord(today, new Date());
        assertTrue("RecordList didn't fire an update", this.updated);
    }

    @Test
    public void testRemoveListeners() {
        RecordList recordList = new RecordList();
        String today = new FormattedDate().toString();

        updated = false;
        Listener l = new Listener() {
            public void update() {
                RecordListTest.this.updated = true;
            }
        };
        recordList.addListener(l);
        recordList.removeListener(l);
        recordList.addRecord(today, new Date());
        assertFalse("RecordList didn't fire an update", this.updated);
    }

    @Test
    public void testGetValueCount() {
        String habitName = "Test Habit";
        Habit habit = new Habit(habitName);

        RecordList recordList = new RecordList();
        habit.setRecordList(recordList);
        String today = "2016-10-02";
        String yesterday = "2016-10-01";
        Date date1 = new Date();
        Date date2 = new Date();

        recordList.addRecord(today, date1);
        recordList.addRecord(today, date2);
        recordList.addRecord(yesterday, date1);
        recordList.addRecord(yesterday, date2);

        assertEquals(4, recordList.valueCount());
    }
}
