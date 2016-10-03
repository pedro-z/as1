package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;

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

    public ArrayList<Habit> getHabits() {
        return habits.getHabitList();
    }

    public void addHabit(Habit habit) { this.habits.addHabit(habit); }

    public void insertHabit(Habit habit, Integer position) {
        this.habits.insertHabit(habit, position);
    }

    public void removeHabit(Habit habit) {
        this.habits.removeHabit(habit);
    }

    public boolean hasHabit( Habit habit ) {
        return this.habits.hasHabit(habit);
    }

    public void clear() {
        this.habits.clear();
    }
}
