package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by shoven on 2016-09-24.
 */

public class Schedule {
    private Collection<Integer> schedule;

    public Schedule() {
        this.schedule = new ArrayList<>();
    }

    public Collection<Integer> getScheduleValue() {
        return schedule;
    }

    public void setScheduleValue(Collection<Integer> schedule) {
        this.schedule = schedule;
    }

    public Integer getSize() {
        return schedule.size();
    }

    public void addToSchedule(Integer dayIndex) {
        if( !schedule.contains(dayIndex) ) {
            schedule.add(dayIndex);
        }
    }

    public void removeFromSchedule(Integer dayIndex) {
        schedule.remove(dayIndex);
    }
}
