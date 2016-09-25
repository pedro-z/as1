package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;

/**
 * Created by shoven on 2016-09-25.
 */
public class WeeklySchedule {
    private ArrayList<DailySchedule> weeklySchedule;

    public WeeklySchedule() {
        this.weeklySchedule = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            weeklySchedule.add(new DailySchedule(i));
        }
    }

    public ArrayList<DailySchedule> getWeeklySchedule() {
        return weeklySchedule;
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

    public Collection<Habit> getAllHabits() {
        Collection<Habit> habits = new ArrayList<>();
        for(DailySchedule dailySchedule : weeklySchedule) {
            for(Habit habit : dailySchedule.getHabits()) {
                if(!habits.contains(habit)) {
                    habits.add(habit);
                }
            }
        }
        return habits;
    }

    public void addHabitSchedule(Habit habit, Schedule schedule) {
        for(Integer day : schedule.getSchedule()) {
            weeklySchedule.get(day).addHabit(habit);
        }
    }

    public void removeHabitSchedule(Habit habit) {
        for(DailySchedule dailySchedule : weeklySchedule) {
            dailySchedule.removeHabit(habit);
        }
    }

    public void updateHabitSchedule(Habit habit, Schedule newSchedule) {
        removeHabitSchedule(habit);
        addHabitSchedule(habit, newSchedule);
    }
}
