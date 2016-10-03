package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by shoven on 2016-09-24.
 */

public class Schedule {
    private Collection<Integer> schedule;
    private ArrayList<Listener> listeners;

    public Schedule() {
        this.schedule = new ArrayList<>();
    }

    public Collection<Integer> getSchedule() {
        return schedule;
    }

    public Integer getSize() {
        return schedule.size();
    }

    public void addToSchedule(Integer dayIndex) {
        // do not add duplicates to the schedule
        // all indices in a schedule must be in between 0 and 6 (7 days of the week)
        if( !schedule.contains(dayIndex) && (dayIndex >= 0 && dayIndex < 7)  ) {
            schedule.add(dayIndex);
        }
        notifyListeners();
    }

    public void removeFromSchedule(Integer dayIndex) {
        schedule.remove(dayIndex);
        notifyListeners();
    }

    public void fillSchedule() {
        schedule.add(0);
        schedule.add(1);
        schedule.add(2);
        schedule.add(3);
        schedule.add(4);
        schedule.add(5);
        schedule.add(6);
    }

    public void clear() {
        schedule.clear();
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
}
