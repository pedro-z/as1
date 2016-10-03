package ca.ualberta.cs.shoven_habittracker;

import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;

/**
 * Created by shoven on 2016-09-25.
 */
public class WeeklySchedule {
    private ArrayList<DailySchedule> weeklySchedule;
    private HabitList habitList;
    private ArrayList<Listener> listeners;

    public WeeklySchedule() {
        this.weeklySchedule = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            weeklySchedule.add(new DailySchedule(i));
        }
        this.habitList = new HabitList();
        this.listeners = new ArrayList<>();
    }

    private ArrayList<Listener> getListeners () {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        return listeners;
    }

    public void notifyListeners () {
        for (Listener listener : getListeners()) {
            try {
                listener.update();
            } catch (NullPointerException e) {
                // skip
            }
        }
    }

    public void addListener(Listener listener) {
        getListeners().add(listener);
    }

    public void removeListener(Listener listener) {
        getListeners().remove(listener);
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
            if (dailySchedule.getHabits().contains(habit)) {
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
            for (Integer day : schedule.getSchedule()) {
                weeklySchedule.get(day).addHabit(habit);
            }
        }
        notifyListeners();
    }

    public void removeHabit(Habit habit) {
        this.habitList.removeHabit(habit);
        for(DailySchedule dailySchedule : weeklySchedule) {
            dailySchedule.removeHabit(habit);
        }
        notifyListeners();
    }

    public void updateHabitSchedule(Habit habit, Schedule newSchedule) {
        for (DailySchedule dailySchedule : weeklySchedule) {
            Integer position = dailySchedule.getHabits().indexOf(habit);
            dailySchedule.removeHabit(habit);
            if (newSchedule.getSchedule().contains(dailySchedule.getDayIndex())) {
                if (position == -1) {
                    dailySchedule.addHabit(habit);
                } else {
                    dailySchedule.insertHabit(habit, position);
                }
            }
        }
        notifyListeners();
    }

    public void clear() {
        this.weeklySchedule = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            weeklySchedule.add(new DailySchedule(i));
        }
        this.habitList = new HabitList();
        this.listeners = new ArrayList<>();
        notifyListeners();
    }

    public void addRecord(Integer position) {
        this.habitList.getHabitList().get(position).addRecord();
        notifyListeners();
    }

    public void deleteRecord(Integer position, String formattedDateString, int childPosition) {
        this.habitList.getHabitList().get(position).getRecordList().getRecordListValue().get(formattedDateString).remove(childPosition);
        notifyListeners();
    }
}
