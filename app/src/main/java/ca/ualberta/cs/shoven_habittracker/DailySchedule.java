package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by shoven on 2016-09-25.
 */
public class DailySchedule {
    private Integer dayIndex;
    private Collection<Habit> habits;

    public DailySchedule(Integer dayIndex) {
        this.dayIndex = dayIndex;
        this.habits = new ArrayList<>();
    }

    public Integer getDayIndex() {
        return dayIndex;
    }

    public Collection<Habit> getHabits() {
        return habits;
    }

    public void addHabit(Habit habit) {
        if(!habits.contains(habit)) {
            this.habits.add(habit);
        }
    }

    public void removeHabit(Habit habit) {
        this.habits.remove(habit);
    }
}
