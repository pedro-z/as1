package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by shoven on 2016-09-25.
 */
public class DailySchedule {
    private Integer dayIndex;
    private HabitList habits;

    public DailySchedule(Integer dayIndex) {
        this.dayIndex = dayIndex;
        this.habits = new HabitList();
    }

    public Integer getDayIndex() {
        return dayIndex;
    }

    public Collection<Habit> getHabits() {
        return habits.getHabitList();
    }

    public void addHabit(Habit habit) { this.habits.addHabit(habit); }

    public void removeHabit(Habit habit) {
        this.habits.removeHabit(habit);
    }
}
