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


This code draws from (and was modified):

Student Picker: Randomly pick students to answer questions

Copyright (C) 2014 Abram Hindle, Sarah Hoven abram.hindle@softwareprocess.ca

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.


COPYRIGHT AND PERMISSION NOTICE

Copyright (c) 1995-2015 International Business Machines Corporation and others

All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, provided that the above copyright notice(s) and this permission notice appear in all copies of the Software and that both the above copyright notice(s) and this permission notice appear in supporting documentation.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF THIRD PARTY RIGHTS. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR HOLDERS INCLUDED IN THIS NOTICE BE LIABLE FOR ANY CLAIM, OR ANY SPECIAL INDIRECT OR CONSEQUENTIAL DAMAGES, OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

Except as contained in this notice, the name of a copyright holder shall not be used in advertising or otherwise to promote the sale, use or other dealings in this Software without prior written authorization of the copyright holder.
*/

package ca.ualberta.cs.shoven_habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shoven on 2016-09-24.
 */

public class Habit {
    private String name;
    private FormattedDate date;
    private String comment;
    private RecordList recordList;
    private ArrayList<Listener> listeners;

    public Habit(String name) {
        this.name = name;
        this.date = new FormattedDate();
        this.comment = null;
    }

    public Habit(String name, FormattedDate date) {
        this.name = name;
        this.date = date;
        this.comment = null;
    }

    public Habit(String name, FormattedDate date, String comment) {
        this.name = name;
        this.date = date;
        this.comment = comment;
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

    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyListeners();
    }

    public String getDate() {
        return date.toString();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyListeners();
    }

    public RecordList getRecordList() {
        if (recordList == null) {
            recordList = new RecordList();
        }
        return recordList;
    }

    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }

    public void addRecord() {
        getRecordList().addRecord(new FormattedDate().toString(), new Date());
        notifyListeners();
    }
}
