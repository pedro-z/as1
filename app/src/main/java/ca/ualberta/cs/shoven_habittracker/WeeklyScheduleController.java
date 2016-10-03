package ca.ualberta.cs.shoven_habittracker;

/**
 * Created by shoven on 2016-09-28.
 */
public class WeeklyScheduleController {
    private static WeeklySchedule weeklySchedule = null;
    private static MainActivity activityContext = null;

    static public WeeklySchedule getWeeklySchedule() {
        if (weeklySchedule == null) {
            weeklySchedule = new WeeklySchedule();
        }
        return weeklySchedule;
    }

    public static void setContext(MainActivity context) {
        activityContext = context;
    }

    public static void save() {
        activityContext.saveInFile();
    }

    public static void setWeeklySchedule(WeeklySchedule weeklySchedule) {
        WeeklyScheduleController.weeklySchedule = weeklySchedule;
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

    public void clear() {
        getWeeklySchedule().clear();
    }

    public void addRecord(Integer position) {
        getWeeklySchedule().addRecord(position);
        save();
    }

    public void deleteRecord(Integer position, String formattedDateString, int childPosition) {
        getWeeklySchedule().deleteRecord(position, formattedDateString, childPosition);
        save();
    }
}
