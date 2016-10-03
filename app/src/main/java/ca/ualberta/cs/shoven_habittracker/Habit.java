package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shoven on 2016-09-24.
 */

public class Habit {
    private String name;
    private FormattedDate date;
    private String comment;
    private RecordList recordList;
    private ArrayList<Listener> listeners;

    public Habit(String name) {
        this.name = name;
        this.date = new FormattedDate();
        this.comment = null;
    }

    public Habit(String name, FormattedDate date) {
        this.name = name;
        this.date = date;
        this.comment = null;
    }

    public Habit(String name, FormattedDate date, String comment) {
        this.name = name;
        this.date = date;
        this.comment = comment;
    }

    private ArrayList<Listener> getListeners () {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        return listeners;
    }

    public void notifyListeners () {
        for (Listener listener : getListeners()) {
            listener.update();
        }
    }

    public void addListener(Listener listener) {
        getListeners().add(listener);
    }

    public void removeListener(Listener listener) {
        getListeners().remove(listener);
    }

    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyListeners();
    }

    public String getDate() {
        return date.toString();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyListeners();
    }

    public RecordList getRecordList() {
        if (recordList == null) {
            recordList = new RecordList();
        }
        return recordList;
    }

    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }

    public void addRecord() {
        getRecordList().addRecord(new FormattedDate().toString(), new Date());
        notifyListeners();
    }
}
