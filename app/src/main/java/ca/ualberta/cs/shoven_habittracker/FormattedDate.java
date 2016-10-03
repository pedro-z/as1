/*
Except as noted, this content is licensed under Creative Commons Attribution 2.5. For details and restrictions, see the Content License.

Habit Tracker: Android app to track daily habits.
Copyright (C) 2016  Sarah Hoven shoven@ualberta.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.


COPYRIGHT AND PERMISSION NOTICE

Copyright (c) 1995-2015 International Business Machines Corporation and others

All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, provided that the above copyright notice(s) and this permission notice appear in all copies of the Software and that both the above copyright notice(s) and this permission notice appear in supporting documentation.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF THIRD PARTY RIGHTS. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR HOLDERS INCLUDED IN THIS NOTICE BE LIABLE FOR ANY CLAIM, OR ANY SPECIAL INDIRECT OR CONSEQUENTIAL DAMAGES, OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

Except as contained in this notice, the name of a copyright holder shall not be used in advertising or otherwise to promote the sale, use or other dealings in this Software without prior written authorization of the copyright holder.
*/

package ca.ualberta.cs.shoven_habittracker;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by shoven on 2016-09-24.
 */

public class FormattedDate implements Comparable {
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

    @Override
    public int compareTo(@NonNull Object another) throws ClassCastException {
        return (another.toString().compareTo(this.toString()));
    }
}
