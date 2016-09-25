package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created by shoven on 2016-09-24.
 */

public class RecordList {
    private Collection<Date> recordList;

    public RecordList() {
        this.recordList = new ArrayList<>();
    }

    public Collection<Date> getRecordListValue() {
        return recordList;
    }

    public Integer getSize() {
        return recordList.size();
    }

    public void addRecord(Date date) {
        recordList.add(date);
    }

    public void deleteRecord(Date date) {
        recordList.remove(date);
    }
}
