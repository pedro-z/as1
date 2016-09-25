package ca.ualberta.cs.shoven_habittracker;

/**
 * Created by shoven on 2016-09-24.
 */

public class Habit {
    private String name;
    private String date;
    private String comment;
    private Schedule schedule;
    private RecordList recordList;

    public Habit(String name) {
        this.name = name;
        this.date = new FormattedDate().getFormattedDate();
    }

    public Habit(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Habit(String name, String date, String comment) {
        this.name = name;
        this.date = date;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public RecordList getRecordList() {
        return recordList;
    }

    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }
}
