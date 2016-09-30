package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by shoven on 2016-09-28.
 */

public class RecordListTest {

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
