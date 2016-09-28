package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by shoven on 2016-09-27.
 */

public class HabitList {
    private Collection<Habit> habitList;

    public HabitList() {
        this.habitList = new ArrayList<>();
    }

    public Collection<Habit> getHabitList() {
        return habitList;
    }

    public void addHabit(Habit habit) {
        if(!habitList.contains(habit)) {
            this.habitList.add(habit);
        }
    }

    public void removeHabit(Habit habit) {
        if(habitList.contains(habit)) {
            this.habitList.add(habit);
        }
    }

    public void clear() {
        this.habitList = new ArrayList<>();
    }
}
