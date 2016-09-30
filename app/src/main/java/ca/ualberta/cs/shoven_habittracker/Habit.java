package ca.ualberta.cs.shoven_habittracker;

import java.text.Format;
import java.text.Normalizer;

/**
 * Created by shoven on 2016-09-24.
 */

public class Habit {
    private String name;
    private FormattedDate date;
    private String comment;
    private RecordList recordList;

    public Habit(String name) {
        this.name = name;
        this.date = new FormattedDate();
    }

    public Habit(String name, FormattedDate date) {
        this.name = name;
        this.date = date;
    }

    public Habit(String name, FormattedDate date, String comment) {
        this.name = name;
        this.date = date;
        this.comment = comment;
    }

    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date.toString();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RecordList getRecordList() {
        return recordList;
    }

    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }
}
