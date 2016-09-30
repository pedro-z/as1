package ca.ualberta.cs.shoven_habittracker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by shoven on 2016-09-24.
 */

public class FormattedDate {
    private Integer year;
    private Integer month;
    private Integer day;

    public FormattedDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public FormattedDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public FormattedDate(Integer year, Integer month, Integer day) {
        if (year > 0 && year < 9999) {
            this.year = year;
        } else {
            this.year = Calendar.getInstance().get(Calendar.YEAR);
        }
        if (month > 0 && month < 13) {
            this.month = month;
        } else {
            this.month = Calendar.getInstance().get(Calendar.MONTH);
        }
        if (day > 0 && day < 32) {
            if ((month==4||month==6||month==9||month==11) && day < 31) {
                this.day = day;
            } else if (month==2 && (day < 29 || (day < 30 && year % 4 == 0))) {
                this.day = day;
            } else {
                this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            }
        } else {
            this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
    }

    public String toString() {
        String monthStr = "0" + Integer.toString(month);
        String dayStr = "0" + Integer.toString(day);
        return Integer.toString(year) + "-"
                + monthStr.substring(monthStr.length()-2) + "-"
                + dayStr.substring(dayStr.length()-2);
    }
}
