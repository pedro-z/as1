package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;

/**
 * Created by shoven on 2016-09-28.
 */
public class WeeklyScheduleController {
    private static WeeklySchedule weeklySchedule = null;
    static public WeeklySchedule getWeeklySchedule() {
        if (weeklySchedule == null) {
            weeklySchedule = new WeeklySchedule();
        }
        return weeklySchedule;
    }

    public HabitList getAllHabits() {
        return getWeeklySchedule().getAllHabits();
    }

    public DailySchedule getDailySchedule(Integer dayIndex) {
        return getWeeklySchedule().getDailySchedule(dayIndex);
    }

    public Schedule getHabitSchedule(Habit habit) {
        return getWeeklySchedule().getHabitSchedule(habit);
    }

    public void hasHabit(Habit habit) {
        getWeeklySchedule().hasHabit(habit);
    }

    public void addHabit(Habit habit, Schedule schedule) {
        getWeeklySchedule().addHabit(habit, schedule);
    }

    public void removeHabit(Habit habit) {
        getWeeklySchedule().removeHabit(habit);
    }

    public void updateHabitSchedule(Habit habit, Schedule newSchedule ) {
        getWeeklySchedule().updateHabitSchedule(habit, newSchedule);
    }
}