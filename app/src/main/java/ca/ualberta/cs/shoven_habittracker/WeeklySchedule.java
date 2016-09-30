package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;

/**
 * Created by shoven on 2016-09-25.
 */
public class WeeklySchedule {
    private ArrayList<DailySchedule> weeklySchedule;
    private HabitList habitList;

    public WeeklySchedule() {
        this.weeklySchedule = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            weeklySchedule.add(new DailySchedule(i));
        }
        this.habitList = new HabitList();
    }

    public ArrayList<DailySchedule> getWeeklySchedule() {
        return weeklySchedule;
    }


    public HabitList getAllHabits() {
        return habitList;
    }

    public DailySchedule getDailySchedule(Integer dayIndex) {
        for(DailySchedule schedule : weeklySchedule) {
            if(schedule.getDayIndex().equals(dayIndex)) {
                return schedule;
            }
        }
        return null;
    }

    public Schedule getHabitSchedule(Habit habit) {
        Schedule habitSchedule = new Schedule();
        for(DailySchedule dailySchedule : weeklySchedule) {
            if(dailySchedule.getHabits().contains(habit)) {
                habitSchedule.addToSchedule(dailySchedule.getDayIndex());
            }
        }
        return habitSchedule;
    }

    public boolean hasHabit(Habit habit) {
        return habitList.hasHabit(habit);
    }

    public void addHabit(Habit habit, Schedule schedule) {
        this.habitList.addHabit(habit);
        if (schedule != null) {
            for(Integer day : schedule.getSchedule()) {
                weeklySchedule.get(day).addHabit(habit);
            }
        }
    }

    public void removeHabit(Habit habit) {
        this.habitList.removeHabit(habit);
        for(DailySchedule dailySchedule : weeklySchedule) {
            dailySchedule.removeHabit(habit);
        }
    }

    public void updateHabitSchedule(Habit habit, Schedule newSchedule) {
        removeHabit(habit);
        addHabit(habit, newSchedule);
    }
}
