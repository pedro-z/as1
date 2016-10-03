package ca.ualberta.cs.shoven_habittracker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shoven on 2016-09-24.
 */

public class RecordList {
    private HashMap<String, List<Date>> recordList;

    public RecordList() {
        this.recordList = new HashMap<>();
    }

    public HashMap<String, List<Date>> getRecordListValue() {
        return recordList;
    }

    public Integer getSize(String formattedDateString) {
        if (recordList.containsKey(formattedDateString)) {
            return recordList.get(formattedDateString).size();
        }
        return 0;
    }

    public int valueCount() {
        int valueCount = 0;
        for (String key : recordList.keySet()) {
            valueCount += recordList.get(key).size();
        }
        return valueCount;
    }

    public void addRecord(String formattedDateString, Date date) {
        if (!recordList.containsKey(formattedDateString)) {
            recordList.put(formattedDateString, new ArrayList<Date>());
        }
        recordList.get(formattedDateString).add(date);
    }

    public void deleteRecord(String formattedDateString, Date date) {
        recordList.get(formattedDateString).remove(date);
        if (recordList.get(formattedDateString).isEmpty()) {
            recordList.remove(formattedDateString);
        }
    }
}
