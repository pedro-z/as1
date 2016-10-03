package ca.ualberta.cs.shoven_habittracker;

import android.content.Context;

/**
 * Created by shoven on 2016-09-28.
 */
public class WeeklyScheduleController {
    private static WeeklySchedule weeklySchedule = null;
    private static MainActivity contextForSave = null;

    public static WeeklySchedule getWeeklySchedule() {
        if (weeklySchedule == null) {
            weeklySchedule = new WeeklySchedule();
        }
        return weeklySchedule;
    }

    public static void setWeeklySchedule(WeeklySchedule newWeeklySchedule) {
        weeklySchedule.setWeeklySchedule(newWeeklySchedule);
        save();
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
        save();
    }

    public void removeHabit(Habit habit) {
        getWeeklySchedule().removeHabit(habit);
        save();
    }

    public void updateHabitSchedule(Habit habit, Schedule newSchedule ) {
        getWeeklySchedule().updateHabitSchedule(habit, newSchedule);
        save();
    }

    public static void setContext(MainActivity context) {
        contextForSave = context;
    }

    public static void save() {
        contextForSave.saveInFile();
    }
}
